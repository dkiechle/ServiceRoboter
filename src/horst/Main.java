/**
 * 
 */
package horst;

import lejos.nxt.Button;



/**
 * @author Daniel Kiechle
 *
 */
public class Main {
	
	static Bewegungen beweg;
	static Sonar sonar;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Hallo Horst der Gerät");
		// Folgende Zeilen sind zum testen des Sonars, sowie zum testen der Bewegung erstellt.
		beweg = new Bewegungen();
		sonar = new Sonar();
		int maxIn=0,maxL=0,minIn=0,minL=300,minD=300,minDistIn=0;
		if(true) {
			sonar.rg.setFloodlight(false);
			//sonar.rgbTest();
			for (int i=0; i<9; i++){
			beweg.turn(90);
			sonar.rg.setFloodlight(false);
			if (sonar.rg.getNormalizedLightValue()>maxL){
				//sonar.rg.setFloodlight(true);
			maxL=sonar.rg.getNormalizedLightValue();
			maxIn=i;
			}
			if (sonar.rg.getNormalizedLightValue()<minL){
				//sonar.rg.setFloodlight(true);
			minL=sonar.rg.getNormalizedLightValue();
			minIn=i;
			}
			
			if (sonar.us.getDistance()<minD){
				minD=sonar.us.getDistance();
				minDistIn=i;
			}
			}
			System.out.println("maxL "+maxIn+" light"+maxL+" minL "+minIn+" light"+minL+" minD "+minDistIn+" dist"+minD);
			Button.waitForPress();
		}
		/*if(true) {
			beweg.forward(100);
			beweg.turn(90);
			beweg.backward(50);
			beweg.turn(-180);
			beweg.forward(50);
			beweg.turn(-90);
			beweg.forward(100);
			System.out.println("Bin fertig");
			
		}*/
	}

}
