import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class Main {
	
	static String outputSrc = "C:/Users/V4/Desktop/JavaOutput.txt";
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
		init();
		
		int h = r.image.getHeight();
		int w = r.image.getWidth();
		
		String output[] = new String[h];
		for (int i=0; i<output.length; i++) output[i] = "";
		
		for (int i=0; i<h; i++) {
			for (int j=0; j<w; j++) {
				output[i] += evalPixel(r.image.getRGB(j, i));
			}
		}
		
		//printOutput(output);
		writeOutput(output);
	}

	private static void init() {
		try { w = new FileWriter(outputSrc); }
		catch (IOException e) { e.printStackTrace(); }
		r = new ImageLoader();
		r.loadImage("monkey.jpeg");
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

}
