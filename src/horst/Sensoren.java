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
	
	int FEUERERKENNUNGSDIFF = 20;
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
	}



@Override
public boolean messen() throws InterruptedException {

	System.out.println("starte Messung...");
	Richtung richtung = map.getPosRi(); // Himmelsrichtung
	if (richtung == Richtung.NORTH){
		ausrichtung = 0;
	}
	else if (richtung == Richtung.EAST){
		ausrichtung = 90;
	}
	else if (richtung == Richtung.SOUTH){
		ausrichtung = 180;
	}
	else if (richtung == Richtung.WEST){
		ausrichtung = 270;
	}
	
	
	int lightMax = 0;
	int lightDir = 0;
	int lightMin = 10000;
	int soll;
	int ist;
	int fehler = 0;
	int total = 0;
	
	
	for (int i=0; i<72; i++){
		Thread.sleep(50);
		int light = LightSens.getNormalizedLightValue();
		if (light > lightMax){
			lightMax = light;
			lightDir = ausrichtung;
			}
		else if (light < lightMin){
			lightMin = light;
		}
		 soll = map.getSoll(ausrichtung);
		 ist = SonicSens.getDistance();
		total++;
		if (soll > ist * ist + SOLLTOLERANZ){
			Thread.sleep(50);
			ist = SonicSens.getDistance();
		}
		if (soll > ist * ist + SOLLTOLERANZ){
			ist = SonicSens.getDistance();
		}
		else {
			map.setWall(ausrichtung,ist );
		}
		
		
		turn(5 * lastTurn);
		
		}
	
	if (lightMax - lightMin > FEUERERKENNUNGSDIFF) {
		//feinmessung
		turn(lightDir- ausrichtung -5);
			lightMax = 0;
			lightDir = 0;
		for (int i=0; i<10; i++){
			int light = LightSens.getNormalizedLightValue();
			if (light > lightMax){
				lightMax = light;
				lightDir = ausrichtung;
				}
			turn(1);		
			}
			map.setLight(lightDir);
			System.out.println("LichtMax bei " + lightDir);
			align();
		}
		lastTurn = lastTurn * -1;
		System.out.println("Fehlerquote: " + fehler/total + "%" );
		if (fehler/total > ABBRECHFEHLERQUOTE) {
			return false;
		}
		return true;
	}








}
