package horst;

import lejos.nxt.Motor;



public class Bewegungen {
	
	final static double RAD_UMFANG = 6.28; // = Raddurchmesser * pi (3,14)  2
	final static double KETTEN_UMFANG = 30.144; // = Kettenabstand * pi (3,14) 9,6
	
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
		Motor.A.rotate((int)(-(distance / RAD_UMFANG)));
		Motor.B.rotate((int)(-(distance / RAD_UMFANG)));
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
		Motor.A.rotate((int)(distance / RAD_UMFANG));
		Motor.B.rotate((int)(distance / RAD_UMFANG));
	
	}
	
	/**
	 * Drehung durch gegensätzliches Rotieren der Achsen.
	 * @param degree Gradanzahl die gedreht werden soll, positiv für Rechts, negativ für Links
	 */
	public void turn(int degree) {
		int turn_ammount = 360 / degree;
		// Geht von dem Fall aus, dass A sich Links von Fahrtrichtung befindet und B rechts davon.
		Motor.A.rotate((int)-((KETTEN_UMFANG / turn_ammount) / RAD_UMFANG) * 360,true);
		Motor.B.rotate((int)((KETTEN_UMFANG / turn_ammount) / RAD_UMFANG) * 360);	
	}

}
