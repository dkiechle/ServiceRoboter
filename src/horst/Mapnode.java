package horst;

public class Mapnode {
	
	int wand;
	int feuer;
	boolean messung;
	
	Mapnode () {
		wand = 0;
		feuer= 0;
		messung = false;
	}
	
	public String toString() {
		return "Wandwert:   "+wand+" Feuerwert:  "+feuer+" Messung:  "+messung;
	}
}
