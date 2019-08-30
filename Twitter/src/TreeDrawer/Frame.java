package TreeDrawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import AbstractWordClasses.AbsMeasurableWord;
import AbstractWordClasses.AbsWord;
import Tokenizer.Tweet;
import Words.Acronym;
import Words.Hashtag;

public class Frame implements ComponentListener {
	
	Tweet tweet;
	
	JPanel panel;
	JFrame frame;
	
	int width, height;
	
	double datapointHeight;
	
	public Frame(Tweet tweet) {
		this.tweet = tweet;

		initPanel();
	}
	
	private void initPanel() {
		width = 800;
		height = 800;
		panel = new JPanel() {
			protected void paintComponent(Graphics g){
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, width, height);
				g.setColor(Color.black);
				draw(g);
			}
		};
		panel.setPreferredSize(new Dimension(width, height));
		panel.addComponentListener(this);
		frame = new JFrame();
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
	
	public void draw(Graphics g) {
		String tweet = "This is a 0x1f609 brand nice test tweet. lol www.url.si :) #OneTwo";
		Tweet t = new Tweet(tweet, null);
		t.processTweet();
		Root r = new Root(t.getTokens(), width, height, g);
		System.out.println(r.getMaxDepth());
		System.out.println(t.toString());
	}
	
	@Override
	public void componentResized(ComponentEvent event) {
		Dimension newSize = event.getComponent().getSize();
		this.width = newSize.width;
		this.height = newSize.height;
		this.panel.repaint();
	}
	
	public void componentHidden(ComponentEvent arg0) {}
	public void componentMoved(ComponentEvent arg0) {}
	public void componentShown(ComponentEvent arg0) {}
}

class DataPoint {
	
	String value;
	Rectangle pos;
	Rectangle bounds;
	
	ArrayList<DataPoint> pointers;
	
	int dataWidth = 50;
	int dataHeight = 30;
	
	public DataPoint(String value) {
//		System.out.println("token set " + value);
		this.value = value;
		this.pointers = new ArrayList<DataPoint>(3);
	}
	
	public void setPointers(String[] values) {
		for (String s : values) {
			pointers.add(new DataPoint(s));
		}
	}
	
	public int getMaxDepth() {
		int maxChildDepth = 0;
		for (DataPoint p : pointers) {
			int newDepth = p.getMaxDepth();
			if (newDepth > maxChildDepth) maxChildDepth = newDepth;
		}
		return maxChildDepth+1;
	}
	
	public void setPositions(Rectangle bounds) {
		this.bounds = bounds;
		this.pos = new Rectangle(
			(int)(bounds.getCenterX() - dataWidth/2),
			(int)(bounds.getCenterY() - dataHeight/2),
			dataWidth,
			dataHeight
		);

		if (pointers == null || pointers.isEmpty()) return;
	
		//double width = bounds.width / pointers.size();
		double height = bounds.height / pointers.size();
		
		for (int i=0; i<pointers.size(); i++) {
			DataPoint dt = pointers.get(i);
			double datapointWidth = bounds.width / pointers.size();
			double datapointHeight = Root.datapointHeight;
			double datapointX = i * datapointWidth + bounds.x;
			double datapointY = bounds.y + Root.datapointHeight;
			dt.setPositions(new Rectangle((int)datapointX, (int)datapointY, (int)datapointWidth, (int)datapointHeight));
		}
	}
	
	public void draw (Graphics g) {
		System.out.println("drawing: " + value);
		Random r = new Random();
		g.setColor(new Color(125+r.nextInt(125), 125+r.nextInt(125), 125+r.nextInt(125)));
		g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
		
		g.setColor(Color.black);
		g.drawRect(pos.x, pos.y, pos.width, pos.height);
		g.drawString(value, pos.x+2, pos.y+(pos.height/2));
		
		g.fillRect(pos.x, pos.y, 5, 5);
		
				
		if (pointers == null || pointers.isEmpty()) return;

		for (DataPoint d : pointers) d.draw(g);
	}
	
}

class Root {
	
	Rectangle bounds;
	Rectangle pos;
	
	ArrayList<DataPoint> pointers;
	
	static double datapointHeight;
	
	public Root(ArrayList<AbsWord> words, int width, int height, Graphics g) {
		bounds = new Rectangle();
		pos = new Rectangle(width/2-25, 50, 50, 30);
		pointers = new ArrayList<DataPoint>(words.size());
		
		
		// some have +"" at getProcessedText() calls, because Emojis and URLS dont get processed and return null
		// its a problem later
		int ii = 0;
		for (AbsWord w : words) {
			if (w instanceof Acronym) {
				pointers.add(new DataPoint(w.getTag()));
				Acronym ac = (Acronym) w;
				pointers.get(ii).setPointers(new String[] {ac.getSourceText(), ac.getProcessedText(), ac.getPleasantness()+""});
				pointers.get(ii).pointers.get(1).setPointers(ac.getListOfWords());
			} else if (w instanceof Hashtag) {
				pointers.add(new DataPoint(w.getTag()));
				Hashtag ac = (Hashtag) w;
				pointers.get(ii).setPointers(new String[] {ac.getSourceText(), ac.getProcessedText(), ac.getPleasantness()+""});
				pointers.get(ii).pointers.get(1).setPointers(ac.getListOfWords());
			} else if (w instanceof AbsMeasurableWord) {
				pointers.add(new DataPoint(w.getTag()));
				AbsMeasurableWord ac = (AbsMeasurableWord) w;
				pointers.get(ii).setPointers(new String[] {ac.getSourceText(), ac.getProcessedText()+"", ac.getPleasantness()+""});
			} else { // AbsWords
				pointers.add(new DataPoint(w.getTag()));
				pointers.get(ii).setPointers(new String[] {w.getSourceText(), w.getProcessedText()+""});
			}
			ii++;
		}
		
		int numberOfLayers = getMaxDepth();
		
		datapointHeight = height / numberOfLayers;
		
		for (int i=0; i<pointers.size(); i++) {
			DataPoint dt = pointers.get(i);
			double datapointWidth = width / pointers.size();
			double datapointX = i * datapointWidth;
			double datapointY = datapointHeight;
			dt.setPositions(new Rectangle((int)datapointX, (int)datapointY, (int)datapointWidth, (int)datapointHeight));
		}
		
		System.out.println(pos.toString());
		
		g.drawRect(pos.x, pos.y, pos.width, pos.height);
		g.drawString("ROOT", pos.x+2, pos.y+(pos.height/2));
		
		for (DataPoint d : pointers) {
			System.out.println("calling to token: " + d.value);
			d.draw(g);
		}
	}
	
	public int getMaxDepth() {
		int maxChildDepth = -1;
		for (DataPoint p : pointers) {
			int newDepth = p.getMaxDepth();
			if (newDepth > maxChildDepth) maxChildDepth = newDepth;
		}
		return maxChildDepth+1;
	}
}



















