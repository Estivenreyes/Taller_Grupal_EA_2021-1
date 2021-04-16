package model;

import processing.core.PApplet;

public class Jugador {
	
	private PApplet app;
	private float posX;
	private float posY;
	private int matX ;
	private int matY;
	
	public Jugador (float posX, float posY, PApplet app) {
		this.posX = posX;
		this.posY = posY;
		this.matY=13;
		this.matX=0;
		this.app = app;
	}
	
	public void draw (int color) {
		if(color==1) {
			app.fill(255,0,255);
		}else{
			app.fill(0,255,255);
		}
		
		app.ellipse(posX, posY, 15, 15);
		

	}
	
	public void move (int[][] map , int dir) {
	

		
		switch(dir) {
		
		//derecha
		case(1):
			if( map[matY][matX+1] == 0||map[matY][matX+1]==2) {
				posX+=18;
				matX+=1;
				System.out.println(matY);
				System.out.println(matX);
			}
				
		break;	
			
		//arriba
		case(2):
			if( map[matY-1][matX] == 0|| map[matY-1][matX]==2) {
				posY-=18;
				matY-=1;
				System.out.println(matY);
				System.out.println(matX);
			}
		break;
				
		//abajo
		case(-2):
			if( map[matY+1][matX] == 0 ||  map[matY+1][matX]==2) {
				posY+=18;
				matY+=1;
			}
		break;
				
		//izquierda
		case(-1):			
			if( map[matY][matX-1] == 0 || map[matY][matX-1] == 2) {
				posX-=18;
				matX-=1;
			}	
		break;
					
				
			}
		}

	public int getMatX() {
		return matX;
	}

	public void setMatX(int matX) {
		this.matX = matX;
	}

	public int getMatY() {
		return matY;
	}

	public void setMatY(int matY) {
		this.matY = matY;
	}
	
		
	}


