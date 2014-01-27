package horst;
import java.lang.Math;
import java.util.ArrayList;

import lejos.nxt.Button;

class Lokalisierung{
	private byte mX=-1;
	private byte mY=-1;
	private Map map;
	private Bewegungen bewegungen;
	
	public Lokalisierung(Map map,Bewegungen bewegungen) {
		this.map = map;
		this.bewegungen = bewegungen;
	}
	
	byte getDistance(){	// dist to next Wall in current Dir
		if (map.getPosRi()==Richtung.EAST){
			for (byte i=1; i < (map.getlength()-map.getPosX()); i++){
				if (map.isFree(map.getPosX()+i,map.getPosY())){	return (byte)(i-1);}
			}
		}
		if (map.getPosRi()==Richtung.SOUTH){
			for (byte i=1; i < (map.getlength()-map.getPosY()); i++){
				if (map.isFree(map.getPosX(),map.getPosY()+i)){	return (byte)(i-1);}
			}
		}
		return -1;
	}
	/**
	 * @return true, if fire can be located from existing scans
	 */
	boolean isFire(){
		byte mF=1;
		ArrayList <Koordinaten> coords=new ArrayList<Koordinaten>();
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
		return (mF>2);
	}
	/**
	 * Moves the robot to the next location for scanning.
	 */
	void nextStep(){
		Richtung dir = map.getPosRi();
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
			x=Math.min(((map.getPosX()+map.getlength())/2),getDistance());
			for (byte i=0; i<x;i++){
				w.add(Richtung.EAST);
			}
			goWay(w);
			return;
			
			case SOUTH:
			x=Math.min(((map.getPosY()+map.getlength())/2),getDistance());
			for (byte i=0; i<x;i++){
				w.add(Richtung.SOUTH);
			}
			goWay(w);
			return;
			
			case WEST:
			w.add(Richtung.WEST);
			goWay(w);
			return;
			
			case NORTH:
			w.add(Richtung.NORTH);
			goWay(w);
			return;
			
		}
	}
	void goWay(ArrayList<Richtung> d){
		if (d.size()==0){
			//change dir
		}
		else{
			
			Richtung ri[] = new Richtung[d.size()]; 
			bewegungen.goWay(d.toArray(ri));
		}
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