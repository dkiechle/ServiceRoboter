package horst;

import lejos.nxt.Button;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Sonar {
	
	UltrasonicSensor us;
	
	Sonar() {
		us = new UltrasonicSensor(SensorPort.S1);
	}
	
	public void sonarTest() {
		while(!Button.ENTER.isPressed()) {
			System.out.println(us.getDistance());
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
	}

}
