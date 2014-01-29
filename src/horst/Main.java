/**
 * 
 */
package horst;

import lejos.nxt.Button;



/**
 * @author Daniel Kiechle
 *
 */
public class Main {
	
	/**
	 * @param args
	 * @return 
	 */
	
	
	public static void main(String[] args) {
		Map map;
		Sensoren sensoren;
		Bewegungen bewegungen;
		Lokalisierung lokalisierung;
		
		map = new Map((byte)10,(byte)20);
		bewegungen = new Bewegungen(map);
		sensoren = new Sensoren(map,bewegungen);
		lokalisierung = new Lokalisierung(map,bewegungen);
		
		/*for(int i = 0;!lokalisierung.isFire();i++){
			try {
				sensoren.messen();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			lokalisierung.nextStep();
			System.out.println(i);
		}
			lokalisierung.stepToFire();
			if(lokalisierung.isFire())System.out.println("FERTIG");
			//sensoren.senstest();

			Button.waitForPress();
			System.out.println();
			*/
			//LÖSCHEN
			int abortCount = 0;
			int distance;
			while (abortCount < 5){
				
				if (sensoren.turnToMax() == false){
					abortCount++;
				}
				else {
					abortCount = 0;
				}
				if (abortCount == 3){
					bewegungen.turn(30 + Math.random()*100);
				}
				distance = sensoren.getDistance();
				
				while(distance <25 && !(distance<200)){
					bewegungen.turn(90);
					distance = sensoren.getDistance();
					
				}
				
					double distToDrive = ((distance/3)*2);
					
					
					bewegungen.move(Math.max(distToDrive,25));
					bewegungen.move(-7);
				
					
			}
			Button.waitForPress();
		}
	
	
		
	
	
		
	}

