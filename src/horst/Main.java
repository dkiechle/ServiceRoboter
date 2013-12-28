/**
 * 
 */
package horst;

import lejos.nxt.*;

/**
 * @author Daniel Kiechle
 *
 */
public class Main {
	
	static Bewegungen beweg;
	static Sonar sonar;
	LCD lcd;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hallo Horst der Gerät");
		// Folgende Zeilen sind zum testen des Sonars, sowie zum testen der Bewegung erstellt.
		beweg = new Bewegungen();
		sonar = new Sonar();
		Button.waitForPress();
		if(Button.RIGHT.isPressed()) {
			sonar.sonarTest();
		}
		if(Button.LEFT.isPressed()) {
			beweg.forward(100);
			beweg.turn(90);
			beweg.backward(50);
			beweg.turn(-180);
			beweg.forward(50);
			beweg.turn(-90);
			beweg.forward(100);
			System.out.println("Bin fertig");
			
		}
	}

}
