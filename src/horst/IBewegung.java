package horst;

public interface IBewegung {
	
	public boolean goWay (Richtung[] richtungen);
	
	public boolean turn (double degree);
	
	public boolean move (int distance);
	
	public boolean goRichtung (Richtung richtung);
	
}
