package horst;

public class Map implements IMap {
	
	private Mapnode[][] speicher;

	Map (int cols,int rows){
		speicher = new Mapnode[cols][rows]; 
	}
	@Override
	public void setWall(int grad, int distanz) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLight(int grad, int distanz) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getSoll(int grad) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean setPosition(int row, int col) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getPosRow() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPosCol() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Richtung getPosRi() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRichtung(Richtung richtung) {
		// TODO Auto-generated method stub

	}

}
