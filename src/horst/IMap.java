package horst;

public interface IMap {
	
	/**
	 * Hinzufügen eines neuen Hindernisses in die Map. 
	 * @param grad 
	 * Von Richtung.NORTH im Uhrzeigersinn
	 * @param distanz
	 * Angabe in mm
	 */
	public void setHinderniss(int grad, int distanz);//distanz = double??
	
	/**
	 * Setzt in allen Feldern den Feuerwert hoch in Abhängigkeit des übergebenden Grades.
	 * @param grad
	 * Von Richtung.NORTH im Uhrzeigersinn
	 */
	public void setFeuer(int grad);//distanz = double??
	
	/**
	 * Gibt den bei der Distanzmessung zu erwartenden Sollwert zurück.
	 * @param grad
	 * Von Richtung.NORTH im Uhrzeigersinn
	 * @return
	 * Sollwert von 0 bis 255
	 */
	public int getSoll(int grad);
	
	/**
	 * Setzt aktuelle Position
	 * @param x
	 * Position auf der x Achse
	 * @param y
	 * Position auf der y Achse
	 * @return
	 *  true Position erfolgreich eingetragen <br>
	 *	false Roboter war schon auf gesetzter Position oder Position nicht in der Map
	 */
	public boolean setPosition (int x, int y);
	
	/**
	 * Gibt aktuellen y Wert der Position zurück.
	 * @return int
	 */
	public int getPosX ();
	
	/**
	 * Gibt aktuellen x Wert der Position zurück.
	 * @return int
	 */
	public int getPosY ();
	
	/**
	 * Gibt aktuelle Blickrichtung des Roboters zurück.
	 * @see Richtung
	 * @return
	 * Richtung
	 */
	public Richtung getPosRi ();
	
	/**
	 * Setzt aktuelle Blickrichtung.
	 * @see Richtung
	 * @param richtung
	 * erwartet Richtung
	 */
	public void setRichtung(Richtung richtung);
	
	/**
	 * Gibt zurück ob im Feld (x,y) sich ein Hinderniss befindet.
	 * @param x
	 * Position auf der x Achse
	 * @param y
	 * Position auf der y Achse
	 * @return
	 * true wenn sich ein Hinderniss auf Position (x,y) befindet
	 */
	public boolean isFree(int x, int y);
	
	/**
	 * Gibt den Feuerwet im Feld (x,y) zurück.
	 * @param x
	 * Position auf der x Achse
	 * @param y
	 * Position auf der y Achse
	 * @return
	 * Feuerwert von 0 bis 127
	 */
	public byte getFeuer(int x, int y);
	
	/**
	 * Gibt die länge des Spielfelds zurück.
	 * @return
	 * Länge des Spielfelds
	 */
	byte getlength();
	
}
