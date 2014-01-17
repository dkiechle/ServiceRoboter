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
	/**
	 * @param args
	 * @return 
	 */
	public static void main(String[] args) {
		Map map;
		map = new Map((byte)3,(byte)20);
		Sensoren sensoren;
		Bewegungen bewegungen = new Bewegungen(map);
		map = new Map((byte)3,(byte)20);
		sensoren = new Sensoren(map,bewegungen);
		
		map.setPosition(0, 0);
		map.setRichtung(Richtung.NORTH);
		try {
			sensoren.messen();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map.getPosRi());
		Button.waitForPress();
		map.setPosition(2,0);
		try {
			sensoren.messen();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(map);
		Button.waitForPress();
		
		
	}

}
