package classifierTester;

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

import Tokenizer.Tweet;

public class DataVisualizer implements ComponentListener {
	
	JFrame frame;
	JPanel panel;
	
	int width = 2000;
	int height = 1000;
	
	ArrayList<ProcessedTweet> list;
	
	static Random r = new Random();
	
	
	public DataVisualizer(ArrayList<ProcessedTweet> tweets) {
		list = new ArrayList<ProcessedTweet>(tweets.size());

		tweets.forEach(t -> {
			if (Math.abs(t.tweet.getSentimentValue()) > 5) return;
			if (t.annotatedSentiment != 0) list.add(t);
		});
		
		int positiveLeft = 0;
		int positiveRight = 0;
		int negativeLeft = 0;
		int negativeRight = 0;
		for (ProcessedTweet t : list) {
			if (t.tweet.getSentimentValue() > Tweet.threshold) {
				if (t.annotatedSentiment == 1) positiveRight++;
				else negativeRight++;
			} else {
				if (t.annotatedSentiment == 1) positiveLeft++;
				else negativeLeft++;
			}
		}
		
		System.out.println("POS: " + positiveLeft + ", " + positiveRight);
		System.out.println("NEG: " + negativeLeft + ", " + negativeRight);
		
		System.out.println("r -> " + (double)(positiveRight+negativeLeft) / (negativeRight+positiveLeft+positiveRight+negativeLeft));
		
		initPanel();
	}
	
	private void initPanel() {
		panel = new JPanel() {
			protected void paintComponent(Graphics g){
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
	
	private void draw(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, width, height);
		
		int lineWidth = width - 100;
		int lineHeight = 5;
		int lineStartCoordinateX = 50;
		int lineStartCoordinateY = height - 50 - lineHeight;
		
		// get the tweet with smallest sentiment value
		// same for the biggest
		double smallestValue = 0;
		double biggestValue = 0;
		ProcessedTweet smallestTweet = null;
		ProcessedTweet biggestTweet = null;
		
		for (ProcessedTweet t : list) {
			if (t.tweet.getSentimentValue() < smallestValue) {
				smallestValue = t.tweet.getSentimentValue();
				smallestTweet = t;
			}
			if (t.tweet.getSentimentValue() > biggestValue) {
				biggestValue = t.tweet.getSentimentValue();
				biggestTweet = t;
			}
		}
		
		System.out.println("Smallest sentiment: " + smallestValue);
		System.out.println("Biggest sentiment: " + biggestValue);
		
		int numberOfSteps = 1 + (int) Math.round((Math.abs(biggestValue) + Math.abs(smallestValue)));
		
		System.out.println(" -> steps: " + numberOfSteps);
		
		int markerWidth = 3;
		int markerHeight = 6;
		
		final double markerSpacing = lineWidth / (numberOfSteps -1);
		
		Rectangle[] markers = new Rectangle[numberOfSteps];
		int[] markerValues = new int[numberOfSteps];
		
		for (int i=0, x=lineStartCoordinateX, y=(lineStartCoordinateY-markerHeight); i<markers.length; i++) {
			markers[i] = new Rectangle(
				(x + (int)(i * markerSpacing)),
				y,
				markerWidth,
				markerHeight
			);
		}
			
		markerValues[0] = (int) Math.round(smallestValue);
		for (int i=1; i<markerValues.length; i++) markerValues[i] = markerValues[i-1] + 1;

		
		//drawing
		g.setColor(Color.black);
		g.fillRect(lineStartCoordinateX, lineStartCoordinateY, lineWidth, lineHeight);
		for (Rectangle r : markers) g.fillRect(r.x, r.y, r.width, r.height);
		for (int i=0; i<markerValues.length; i++) 
			g.drawString(
				markerValues[i]+"",
				markers[i].x + 2,
				markers[i].y - 10
			);
		
		final int dataMaxHeight = 100;
		final int dataMinHeight = height - 150;
		final int dataPos = dataMinHeight - dataMaxHeight;
		
		final int dataPointSize = 3;
		
		for (ProcessedTweet t : list) {
			double calculatedSentiment = t.tweet.getSentimentValue();
			double annotatedSentiment = t.annotatedSentiment;
			
			// find closest LEFT marker to sentiment
			int closestInx = -1;
			double closestInt = (int) calculatedSentiment;

			for (int i=0; i<markerValues.length; i++) {
				if (closestInt == markerValues[i]) closestInx = i;
			}
			if (closestInx == -1) throw new Error("ERROR: Error at processing min distance.");
			
//			if (closestInx+1 >= markers.length) return; // lazy			
			double x1 = markers[closestInx].x;
//			double x2 = markers[closestInx+1].x;
			
			double fraction = calculatedSentiment - ((int)(calculatedSentiment));
			double spacingX = fraction * markerSpacing;
			double finalXposition = spacingX + x1;
			
			double finalYposition = dataMinHeight - (r.nextDouble() * dataPos);
			
			Rectangle dataPoint = new Rectangle(
				(int) finalXposition,
				(int) finalYposition,
				dataPointSize,
				dataPointSize
			);
			
			g.setColor((annotatedSentiment == -1)? Color.red : Color.green);
			g.fillRect(dataPoint.x, dataPoint.y, dataPoint.width, dataPoint.height);
		};
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
