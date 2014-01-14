package horst;

public class Mapnode {
	
	byte wand;
	byte feuer;
	boolean gewesen;
	
	public void addWand(){
		if(wand==5)return;
		wand++;
	}
	
	public void addFeuer(){
		feuer++;
	} 
	
	public void setGewesen(){
		gewesen = true;
	}
	
	public byte getWand(){
		return wand;
	}
	
	public byte getFeuer(){
		return feuer;
	}
	
	public boolean getGewesen(){
		return gewesen;
	}
	
	public String toString() {
		return "Wandwert:   "+wand+" Feuerwert:  "+feuer+" Messung:  "+gewesen;
	}
}
