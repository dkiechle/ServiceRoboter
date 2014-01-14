package horst;

import java.util.ArrayList;
import java.util.List;

public class Map implements IMap {
	
	private List<Mapnode> map;
	private byte[] position;
	private byte grš§e;
	private byte felder;
	private Richtung richtung;

	public Map (byte felder,byte grš§e){
		map = new ArrayList<Mapnode>();
		position = new byte[] {0, 0}; //x,y
		this.grš§e = grš§e;
		this.felder = (byte) (felder-1);
	}
	
	private byte searchMap (byte x,byte y){
		for(byte i=0; i!=(byte)map.size();i++){
			if(map.get(i).getX()==x&&map.get(i).getY()==y) return (byte) map.indexOf(map.get(i));
		}
		return -1;
	}
	
	private byte[] getFeld (int grad,int distanz){
		byte x;
		byte y;
		switch (grad/90) {
		case 0:
			x = (byte) (position[0] + Math.floor((distanz*Math.cos(((grad*Math.PI)/180))/grš§e)+0.5));
			y = (byte) (position[1] - Math.floor((distanz*Math.sin(((grad*Math.PI)/180))/grš§e)+0.5));
			return new byte[] {x,y};
			
		case 1:
			grad = grad-90;
			y = (byte) (position[1] - Math.floor((distanz*Math.cos(((grad*Math.PI)/180))/grš§e)+0.5));
			x = (byte) (position[0] - Math.floor((distanz*Math.sin(((grad*Math.PI)/180))/grš§e)+0.5));
			return new byte[] {x,y};
			
		case 2:
			grad = grad-180;
			x = (byte) (position[0] - Math.floor((distanz*Math.cos(((grad*Math.PI)/180))/grš§e)+0.5));
			y = (byte) (position[1] + Math.floor((distanz*Math.sin(((grad*Math.PI)/180))/grš§e)+0.5));
			return new byte[] {x,y};
			
		case 3:
			grad = grad-270;
			y = (byte) (position[1] + Math.floor((distanz*Math.cos(((grad*Math.PI)/180))/grš§e)+0.5));
			x = (byte) (position[0] + Math.floor((distanz*Math.sin(((grad*Math.PI)/180))/grš§e)+0.5));
			return new byte[] {x,y};
		}
		return new byte[] {(byte)-1,(byte)-1};
	}
	
	private boolean atFeld (byte x,byte y){
		if (x>felder||x<0||y>felder||y<0) return false;
		else return true;
	}

	@Override
	public void setWall(int grad, int distanz) {
		byte [] feld = getFeld(grad,distanz);
		byte loc = searchMap(feld[0],feld[1]);
		if(loc==-1) map.add(new Mapnode(feld[0],feld[1],(byte) 10));
		else {
			Mapnode node = map.get(loc);
			if((node.getZustand()/10)==1||(node.getZustand()/10)==11)return;
			node.setZustand((byte) (node.getZustand()+10));
		}
	}

	@Override
	public void setLight(int grad, int distanz) {
		byte[] feld;
		byte t = 0;
		for(;t!=distanz;t++){
			feld = getFeld(grad,t);
			if(!atFeld(feld[0],feld[1]))return;
			byte loc = searchMap(feld[0],feld[1]);
			if(loc>-1){
				if(map.get(loc).getZustand()/10==1||map.get(loc).getZustand()/10==11)return;
				map.get(loc).setZustand((byte) (map.get(loc).getZustand()+1));
				for(byte[] loop = feld.clone();loop[0]==feld[0]&&loop[1]==feld[1];t++){
					loop = getFeld(grad,t);
				}
				t--;
			}else{
				map.add(new Mapnode(feld[0],feld[1],(byte) 1));
				for(byte[] loop = feld.clone();loop[0]==feld[0]&&loop[1]==feld[1];t++){
					loop = getFeld(grad,t);
				}
				t--;
			}
			
		} 
	}

	@Override
	public int getSoll(int grad) {
		return 0;
	}

	@Override
	public boolean setPosition(int row, int col) {
		position[0] = (byte) col;
		position[1] = (byte) row;
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
	@Override
	public boolean getWall(int x, int y) {
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
	public String toString (){
		String output = "Apos: "+position[0]+"/"+position[1]+"\nFelder: "+(felder+1)+"\nGroe§e: "+grš§e+"\n";
		for (int i=0;i!=map.size();i++){
			output = output+map.get(i).toString()+"\n";
		}
		return output;
	}

}
