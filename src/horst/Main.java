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
	
	/**
	 * @param args
	 * @return 
	 */
	public static void main(String[] args) {
		Map map;
		Sensoren sensoren;
		Bewegungen bewegungen;
		Lokalisierung lokalisierung;
		
		map = new Map((byte)10,(byte)20);
		bewegungen = new Bewegungen(map);
		sensoren = new Sensoren(map,bewegungen);
		lokalisierung = new Lokalisierung(map,bewegungen);
		
		/*for(int i = 0;!lokalisierung.isFire();i++){
			try {
				sensoren.messen();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lokalisierung.nextStep();
			System.out.println(i);
		}
			lokalisierung.stepToFire();
			if(lokalisierung.isFire())System.out.println("FERTIG");
			//sensoren.senstest();
			*/
		
			bewegungen.move(8);
			bewegungen.move(-10);
			//bewegungen.turn(1800);
			//Button.waitForPress();
		}
		
		
		
	}

