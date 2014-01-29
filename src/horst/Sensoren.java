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
	ADSensorPort LICHTSENSOR = SensorPort.S1;
	I2CPort SONICSENSOR = SensorPort.S2;
	LightSensor LightSens;
	UltrasonicSensor SonicSens;
	IBewegung beweg;
	int ausrichtung = 0;

	public boolean turnToMax() {

		int lightMax = 0;
		int lightDir = 0;
		int lightMin = 10000;

		ausrichtung = 0;
		for (int i = 0; i < 72; i++) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int light = LightSens.getNormalizedLightValue();
			if (light > lightMax) {
				lightMax = light;
				lightDir = ausrichtung;
			} else if (light < lightMin) {
				lightMin = light;
			}

			turn(5);

		}

		turn(lightDir - ausrichtung - 10);
		lightMax = 0;
		lightDir = 0;
		for (int i = 0; i < 10; i++) {
			int light = LightSens.getNormalizedLightValue();
			if (light > lightMax) {
				lightMax = light;
				lightDir = ausrichtung;
			}
			turn(2);
		}

		turn(lightDir - ausrichtung + LICHTSENSOROFFSET);
		System.out.println("LDIFF: " + (lightMax - lightMin));
		if (lightMax - lightMin < FEUERERKENNUNGSDIFF) {
			return false;
		}
		return true;
	}

	public int getDistance() {
		return SonicSens.getDistance();
	}

	private void turn(int degree) {
		if (degree > 180) {
			degree = (-360 + degree);
		} else if (degree < -180) {
			degree = (360 + degree);
		}
		beweg.turn(degree);
		ausrichtung = ausrichtung + degree;
		if (ausrichtung >= 360) {
			ausrichtung = ausrichtung - 360;
		} else if (ausrichtung < 0) {
			ausrichtung = ausrichtung + 360;
		}
	}

	public Sensoren(IBewegung bewegung) {
		LightSens = new LightSensor(LICHTSENSOR);
		SonicSens = new UltrasonicSensor(SONICSENSOR);
		beweg = bewegung;
		LightSens.setFloodlight(false);
	}

}
