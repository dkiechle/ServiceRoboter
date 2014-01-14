package horst;

import lejos.nxt.Motor;

public class Bewegungen implements IBewegung {

	final static double RAD_UMFANG = 8.0; // = Raddurchmesser * pi (3,14) 2 
	final static double KETTEN_UMFANG = 32.90; // =  Kettenabstand (14,3) * pi (3,14)

	private IMap map;
	private short dir;

	private short N = 0, E = 90, S = 180, W = 270;

	public Bewegungen(IMap map) {
		this.map = map;
		dir = translateDir(map.getPosRi());
		Motor.A.setSpeed(240);
		Motor.B.setSpeed(240);
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
	private void forward(double distance) {
		double faktor = distance / RAD_UMFANG;
		int grad = (int)(faktor*360);
		Motor.A.rotate(grad, true);
		Motor.B.rotate(grad);
	}

	/**
	 * Fährt rückwärts bis auf bestimmte Distanz
	 * 
	 * @param distance
	 *            in Milimeter anzugeben
	 */
	private void backward(double distance) {
		double faktor = distance / RAD_UMFANG;
		int grad = (int)(faktor*360);
		Motor.A.rotate(grad, true);
		Motor.B.rotate(grad);

	}

	/**
	 * Drehung durch gegensätzliches Rotieren der Achsen.
	 * 
	 * @param degree
	 *            Gradanzahl die gedreht werden soll, positiv für Rechts,
	 *            negativ für Links
	 */
	@Override
	public boolean turn(double degree) {
		double turn_amount = 360.0 / degree;
		// Geht von dem Fall aus, dass A sich Links von Fahrtrichtung befindet
		// und B rechts davon.
		Motor.A.setAcceleration(2000);
		Motor.B.setAcceleration(2000);
		Motor.A.rotate((int) (-((KETTEN_UMFANG / turn_amount) / RAD_UMFANG) * 720), true);
		Motor.B.rotate((int) (((KETTEN_UMFANG / turn_amount) / RAD_UMFANG) * 720));
		Motor.A.setAcceleration(6000);
		Motor.B.setAcceleration(6000);
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
			if ((translateDir(richtung) - dir) < (dir - translateDir(richtung) + 360)) {
				turn(translateDir(richtung) - dir);
				dir = translateDir(richtung);
			} else {
				turn(-(dir - translateDir(richtung) + 360));
				dir = translateDir(richtung);
			}
		}
		return true;
	}

}
