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
		map = new Map((byte)10,(byte)20);
		Sensoren sensoren;
		Bewegungen bewegungen = new Bewegungen(map);
		sensoren = new Sensoren(map,bewegungen);
		map.setPosition(1, 1);
		
		try{
			for(int i = 0;i<90;i++) {
		bewegungen.turn(1);
			}
		Thread.sleep(200);
		for(int i = 0;i<45;i++) {
			bewegungen.turn(2);
		}
		Thread.sleep(200);
		for(int i = 0;i<360;i++) {
			bewegungen.turn(-1);
		}
		} catch(Exception e){}
		
		
//		try {
//			sensoren.messen();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(map.getPosRi());
//		//Button.waitForPress();
//
//		Richtung[] rs =  {Richtung.SOUTH, Richtung.SOUTH,Richtung.SOUTH,Richtung.SOUTH,Richtung.EAST,Richtung.EAST,Richtung.EAST,Richtung.EAST,Richtung.EAST,};
//		bewegungen.goWay(rs);
//		sensoren.correct();
//		map.setPosition(5,4);
//		try {
//			sensoren.messen();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Richtung[] rs2 = {Richtung.SOUTH, Richtung.SOUTH,Richtung.SOUTH, Richtung.WEST, Richtung.WEST, Richtung.WEST, Richtung.WEST
//		};
//		bewegungen.goWay(rs2);
//		sensoren.correct();
//		map.setPosition(1,7);
//		try {
//			sensoren.messen();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Richtung[] rs3 = {Richtung.EAST, Richtung.EAST, Richtung.EAST, Richtung.EAST, Richtung.EAST, Richtung.EAST, Richtung.EAST};
//		bewegungen.goWay(rs3);
//		sensoren.correct();
//		map.setPosition(8,7);
//		try {
//			sensoren.messen();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println(map);
		Button.waitForPress();
			
		}
		
		
		
	}

