package horst;

import lejos.nxt.ADSensorPort;
import lejos.nxt.I2CPort;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/**
 * 
 * @author Marco Höppner
 *
 */
public class Sensoren implements ISensoren {
	
	
	int FEUERERKENNUNGSDIFF = 25;
	int LICHTSENSOROFFSET = -13;
	double SOLLTOLERANZ = 0.15;
	double ABBRECHFEHLERQUOTE = 0.6;
	ADSensorPort LICHTSENSOR = SensorPort.S1;
	I2CPort SONICSENSOR = SensorPort.S2;
	LightSensor LightSens;
	UltrasonicSensor SonicSens;
	IMap map;
	IBewegung beweg;
	int ausrichtung = 0;
	int lastTurn = 1;

	
	public boolean turnToMax(){

		int lightMax = 0;
		int lightDir = 0;
		int lightMin = 10000;

		ausrichtung = 0;
		for (int i=0; i<72; i++){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int light = LightSens.getNormalizedLightValue();
			if (light > lightMax){
				lightMax = light;
				lightDir = ausrichtung;
				}
			else if (light < lightMin){
				lightMin = light;
				}
	
			turn(5);
			
			}
		
	
			turn(lightDir - ausrichtung -10);
				lightMax = 0;
				lightDir = 0;
			for (int i=0; i<10; i++){
				int light = LightSens.getNormalizedLightValue();
				if (light > lightMax){
					lightMax = light;
					lightDir = ausrichtung;
					}
				turn(2);		
				}
			
			turn(lightDir - ausrichtung +LICHTSENSOROFFSET);
			System.out.println("LDIFF: " +(lightMax-lightMin));
			if (lightMax - lightMin < FEUERERKENNUNGSDIFF){
				return false;
			}
		return true;
	}
	
	public int getDistance(){
		return SonicSens.getDistance();
	}
	
	private void turn(int degree){
		if (degree > 180){
			degree = (-360+ degree);
		}
		else if (degree < -180){
			degree = (360 + degree);
		}
		beweg.turn(degree);
		ausrichtung = ausrichtung+degree;
		if (ausrichtung >= 360){
			ausrichtung = ausrichtung - 360;
		}
		else if (ausrichtung <0){
			ausrichtung = ausrichtung + 360;
		}
	}
	
	private void align(){  //dreht zur nächstgelegenen Himmelsrichtung
		if (ausrichtung < 45 || ausrichtung >= 345){
			turn(ausrichtung*-1);
			map.setRichtung(Richtung.NORTH);
		}
		else if (ausrichtung <135){
			turn ((ausrichtung-90)*-1);
			map.setRichtung(Richtung.EAST);
		}
		else if (ausrichtung <225){
			turn ((ausrichtung-180)*-1);
			map.setRichtung(Richtung.SOUTH);
		}
		else if (ausrichtung <315){
			turn ((ausrichtung-270)*-1);
			map.setRichtung(Richtung.WEST);
		}
	}
	
public Sensoren(IMap karte, IBewegung bewegung){
	LightSens = new LightSensor(LICHTSENSOR);
	SonicSens = new UltrasonicSensor(SONICSENSOR);
	map = karte;
	beweg = bewegung;
	LightSens.setFloodlight(false);
	}


private int translateRichtung(Richtung richtung){
	Richtung r = richtung ;
	if (richtung == Richtung.NORTH){
		return 0;
	}
	else if (richtung == Richtung.EAST){
		return 90;
	}
	else if (richtung == Richtung.SOUTH){
		return  180;
	}
	else if (richtung == Richtung.WEST){
		return 270;
	}
	return 0;
}

public void senstest(){
	while(true){
		System.out.println(LightSens.getNormalizedLightValue());
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}


@Override
public boolean messen() throws InterruptedException {

	System.out.println("starte Messung...");
	ausrichtung = translateRichtung(map.getPosRi());

	
	int lightMax = 0;
	int lightDir = 0;
	int lightMin = 10000;
	int soll;
	int ist;
	float fehler = 0;
	float total = 0;
	
	
	for (int i=0; i<72; i++){
		Thread.sleep(50);
		int light = LightSens.getNormalizedLightValue();
		if (light > lightMax){
			lightMax = light;
			LightSens.setFloodlight(true);
			Thread.sleep(100);
			LightSens.setFloodlight(false);
			lightDir = ausrichtung;
			}
		else if (light < lightMin){
			lightMin = light;
		}
		 soll = map.getSoll(ausrichtung);
		 ist = SonicSens.getDistance();
		 System.out.println("I "+ist+" S: "+soll+"D: "+(soll/ist));
		total= total + 1;
		if (soll < ist + ist * SOLLTOLERANZ){ // @Marco  (ist * ist + SOLLTOLERANZ), hat nicht funktioniert
			Thread.sleep(25);
			ist = SonicSens.getDistance();
		}
		if (soll >= ist) {
			map.setHinderniss(ausrichtung,ist );
		}
		else {
		fehler = fehler + 1;	
		}
		
		
		
		turn(5 * lastTurn);
		
		}
	
	if (lightMax - lightMin > FEUERERKENNUNGSDIFF) {
		//feinmessung
		turn(lightDir- ausrichtung -10);
			lightMax = 0;
			lightDir = 0;
		for (int i=0; i<10; i++){
			int light = LightSens.getNormalizedLightValue();
			if (light > lightMax){
				lightMax = light;
				lightDir = ausrichtung;
				}
			turn(2);		
			}
			map.setFeuer(((lightDir-10)+360)%360);
			System.out.println("LichtMax bei " + lightDir);
			align();
		}
		lastTurn = lastTurn * -1;
		//System.out.println("Fehlerquote: " + fehler/total + "%" );
		//System.out.println("ausrichtung: " + ausrichtung );
		if (fehler/total > ABBRECHFEHLERQUOTE) {
			return false;
		}
		return true;
	}



	public boolean correct (){
		
		int ausrichtung = translateRichtung(map.getPosRi());
		int closestWall = 0;
		int closest = 400;
		Richtung r = Richtung.NORTH;
		 if (map.getSoll(0) < closest){
			 closest = map.getSoll(0);
		 }
		 if (map.getSoll(90) < closest){
			 closest = map.getSoll(90);
			 closestWall = 90;

				 r = Richtung.EAST;
		 }
		 if (map.getSoll(180) < closest){
			 closest = map.getSoll(180);
			 closestWall = 180;

			 r = Richtung.SOUTH;
			 
		 }
		 if (map.getSoll(270) < closest){
			 closest = map.getSoll(270);
			 closestWall = 270;

			 r = Richtung.WEST;
		 }
		 
		turn(closestWall - ausrichtung - 10);
		int min = 400;
		int messung = SonicSens.getDistance();
			for(int i = 0; i < 20; i++){
				System.out.println("mess: "+ messung +  "min:"+min);
				if (messung < min){
					min = messung;
					closestWall = ausrichtung;
				}
				turn(1);
			}
			int toTurn = closestWall - ausrichtung;
			System.out.println("korrektur:" + toTurn + " C:" + closestWall );
		turn(toTurn);
		map.setRichtung(r);
		
		return true;
		
		
				 
		
		
	}




}
