package horst;
import java.lang.Math;
import java.util.ArrayList;


class Lokalisierung{
	private Richtung dir;
	private ArrayList<Koordinaten> coords;
	private byte mX=-1;
	private byte mY=-1;
	private Map map;
	private Bewegungen bewegungen;
	
	public Lokalisierung(Map map,Bewegungen bewegungen) {
		this.map = map;
		this.bewegungen = bewegungen;
		dir = map.getPosRi();
	}
	
	byte getDistance(Richtung d){	// dist to next Wall in current Dir
		if (d==Richtung.EAST){
			for (byte i=1; i < (map.getlength()-map.getPosX()); i++){
				Koordinaten coord=new Koordinaten(i, (byte)map.getPosY());
				if (map.isFree(map.getPosX()+i,map.getPosY())||coords.contains(coord)){	return (byte)(i-1);}
			}
		}
		if (d==Richtung.SOUTH){
			for (byte i=1; i < (map.getlength()-map.getPosY()); i++){
				Koordinaten coord=new Koordinaten((byte)map.getPosY(),i);
				if (map.isFree(map.getPosX(),map.getPosY()+i)||coords.contains(coord)){	return (byte)(i-1);}
			}
		}
		return -1;
	}
	/**
	 * @return true, if fire can be located from existing scans
	 */
	boolean isFire(){
		dir =map.getPosRi();
		byte mF=1;
		coords=new ArrayList<Koordinaten>();
		for (byte x=0; x<map.getlength();x++){
			for(byte y=0; y<map.getlength();y++){
				if (mF<map.getFeuer(x,y) ){
					mF=map.getFeuer(x,y);
					mX=x;
					mY=y;
					coords.clear();
				}
				if (mF==map.getFeuer(x,y)){
					Koordinaten a=new Koordinaten(x, y);
					coords.add(a);
				}
			}
		}
		for (byte i=0; i<coords.size(); i++){
			if((coords.get(i).getX()-1)>mX||(coords.get(i).getX()+1)<mX||(coords.get(i).getY()-1)>mY||(coords.get(i).getY()+1)<mY||coords.size()>5){
				mX=-1;
				mY=-1;
				return false;
			}
		}
		if(mF>2){System.out.println("x="+mX+" y="+mY);}
		return (mF>2);
	}
	/**
	 * Moves the robot to the next location for scanning.
	 */
	void nextStep(){
		int x=0;
		ArrayList<Richtung> w = new ArrayList<Richtung>();
		// dir==South
		// X=0.5(getY+maxLength)		[S,...,S]
	
		// dir==East
		// X=0.5(getX+maxLength)		[E,...,E]
		
		// dir==North
		// [N]
		
		// dir==West
		// [W]
		switch(dir){
					//@toDo: maximum==0 testen, mit go Way
			case EAST:
				x=Math.min(((map.getPosX()+map.getlength())/2),getDistance(dir));
				if(map.getPosX()==(map.getlength()+1)/*+1 ?*/){dir=changeDir(dir);nextStep();return;}
				if (x==0){dir=changeDir(dir);nextStep();return;}
				for (byte i=0; i<x;i++){
					w.add(dir);
				}
				goWay(w);
				return;
			
			case SOUTH:
				x=Math.min(((map.getPosY()+map.getlength())/2),getDistance(dir));
				if(map.getPosY()==(map.getlength()+1)){dir=changeDir(dir);nextStep();return;}
				if (x==0){dir=changeDir(dir);nextStep();return;}
				for (byte i=0; i<x;i++){
					w.add(dir);
				}
				goWay(w);
				return;
			
			case WEST:
				if(map.getPosX()==0){dir=changeDir(dir);nextStep();return;}
				if (!map.isFree(map.getPosX()-1,map.getPosY())){dir=changeDir(dir);nextStep();return;}
				w.add(dir);
				goWay(w);
				return;
			
			case NORTH:
				if(map.getPosY()==0){dir=changeDir(dir);nextStep();return;}
				if (!map.isFree(map.getPosX(),map.getPosY()-1)){dir=changeDir(dir);nextStep();return;}
				w.add(dir);
				goWay(w);
				return;
			
		}
	}
	void goWay(ArrayList<Richtung> d){
			Richtung ri[] = new Richtung[d.size()]; 
			bewegungen.goWay(d.toArray(ri));
	}
	private Richtung changeDir(Richtung d){
		switch (d){
		case WEST:
			return Richtung.NORTH;
		case NORTH:
			return Richtung.EAST;
		case EAST:
			return Richtung.SOUTH;
		case SOUTH:
			return Richtung.WEST;
		}
		return null;
	}
	/**
	 * Only called if isFire==true. Moves the robot towards fire.
	 */
	void stepToFire(){
		//Dir[] d = findWay(mX,mY)
	}
}
class Koordinaten{
	byte x,y;
	Koordinaten(byte x, byte y){
		this.x=x;
		this.y=y;
	}
	byte getX(){
		return x;
	}
	byte getY(){
		return y;
	}
}