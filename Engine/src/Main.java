import java.awt.Dimension;

import Engine.Engine;


public class Main {

	public static void main(String[] args) {
		Engine e = new Engine(new Dimension(800, 600));
		new Thread(e).start();
		
		//ResourceLoader rl = new ResourceLoader();
		
		//Player p = new Player(new Vector(600, 400), new Rectangle(600, 400, 30, 30), new SimpleRenderer());
		playmusic("test.wav");
	}
	
	public static void playmusic(String musicLocation) {
		

	}

}
