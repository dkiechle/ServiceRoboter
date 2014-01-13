package horst;

public interface IMap {

	public void setWall(int grad, int distanz);//distanz = double??
	
	public void setLight(int grad, int distanz);//distanz = double??
	
	public int getSoll(int grad);
	
	public boolean setPosition (int row, int col);
	
	public int getPosRow ();
	
	public int getPosCol ();
	
	public Richtung getPosRi ();
	
	public void setRichtung(Richtung richtung);
	
}
