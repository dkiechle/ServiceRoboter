package horst;

public interface IMap {

	public void setWall(int grad, int distanz);
	
	public void setLight(int grad, int distanz);
	
	public int getSoll(int grad);
	
	public boolean setPosition (int row, int col);
	
	public int getPosRow ();
	
	public int getPosCol ();
	
	public Richtung getPosRi ();
	
	public void setRichtung(Richtung richtung);
	
}
