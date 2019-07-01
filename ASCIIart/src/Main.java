import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.imageio.ImageIO;

public class Main {
	
	static String outputSrc = "Resources/JavaOutput.txt";
	static ImageLoader r;
	static Writer w;
	
	static char c1 = 'I';
	static char c2 = 'L';
	static char c3 = 'S';
	static char c4 = 'E';
	static char c5 = 'K';
	static char c6 = 'W';
	
	static char chars[] = {
			'.', ';', '-', '"',
			'*', '(', '=', 'r',
			'^', 'I', 's', 'L',
			'x', 'T', 'e', '2',
			'5', 'o', 'C', 'Z',
			'X', 'p', 'd', 'w',
			'q', 'R', 'N', 'M',
	};
	
	public static void main(String[] args) {
		initFileWriter();
		loadImage("bullet1.png");
		
		int h = r.image.getHeight();
		int w = r.image.getWidth();
		
//		String output[] = new String[h];
//		for (int i=0; i<output.length; i++) output[i] = "";
//		
//		for (int i=0; i<h; i++) {
//			for (int j=0; j<w; j++) {
//				output[i] += evalPixel(r.image.getRGB(j, i));
//			}
//		}
		
		for (int i=0; i<h; i++) {
			for (int j=0; j<w; j++) {
				r.image.setRGB(j, i, evalPixel4(r.image.getRGB(j, i)));
			}
		}
		
//		printOutput(output);
//		writeOutput(output);
		saveImage();
	}
	
	public static void saveImage() {
		try {
			BufferedImage bimage = new BufferedImage(r.image.getWidth(null), r.image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		    Graphics2D bGr = bimage.createGraphics();
		    bGr.drawImage(r.image, 0, 0, null);
		    bGr.dispose();
		    
		    File outputfile = new File("Resources/output.png");
		    ImageIO.write(r.image, "png", outputfile);
		} catch (Exception e) {
			e.printStackTrace();
		}   
	}

	private static void initFileWriter() {
		try { w = new FileWriter(outputSrc); }
		catch (IOException e) { e.printStackTrace(); }
	}

	private static void loadImage(String imgName) {
		r = new ImageLoader();
		r.loadImage(imgName);
	}
	
	public static void writeOutput(String output[]) {
		String newLine = System.getProperty("line.separator");
		try {
			for (int i=0; i<output.length; i++) {
				w.write(output[i]);
				w.write(newLine);
			}
			w.flush();
			w.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static double maxBrightness = 255 * 3;
	static double midBrightness = maxBrightness / 2;
	static double scaleFactor = 1; // 1 for no additional effect, > 1 for more effect, < 1 for less effect
	
	public static int evalPixel2(int pixel) {
//		int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    
	    double newRed, newBlue, newGreen;
	    
	    int sumOfClrs = red + green + blue;
	    double scale = 1;
	    
	    if (sumOfClrs >= midBrightness) {
	    	// brighten
	    	scale =  scaleFactor * (sumOfClrs / (maxBrightness - midBrightness));
	    	//System.out.println(scale);
	    	
	    	newRed = red * scale;
	    	newBlue = blue * scale;
	    	newGreen = green * scale;
	    } else {
	    	// darken
	    	scale = scaleFactor * (sumOfClrs / (midBrightness));
	    	//System.out.println(scale);
	    	
	    	newRed = red * scale;
	    	newBlue = blue * scale;
	    	newGreen = green * scale;	    	
	    }
	    
	    return new Color(
	    	(int)((newRed > 255)? 255 : newRed),
	    	(int)((newGreen > 255)? 255 : newGreen),
	    	(int)((newBlue > 255)? 255 : newBlue)
	    ).getRGB();
	}
	
	
	public static int evalPixel3(int pixel) {
//	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;

	    int avg = (red + green + blue) / 3;
	    
//	    red = blue = green = (int)avg;
	    
	    return new Color(avg, avg, avg).getRGB();
	}
	
	public static int evalPixel4(int pixel) {
	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    
	    
	    if (red > green && red > blue) {
	    	return new Color(blue, green, red, alpha).getRGB();
	    }
	    
//	    red = blue = green = (int)avg;
	    
	    return new Color(red, green, blue, alpha).getRGB();
	}
	
	
	
	public static char evalPixel(int pixel) {
	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;

	    int total = red + green + blue;
	    int chunk = (255 + 255 + 255) / chars.length;
	    
	    for (int i=0; i<chars.length; i++) if (total < chunk*i) return chars[i];
	    
	    return ' ';
	}

	
	
	public static void printOutput(String output[]) {
		for (int i=0; i<output.length; i++) System.out.println(output[i]);
	}
	
	public static void printPixel(int pixel) {
	    int alpha = (pixel >> 24) & 0xff;
	    int red = (pixel >> 16) & 0xff;
	    int green = (pixel >> 8) & 0xff;
	    int blue = (pixel) & 0xff;
	    System.out.print("(" + alpha + ", " + red + ", " + green + ", " + blue + ") ");
	}
}
