package horst;

public class Map implements IMap {
	
	public Mapnode[][] speicher;
	private int[] position;
	private Richtung richtung;

	Map (int cols,int rows){
		speicher = new Mapnode[cols][rows];
		position = new int [2];
	}
	@Override
	public void setWall(int grad, int distanz) {
		speicher[0][0].wand = speicher[0][0].wand+1;
		System.out.println(speicher[0][0]);
		

	}

	@Override
	public void setLight(int grad, int distanz) {
		speicher[0][0].feuer = speicher[0][0].feuer+1;
		System.out.println(speicher[0][0]);

	}

	@Override
	public int getSoll(int grad) {
		
		return 255;
	}

	@Override
	public boolean setPosition(int row, int col) {
		position[0] = col; // Spalte
		position[1] = row; // Zeile
		if(!speicher[col][row].messung) speicher[0][0].messung = true;
		return true;
	}

	@Override
	public int getPosRow() {
		return position[1];
	}

	@Override
	public int getPosCol() {
		return position[0];
	}

	@Override
	public Richtung getPosRi() {
		return richtung;
	}

	@Override
	public void setRichtung(Richtung richtung) {
		this.richtung = richtung;

	}

}
