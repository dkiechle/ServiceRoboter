package horst;

import lejos.nxt.Motor;

public class Bewegungen implements IBewegung {

	final static double RAD_UMFANG = 6.28; // = Raddurchmesser * pi (3,14) 2
	final static double KETTEN_UMFANG = 60.288; // = 2x Kettenabstand (9,6) * pi (3,14)

	private IMap map;
	private short dir;

	private short N = 0, E = 90, S = 180, W = 270;

	public Bewegungen(IMap map) {
		this.map = map;
		dir = translateDir(map.getPosRi());
	}

	private short translateDir(Richtung r) {
		short ret;
		if (r == Richtung.NORTH) {
			ret = N;
		} else if (r == Richtung.EAST) {
			ret = E;
		} else if (r == Richtung.SOUTH) {
			ret = S;
		} else {
			ret = W;
		}
		return ret;
	}

	/**
	 * Fährt vorwärts bis auf bestimmte Distanz
	 * 
	 * @param distance
	 *            in Milimeter anzugeben
	 */
	private void forward(int distance) {
		Motor.A.rotate(((int) (-(distance / RAD_UMFANG))) * 360, true);
		Motor.B.rotate(((int) (-(distance / RAD_UMFANG))) * 360);
	}

	/**
	 * Fährt rückwärts bis auf bestimmte Distanz
	 * 
	 * @param distance
	 *            in Milimeter anzugeben
	 */
	private void backward(int distance) {
		Motor.A.rotate(((int) (distance / RAD_UMFANG)) * 360, true);
		Motor.B.rotate(((int) (distance / RAD_UMFANG)) * 360);

	}

	/**
	 * Drehung durch gegensätzliches Rotieren der Achsen.
	 * 
	 * @param degree
	 *            Gradanzahl die gedreht werden soll, positiv für Rechts,
	 *            negativ für Links
	 */
	@Override
	public boolean turn(int degree) {
		int turn_ammount = 360 / degree;
		// Geht von dem Fall aus, dass A sich Links von Fahrtrichtung befindet
		// und B rechts davon.
		Motor.A.rotate((int) -((KETTEN_UMFANG / turn_ammount) / RAD_UMFANG) * 360, true);
		Motor.B.rotate((int) ((KETTEN_UMFANG / turn_ammount) / RAD_UMFANG) * 360);
		return true;
	}

	/**
	 * Bewegung entlang einer Kette von Richtungen (Himmelsrichtungen).
	 * 
	 * @param richtungen
	 *            Eine folge von Richtungen vom Typ Richtung.
	 */
	@Override
	public boolean goWay(Richtung[] richtungen) {
		for (int i = 0; i < richtungen.length; i++) {
			goRichtung(richtungen[i]);
		}
		return true;
	}

	/**
	 * Bewegung ohne Rotation nach vorne oder nach hinten.
	 * 
	 * @param distance
	 *            Die zu fahrende Distanz in milimeter.
	 */
	@Override
	public boolean move(int distance) {
		if (distance > 0) {
			forward(distance);
			return true;
		} else if (distance < 0) {
			backward(distance);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Bewegung um ein Feld in Himmelsrichtung
	 * 
	 * @param richtung
	 *            Eine Richtung vom Typ Richtung.
	 */
	@Override
	public boolean goRichtung(Richtung richtung) {
		if (dir == translateDir(richtung)) {
			move(15);
		} else if (dir == N || dir == E || dir == S || dir == W) {
			turn(90);
			dir += 90 % 360;
			goRichtung(richtung);
		} else {
			turn(dir - translateDir(richtung));
			dir = translateDir(richtung);
		}
		return true;
	}

}
