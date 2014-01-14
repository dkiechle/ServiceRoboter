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
		map = new Map(60,60);
		for(int i=0;i<59;i++){
			for(int j=0;j<59;j++){
				map.speicher[i][j].feuer = 10;
				map.speicher[i][j].wand  = 10;
				System.out.println(" "+i+"  "+j);
			}
		}
	}

}
