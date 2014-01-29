package horst;

import lejos.nxt.Motor;

public class Bewegungen implements IBewegung {

	final static double RAD_UMFANG = 10.2; // = Raddurchmesser * pi (3,14) 2
	final static double KETTEN_UMFANG = 62.33; // = Kettenabstand (14,3) * pi 59,5


	private int dir;
	private IMap map;
	private Sensoren sensor;
	private double turnDifference;

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
		Motor.A.setSpeed(300);
		Motor.B.setSpeed(300);
		
	}
	
	public void setSensor(Sensoren send) {
		sensor = send;
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
	 * Fährt rückwärts bis auf bestimmte Distanz
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
	 * Drehung durch gegensätzliches Rotieren der Achsen.
	 * 
	 * @param degree
	 *            Gradanzahl die gedreht werden soll, positiv für Rechts,
	 *            negativ für Links
	 */
	@Override
	public boolean turn(double degree) {
		dir = (dir + (int)degree)%360;
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
		Motor.A.setAcceleration(2800);
		Motor.B.setAcceleration(2800);
		/* Veraltet
		if(degree < 10) {
			Motor.A.setAcceleration(1400);
			Motor.B.setAcceleration(1400);
		}
		*/
		Motor.A.rotate((int)-grad,true);
		Motor.B.rotate((int)grad);
		if(Math.abs(turnDifference) > 1) {
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
		while(Motor.A.isMoving() || Motor.B.isMoving()) {
			
		}
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
	public boolean move(double distance) {
		double section = distance / 10;
		double section2 = section;
		
			if (distance > 0) {
				for(;section<distance&&(sensor.getDistance()>18);section+= distance/10){
					forward(section2);
				
				}
			} else if (distance < 0) {
				backward(distance);
			}
		
		return true;
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
			move(19.4);
			map.setRichtung(richtung);
		} else {
//			if (dir == N || dir == E || dir == S || dir == W) {
//			turn(90);
//			goRichtung(richtung); }
		// Veralteter Code
			if ((translateDir(richtung) - dir) < (dir - translateDir(richtung) + 360)) {
				turn(translateDir(richtung) - dir);
				dir = translateDir(richtung);
				goRichtung(richtung);
			} else {
				turn(-(dir - translateDir(richtung) + 360));
				dir = translateDir(richtung);
				goRichtung(richtung);	
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
	
	public void killFlame() {
		move(8);
		try{
			Thread.sleep(50);
		} catch (Exception e){}
		move(-10);
	}
	
	private void fixWrong()  {
		/*
		 * 1. Herausfinden wo Horst ist (laut Map)
		 * 2. Nachprüfen ob das Stimmen kann (durch Sensoren)
		 * 3. Bei Abweichung nachbessern (Differenz im Messwert von Sensor ausbessern bis Soll erreicht.
		 */
		
	}

}
