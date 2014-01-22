package horst;

public interface IBewegung {
	
	public boolean goWay (Richtung[] richtungen);
	
	public boolean turn (double degree);
	
	public boolean move (double distance);
	
	public boolean goRichtung (Richtung richtung);
	
}
