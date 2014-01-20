package horst;

import lejos.nxt.Motor;

public class Bewegungen implements IBewegung {

	final static double RAD_UMFANG = 10.08; // = Raddurchmesser * pi (3,14) 2
	final static double KETTEN_UMFANG = 59.88; // = Kettenabstand (14,3) * pi 59,5
												// (3,14)

	private short dir;
	private IMap map;
	private double turnDifference;
	private int distanceDone;

	private final short N = 0, E = 90, S = 180, W = 270;

	/**
	 * Initiert die Bewegungsklasse und setzt die Geschwindigkeit auf ein
	 * bestimmtes Niveau.
	 * 
	 * @param map
	 */
	public Bewegungen(IMap map) {
		this.map = map;
		dir = translateDir(map.getPosRi());
		distanceDone = 0;
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
	 * F�hrt vorw�rts bis auf bestimmte Distanz
	 * 
	 * @param distance
	 *            in Centimeter anzugeben
	 */
	private boolean forward(double distance) {
		double faktor = distance / RAD_UMFANG;
		int grad = (int) (faktor * 360);
		Motor.A.rotate(grad, true);
		Motor.B.rotate(grad);
		return true;
	}

	/**
	 * F�hrt r�ckw�rts bis auf bestimmte Distanz
	 * 
	 * @param distance
	 *            in Centimeter anzugeben
	 */
	private boolean backward(double distance) {
		double faktor = distance / RAD_UMFANG;
		int grad = (int) (faktor * 360);
		Motor.A.rotate(grad, true);
		Motor.B.rotate(grad);
		return true;
	}

	/**
	 * Drehung durch gegens�tzliches Rotieren der Achsen.
	 * 
	 * @param degree
	 *            Gradanzahl die gedreht werden soll, positiv f�r Rechts,
	 *            negativ f�r Links
	 */
	@Override
	public boolean turn(double degree) {
		double turn_amount = 360 / degree;
		// Geht von dem Fall aus, dass A sich Links von Fahrtrichtung befindet
		// und B rechts davon.
		double distance = KETTEN_UMFANG / turn_amount;
		double faktor = distance / RAD_UMFANG;
		turnDifference += (faktor * 360) - Math.round(faktor * 360);
		int grad = (int)Math.round(faktor * 360);
		boolean motorStopped = false;
		while(!motorStopped) {
			if(!Motor.A.isMoving() && !Motor.B.isMoving()) {
				motorStopped = true;
			}
		}
		Motor.A.setAcceleration(2000);
		Motor.B.setAcceleration(2000);
		if(degree < 10) {
			Motor.A.setAcceleration(1500);
			Motor.B.setAcceleration(1500);
		}
		Motor.A.rotate((int)-grad,true);
		Motor.B.rotate((int)grad);
		if(degree < 10) {
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(Math.abs(turnDifference) > 3) {
			Motor.A.rotate((int)-turnDifference,true);
			Motor.B.rotate((int)turnDifference);
			turnDifference = turnDifference - (int) turnDifference;
		}
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
			distanceDone += distance;
			forward(distance);
			return true;
		} else if (distance < 0) {
			distanceDone -= distance;
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

	/**
	 * Erlaubt es, den Roboter ohne Bewegegungsbefehl in eine der 4
	 * Himmelsrichtungen zu drehen.
	 * 
	 * @param richtung
	 *            Eine Richtung vom Typ Richtun
	 * @return
	 */
	public boolean turnTo(Richtung richtung) {
		if (dir != translateDir(richtung)) {
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

	public void test(double degree) {
		Motor.A.rotate(1800, true);
		Motor.B.rotate(1800);
	}
	
	private void fixWrong()  {
		/*
		 * 1. Herausfinden wo Horst ist (laut Map)
		 * 2. Nachpr�fen ob das Stimmen kann (durch Sensoren)
		 * 3. Bei Abweichung nachbessern (Differenz im Messwert von Sensor ausbessern bis Soll erreicht.
		 */
		
	}

}
