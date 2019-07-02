package Graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class ResourceLoader {
	
	private Image playerImage;
	private Image bulletImage;
	private Image missileImage;
	private Image enemyImage;
	private Image enemyBulletImage;
	private Image asteroidImage;
	
	private Image dragonHead, dragonBody, dragonTail;
	
	//private Clip clip;
	
	String musicLocation = "test.wav";
	
	public ResourceLoader() {
		playerImage = loadImage("rocket.png");
		playerImage = playerImage.getScaledInstance(60, 35, 1);
		
		asteroidImage = loadImage("asteroid1.png");
		asteroidImage = asteroidImage.getScaledInstance(50, 50, 1);
		
		bulletImage = loadImage("bullet1.png");
		bulletImage = bulletImage.getScaledInstance(30, 8, 1);
		
		enemyBulletImage = loadImage("enemyBullet.png");
		enemyBulletImage = enemyBulletImage.getScaledInstance(30, 8, 1);
		
		missileImage = loadImage("missile1.png");
		missileImage = missileImage.getScaledInstance(25, 18, 1);
		
		enemyImage = loadImage("enemy.png");
		enemyImage = enemyImage.getScaledInstance(40, 30, 1);
		
		// ---
		dragonHead = loadImage("dragon/dragon_head1.png");
		dragonHead = dragonHead.getScaledInstance(50, 50, 1);
		
		dragonBody = loadImage("dragon/dragon_body1.png");
		dragonBody = dragonBody.getScaledInstance(50, 50, 1);
		
		dragonTail = loadImage("dragon/dragon_tail1.png");
		dragonTail = dragonTail.getScaledInstance(50, 50, 1);
		// ---
		
		//loadClip();
	}
	
	public static BufferedImage loadImage(String fileName) {
		BufferedImage img = null;
		try {
			System.out.print("Loading resource: 'Resources/" + fileName + "' ... ");
			img = ImageIO.read(new File("Resources/" + fileName));
			System.out.println("done.");
		}
		catch (IOException e) { e.printStackTrace(); }
		return img;
	}
	
	public Image getPlayerImage() {
		return playerImage;
	}
	
	public Image getBulletImage() {
		return bulletImage;
	}
	
	public Image getMissileImage() {
		return missileImage;
	}

	public Image getEnemyImage() {
		return enemyImage;
	}
	
	public Image getEnemyBulletImage() {
		return enemyBulletImage;
	}
	
	public Image getDragonHeadImage() {
		return dragonHead;
	}
	
	public Image getDragonBodyImage() {
		return dragonBody;
	}
	
	public Image getDragonTailImage() {
		return dragonTail;
	}

	public Image getAsteroidImage() {
		return asteroidImage;
	}
	
//	public void loadClip() {
//		try {
//			File musicPath = new File(musicLocation);
//			AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
//			this.clip = AudioSystem.getClip();
//			this.clip.open(audioInput);
//			
//			//clip.start();
//	
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public Clip getClip() {
//		return clip;
//	}
}
