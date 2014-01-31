/**
 * 
 */
package horst;

/**
 * @author Daniel Kiechle
 * 
 */
public class FireLocalisation {

	/**
	 * @param args
	 * @return
	 */

	public static void main(String[] args) {
		Map map;
		Sensoren sensoren;
		Bewegungen bewegungen;
		Lokalisierung lokalisierung;

		map = new Map((byte) 10, (byte) 20);
		bewegungen = new Bewegungen(map);
		sensoren = new Sensoren(map, bewegungen);
		bewegungen.setSensor(sensoren);
		lokalisierung = new Lokalisierung(map, bewegungen);

		for (; !lokalisierung.isFire();) {
			try {
				sensoren.messen();
			} catch (InterruptedException e) {

				e.printStackTrace();
			}
			lokalisierung.nextStep();

		}
		lokalisierung.stepToFire();
		if (lokalisierung.isFire())
			System.out.println("FERTIG");

	}

}
