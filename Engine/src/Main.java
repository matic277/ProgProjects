import java.awt.Dimension;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import Engine.Engine;
import Units.Player;
import Units.Unit;

import javax.sound.sampled.*;
import Engine.MediaPlayer;


public class Main {

	public static void main(String[] args) throws Exception {
		Engine e = new Engine(new Dimension(1000, 800));
		new Thread(e).start();

//		File f = new File("./Resources/sounds/sf_enemybullet.wav");
//		MediaPlayer media = new MediaPlayer(f.getAbsolutePath());
//		media.setVolume(0.2F);
//		new Thread(media).start();

	}
}
