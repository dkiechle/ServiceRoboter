/**
 * 
 */
package horst;



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
		map = new Map((byte)20,(byte)20);
		map.setPosition(10, 10);
		map.setRichtung(Richtung.SOUTH);
		map.setWall(145, 90);
		map.setWall(20, 60);
		map.setLight(93);
		System.out.println(map);
		
	}

}
