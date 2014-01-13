package horst;

public class Mapnode {
	
	public int wand;
	public int feuer;
	public boolean messung;
	
	Mapnode () {
		wand = 0;
		feuer= 0;
		messung = false;
	}
	
	public String toString() {
		return "Wandwert:   "+wand+" Feuerwert:  "+feuer+" Messung:  "+messung;
	}
}
