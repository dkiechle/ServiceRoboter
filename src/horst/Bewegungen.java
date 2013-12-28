package horst;

import lejos.nxt.Motor;

public class Bewegungen {
	
	final static short RAD_UMFANG = 0; // = Raddurchmesser * pi (3,14)
	final static short KETTEN_UMFANG = 0; // = Kettenabstand * pi (3,14)
	
	public Bewegungen() {
		
	}
	
	/**
	 * Fährt vorwärts (1 Tick)
	 */
	public void forward() {
		Motor.A.backward();
		Motor.B.backward();
	}
	
	/**
	 * Fährt vorwärts bis auf bestimmte Distanz
	 * @param distance in Milimeter anzugeben
	 */
	public void forward(int distance) {
		Motor.A.rotate(-(distance / RAD_UMFANG));
		Motor.B.rotate(-(distance / RAD_UMFANG));
	}
	
	/**
	 * Fährt vorwärts (1 Tick)
	 */
	public void backward() {
		Motor.A.forward();
		Motor.B.forward();
	}
	
	/**
	 * Fährt rückwärts bis auf bestimmte Distanz
	 * @param distance in Milimeter anzugeben
	 */
	public void backward(int distance) {
		Motor.A.rotate(distance / RAD_UMFANG);
		Motor.B.rotate(distance / RAD_UMFANG);
	}
	
	/**
	 * Drehung durch gegensätzliches Rotieren der Achsen.
	 * @param degree Gradanzahl die gedreht werden soll, positiv für Rechts, negativ für Links
	 */
	public void turn(int degree) {
		int turn_ammount = 360 / degree;
		// Geht von dem Fall aus, dass A sich Links von Fahrtrichtung befindet und B rechts davon.
		Motor.A.rotate(-((KETTEN_UMFANG / turn_ammount) / RAD_UMFANG) * 360);
		Motor.B.rotate(((KETTEN_UMFANG / turn_ammount) / RAD_UMFANG) * 360);	
	}

}
