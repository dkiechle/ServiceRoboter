package horst;

public interface IBewegung {
	
	public boolean goWay (Richtung[] richtungen);
	
	public boolean turn (int degree);
	
	public boolean move (int distance);
	
	public boolean goRichtung (Richtung richtung);
	
}
