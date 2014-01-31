package horst;

public class Mapnode {

	private byte x;
	private byte y;
	private byte zustand;

	public Mapnode(byte x, byte y, byte zustand) {
		this.x = x;
		this.y = y;
		this.zustand = zustand;
	}

	public byte getX() {
		return x;
	}

	public void setX(byte x) {
		this.x = x;
	}

	public byte getY() {
		return y;
	}

	public void setY(byte y) {
		this.y = y;
	}

	public byte getZustand() {
		return zustand;
	}

	public void setZustand(byte zustand) {
		this.zustand = zustand;
	}

	@Override
	public String toString() {
		return "POS: " + x + "/" + y + " ZU: " + zustand;
	}

}
