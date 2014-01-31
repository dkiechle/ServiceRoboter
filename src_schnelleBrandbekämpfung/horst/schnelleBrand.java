/**
 * 
 */
package horst;

import lejos.nxt.Button;

/**
 * @author Daniel Kiechle
 * 
 */
public class schnelleBrand {

	/**
	 * @author Marco Höppner
	 * 
	 * Der Hauptalgorithmus.
	 * Sucht Das Lichtmaxima und nähert sich diesem, während Hindernissen ausgewichen wird.
	 * Löscht solange Teelichter, bis keine zu großen Lichtunterschiede mehr erkennbar sind.
	 */

	public static void main(String[] args) {

		Sensoren sensoren;
		Bewegungen bewegungen;

		bewegungen = new Bewegungen();
		sensoren = new Sensoren(bewegungen);
		bewegungen.setSensor(sensoren);

		int abortCount = 0;
		int distance;
		while (abortCount < 5) {

			if (sensoren.turnToMax() == false) {
				abortCount++;
			} else {
				abortCount = 0;
			}
			if (abortCount == 3) {
				bewegungen.turn(30 + Math.random() * 100);
			}
			distance = sensoren.getDistance();

			double distToDrive = ((distance / 3) * 2);

			bewegungen.move(Math.max(distToDrive, 25));
			bewegungen.move(-8);

		}
		Button.waitForPress();
	}

}
