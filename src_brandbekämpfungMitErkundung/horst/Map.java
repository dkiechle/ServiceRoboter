package horst;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
/**
 *Die Klasse Map implementiert alle nštigen Speicher Ressourcen.
 *@author Daniel Kiechle <kiechle.daniel@web.de>
 *@version 1.0 
 */
public class Map implements IMap {
	
	private List<Mapnode> map;
	private byte[] position;
	private double groese;
	private byte felder;
	private Richtung richtung;
	
	/**
	 * Initialisiert die Map.
     * Aktuelle Position wird auf (0,0) gesetzt und Blickrichtung auf Richtung.NORTH.
	 * @param felder
	 * Anzahl der Felder in x oder y Achse
	 * @param groese
	 * Grš§e einer Kante eines Feldes in mm
	 */
	public Map (byte felder,double groese){
		map = new ArrayList<Mapnode>();
		position = new byte[] {0, 0}; 
		map.add(new Mapnode(position[0],position[1],(byte)100));
		richtung = Richtung.NORTH;
		this.groese = groese;
		this.felder = (byte) (felder-1);
	}
	
	private byte searchMap (byte x,byte y){
		for(byte i=0; i!=(byte)map.size();i++){
			if(map.get(i).getX()==x&&map.get(i).getY()==y) return (byte) map.indexOf(map.get(i));
		}
		return -1;
	}
	
	private byte[] getFeld (int grad,int distanz){
		switch (grad/90) {
		case 0:
			return new byte[] {(byte) (position[0] + Math.floor((distanz*Math.sin(((grad*Math.PI)/180))/groese)+0.5)),(byte) (position[1] - Math.floor((distanz*Math.cos(((grad*Math.PI)/180))/groese)+0.5))};
		case 1:
			grad = grad-90;
			return new byte[] {(byte) (position[0] + Math.floor((distanz*Math.cos(((grad*Math.PI)/180))/groese)+0.5)),(byte) (position[1] + Math.floor((distanz*Math.sin(((grad*Math.PI)/180))/groese)+0.5))};
		case 2:
			grad = grad-180;
			return new byte[] {(byte) (position[0] - Math.floor((distanz*Math.sin(((grad*Math.PI)/180))/groese)+0.5)),(byte) (position[1] + Math.floor((distanz*Math.cos(((grad*Math.PI)/180))/groese)+0.5))};
		case 3:
			grad = grad-270;
			return new byte[] {(byte) (position[0] - Math.floor((distanz*Math.cos(((grad*Math.PI)/180))/groese)+0.5)),(byte) (position[1] - Math.floor((distanz*Math.sin(((grad*Math.PI)/180))/groese)+0.5))};
		}
		return new byte[] {(byte)-1,(byte)-1};
	}
	
	private boolean outOfMap (byte x,byte y){
		if (x>felder||x<0||y>felder||y<0) return true;
		else return false;
	}

	@Override
	public void setHinderniss(int grad, int distanz) {
		byte [] feld = getFeld(grad,distanz);
		if(outOfMap(feld[0],feld[1]))return;
		byte loc = searchMap(feld[0],feld[1]);
		if(loc==-1) map.add(new Mapnode(feld[0],feld[1],(byte) 10));
		else {
			Mapnode node = map.get(loc);
			if((node.getZustand()/10)==1||(node.getZustand()/10)==11)return;
			node.setZustand((byte) (node.getZustand()+10));
		}
	}

	@Override
	public void setFeuer(int grad) { // akktuelle position rausnehmen
		byte[] feld = getFeld(grad,0);
		byte t = 0;
		for(byte[] loop = feld.clone();loop[0]==feld[0]&&loop[1]==feld[1];t++){
			loop = getFeld(grad,t);
		}
		for(;t!=255;t++){
			feld = getFeld(grad,t);
			if(outOfMap(feld[0],feld[1]))return;
			byte loc = searchMap(feld[0],feld[1]);
			if(loc>-1){
				map.get(loc).setZustand((byte) (map.get(loc).getZustand()+1));
				if(map.get(loc).getZustand()/10==1||map.get(loc).getZustand()/10==11)return;
				for(byte[] loop = feld.clone();loop[0]==feld[0]&&loop[1]==feld[1];t++){
					loop = getFeld(grad,t);
				}
			}else{
				map.add(new Mapnode(feld[0],feld[1],(byte) 1));
				for(byte[] loop = feld.clone();loop[0]==feld[0]&&loop[1]==feld[1];t++){
					loop = getFeld(grad,t);
				}
			}
			
		} 
	}

	@Override
	public int getSoll(int grad) {
		byte[] feld=position;
		int distanz=0;
		for(byte[] loop = feld.clone();loop[0]==feld[0]&&loop[1]==feld[1];distanz++){
			loop = getFeld(grad,distanz);
		}
		distanz=0;
		do{
			byte[] loop=feld;
			for(;loop[0]==feld[0]&&loop[1]==feld[1];distanz++){
				feld=getFeld(grad,distanz);
			}
			if (isFree(feld[0],feld[1])){
				loop=feld;
				for(;loop[0]==feld[0]&&loop[1]==feld[1];distanz++){
					feld=getFeld(grad,distanz);
				}
				return distanz--;
			}
		}while(!outOfMap(feld[0],feld[1]));
		if (feld[0]==-1&&feld[1]==-1||feld[0]==(felder+1)&&feld[1]==-1||feld[0]==(felder+1)&&feld[1]==(felder+1)||feld[0]==-1&&feld[1]==(felder+1)) return 255;
		return distanz;
		
	}
	
	@Override
	public boolean setPosition(int x, int y) {
		position[0] = (byte) x;
		position[1] = (byte) y;
		if(outOfMap((byte)x,(byte)y))return false;
		byte loc = searchMap (position[0],position[1]);
		if (loc==-1) map.add(new Mapnode(position[0],position[1],(byte)100));
		else{
			Mapnode node = map.get(loc);
			if((node.getZustand()/100)==1) return false;
			node.setZustand((byte) (node.getZustand()+100));
		}
		return true;
	}

	@Override
	public int getPosY() {
		return position[1];
	}

	@Override
	public int getPosX() {
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
	
	@Override
	public boolean isFree(int x, int y) {
		byte loc = searchMap((byte)x,(byte)y);
		if(loc>-1){
			Mapnode node = map.get(loc);
			if((node.getZustand()/10)==1||(node.getZustand()/10)==11)return true;
		}
		return false;
	}

	@Override
	public byte getFeuer(int x, int y) {
		byte loc = searchMap((byte)x,(byte)y);
		if(loc>-1){
			Mapnode node = map.get(loc);
			if((node.getZustand()%10)!=0)return (byte) (node.getZustand()%10);
		}
		return 0;
	}
	
	@Override
	public byte getlength(){
		return (byte) (felder+1);
	}
	
	@Override
	public String toString (){
		String output = "Apos: "+position[0]+"/"+position[1]+"\nFelder: "+(felder+1)+"\nGroe§e: "+groese+"\n";
		for (int i=0;i!=map.size();i++){
			if (i%5==0) Button.waitForPress();
			System.out.println(map.get(i).toString());
		}
		return output;
	}

}
