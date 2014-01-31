package horst;

import lejos.nxt.Motor;

public class Bewegungen implements IBewegung {

	final static double RAD_UMFANG = 10.2; // = Raddurchmesser * pi (3,14) 2
	final static double KETTEN_UMFANG = 62.33; // = Kettenabstand (14,3) * pi
												// 59,5

	private Sensoren sensor;
	private double turnDifference;

	/**
	 * Initiert die Bewegungsklasse und setzt die Geschwindigkeit auf ein
	 * bestimmtes Niveau.
	 * 
	 * @param map
	 */
	public Bewegungen() {

		Motor.A.setSpeed(300);
		Motor.B.setSpeed(300);

	}

	/**
	 * Für den funktionierenden Ablauf wird die Sensorenklasse benötigt, diese
	 * wird hiermit gesetzt.
	 * 
	 * @param send
	 */
	public void setSensor(Sensoren send) {
		sensor = send;
	}

	/**
	 * Fährt vorwärts bis auf bestimmte Distanz
	 * 
	 * @param distance
	 *            in Centimeter anzugeben
	 */
	private boolean forward(double distance) {
		double faktor = distance / RAD_UMFANG;
		int grad = (int) (faktor * 360);
		Motor.A.rotate(grad, true);
		Motor.B.rotate(grad);
		return true;
	}

	/**
	 * Fährt rückwärts bis auf bestimmte Distanz
	 * 
	 * @param distance
	 *            in Centimeter anzugeben
	 */
	private boolean backward(double distance) {
		double faktor = distance / RAD_UMFANG;
		int grad = (int) (faktor * 360);
		Motor.A.rotate(grad, true);
		Motor.B.rotate(grad);
		return true;
	}

	/**
	 * Drehung durch gegensätzliches Rotieren der Achsen.
	 * 
	 * @param degree
	 *            Gradanzahl die gedreht werden soll, positiv für Rechts,
	 *            negativ für Links
	 */
	@Override
	public boolean turn(double degree) {
		double turn_amount = 360 / degree;
		// Geht von dem Fall aus, dass A sich Links von Fahrtrichtung befindet
		// und B rechts davon.
		double distance = KETTEN_UMFANG / turn_amount;
		double faktor = distance / RAD_UMFANG;
		turnDifference += (faktor * 360) - Math.round(faktor * 360);
		int grad = (int) Math.round(faktor * 360);
		boolean motorStopped = false;
		while (!motorStopped) {
			if (!Motor.A.isMoving() && !Motor.B.isMoving()) {
				motorStopped = true;
			}
		}
		Motor.A.setAcceleration(2800);
		Motor.B.setAcceleration(2800);

		Motor.A.rotate((int) -grad, true);
		Motor.B.rotate((int) grad);
		if (Math.abs(turnDifference) > 1) {
			Motor.A.rotate((int) -turnDifference, true);
			Motor.B.rotate((int) turnDifference);
			turnDifference = turnDifference - (int) turnDifference;
		}
		Motor.A.setAcceleration(6000);
		Motor.B.setAcceleration(6000);
		return true;
	}

	/**
	 * Bewegung ohne Rotation nach vorne oder nach hinten. Die Distanz wird in
	 * 10tel geteilt und abgefahren, wird dabei ein Hinderniss in Fahrtirichtung
	 * gemessen, wird der Bewegungsablauf unterbrochen.
	 * 
	 * @param distance
	 *            Die zu fahrende Distanz in milimeter.
	 */
	@Override
	public boolean move(double distance) {
		double section = distance / 10;
		double section2 = section;

		if (distance > 0) {
			for (; section < distance && (sensor.getDistance() > 18); section += distance / 10) {
				forward(section2);

			}
		} else if (distance < 0) {
			backward(distance);
		}

		return true;
	}
}
