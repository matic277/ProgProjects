import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageLoader {
	
	BufferedImage image;
	
	public void loadImage(String src) {
		try { image = ImageIO.read(new File("Resources/" + src)); }
		catch (IOException e) { e.printStackTrace(); }
	}




}
