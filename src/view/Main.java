package view;

import java.util.ArrayList;

import com.google.gson.Gson;

import communicaton.Coordenada;
import communicaton.OnListener;
import communicaton.TCPConection1;
import communicaton.TCPConection2;
import model.Jugador;
import model.Logic;
import model.Matriz;
import processing.core.PApplet;
import processing.core.PImage;

public class Main extends PApplet implements OnListener{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(Main.class.getName());
	}
	
	int screen;
	int[][]matrix;
	Matriz matriz;
	Jugador player1;
	Jugador player2;
	PImage inicio, instrucciones, bg;
	ArrayList<Jugador> player = new ArrayList<Jugador>();
	Logic logic;
	
	Gson gson = new Gson();
	
	TCPConection1 tcp1;	
	TCPConection2 tcp2;
	int dir1;
	int dir2;
	String winner;
	
	public void settings () {
		size(1280,720);
	}
	
	public void setup() {
		frameRate(15);
		matriz = new Matriz(this);
		
		tcp1 = TCPConection1.getInstance();
		tcp2 = TCPConection2.getInstance();
		
		tcp1.setObserver(this);
		tcp2.setObserver(this);
		
		screen=0;
		winner="";
		
		logic = new Logic();
		player1 = new Jugador(360, 268, this);
		player2 = new Jugador(360, 268, this);

		inicio = loadImage("./data/inicio.png");
		instrucciones = loadImage("./data/instrucciones.png");
		bg = loadImage("./data/bg.png");
		
		dir1=0;
		dir2=0;
		
	}
	
	public void draw() {
		background(15,15,59);
		
		if (screen == 0) {
			image(inicio, 0, 0);
		}
		
		if(screen == 1) {
			image(instrucciones, 0, 0);
		}
		
		if (screen == 2) {
			image(bg, 0, 0);
			String ip = logic.getIp();
			textSize(24);
			text(ip, 550,450);
			text("puerto 24",350,550);
			text("puerto 25", 850,550);
			
			if (tcp1.isConnected()&&tcp2.isConnected()) {
				
				screen=3;
			}
			
		}
		
		
		if(screen == 3) {
			
			matriz.drawMatrix();
			player1.draw(1);
			player2.draw(2);
			
			
			try{
				player1.move(matriz.getMatrix(), dir1);
				player2.move(matriz.getMatrix(), dir2);
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			if(player1.getMatY()==36 && player1.getMatX()==31){
				winner="Ganó el player 1";
				screen=4;
			}
			
			if(player2.getMatY()==36 && player2.getMatX()==31){
				winner="Ganó el player 2";
				screen=4;
			}
			
		
		}
		
		if(screen==4) {
			image(bg, 0, 0);
			textSize(24);
			text(winner, 550,450);
		}
		
	}
	
	
	public void mousePressed() {
		switch(screen) {
		case 0:
			if (mouseX > 567 && mouseX < 712 && mouseY > 497 && mouseY < 549) {
				screen = 2;
			}else if (mouseX > 567 && mouseX < 712 && mouseY > 574 && mouseY < 627) {
				screen = 1;
			}
		
		case 1:
			if (mouseX > 79 && mouseX < 134 && mouseY > 49 && mouseY < 83) {
				screen = 0;
			}
		break;
		
		case 2:
			
		break;
		

		}
	}

	@Override
	public void onMessage(int player, String message) {
		String action;
		Coordenada coor = gson.fromJson(message, Coordenada.class);
		
		action=coor.getAccion();
		
		
		switch(player) {
		case 1:
			switch(action) {
			
			case("RIGHTSTART"):
				
				dir1=1;
				break;
				
			case("LEFTSTART"):
				
				dir1=-1;
			
				break;
				
				
			case("UPSTART"):
				
				dir1=2;
			
				break;
				
				
			case("DOWNSTART"):
				
				dir1=-2;
			
				break;
			}
			
			if(action.contains("STOP")) {
				
				dir1=0;
			}
			
			
			break;
			
			
		case 2:
			
			switch(action) {
			
			case("RIGHTSTART"):
				
				dir2=1;
				break;
				
			case("LEFTSTART"):
				
				dir2=-1;
			
				break;
				
				
			case("UPSTART"):
				
				dir2=2;
			
				break;
				
				
			case("DOWNSTART"):
				
				dir2=-2;
			
				break;
			}
			
			if(action.contains("STOP")) {
				
				dir2=0;
			}
			
			
			break;
			
			
		}
		
		
	}
	
	public void keyPressed() {
		
		try {
			player1.move(matriz.getMatrix(), keyCode);
		}catch(Exception e){
			
		}
		
		
		
	}

}
