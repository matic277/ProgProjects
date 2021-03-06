package Try;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Painter extends JPanel {
	
	JFrame frame;
	Listener l;
	
	// info and stats
	long iterations;
	double avgtime;
	static double totaltime;
	static double t1, t2 = 0;
	
	static DecimalFormat df;
	
	// users inputs
	boolean draw;
	boolean threaded;
	
	// panel size
	int width, height;
	static int cellsize;
	
	// grid of cells
	static int g[][];
	static int tg[][];
	static int n;
	static String gridSize;
	
	// repainting speed
	static int speed;
	
	// colors
	Color background;
	Color grid;
	Color alive, dead;
	
	// buttons
	static JButton pause;
	static JButton reset;
	static AtomicBoolean simulate;
	
	// labels
	JLabel leftinfo;
	JLabel rightinfo;
	static JLabel speedinfo;

	// slider for speed
	static JSlider slider;
	
	
	
	
	public Painter (int pCellSize, boolean pdraw, boolean pthreaded) {
		//n = pn;
		draw = pdraw;
		threaded = pthreaded;
		speed = 50;
		avgtime = 0;
		iterations = 0;
		totaltime = 0;
		
		t1 = t2 = 0;
		
		// start with paused state
		simulate = new AtomicBoolean(false);
		
		// preset grid to infinite loop
		GridXX.initGrid();
		gridSize = "XX";
		n = g.length;
		
		System.out.println("PAINTER G : "+g.length+"x"+g[0].length+" n: "+n);
		System.out.println("PAINTER TG: "+tg.length+"x"+tg[0].length+" n: "+n);
		
		// drawing size
		cellsize = pCellSize;
		
		// set up drawing panel if user wants to see simulation
		// otherwise just set up size so you can fit info and buttons
		if (draw) {
			width = 10 + 10 + n * cellsize;
			height = 100 + width - 10;
		} else {
			width = 600;
			height = 105;
		}
		
		l = new Listener(this);
		this.addMouseListener(l);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(width, height));
			
		background = new Color(250, 250, 250);
		grid = new Color(20, 20, 20);
		dead = new Color(230, 230, 230);
		alive = Color.yellow;
		
		// ad labels, buttons
		addComponents();
		
		frame = new JFrame("Grid size: "+n+" x "+n);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		Main.ref = this;
		
		df = new DecimalFormat("#.#");
        df.setMaximumFractionDigits(8);
		
		// run threads if wanted
		if (threaded) Main.initThreads();
	}
	
	public void simulate () {
		if (threaded) {
			// multi-threaded
			// don't do anything, barrier handles repainting
			return;
		}
		else {
			// single-threaded
			// if paused, stop redrawing jpanel
			// when unpaused, Listener will repaint
			// and handle simulating variable
			if (simulate.get()) {
				
				t1 = System.currentTimeMillis();
				Simulator.simulate();
			    t2 = System.currentTimeMillis();
				
				//System.out.println(t2-t1);
				
				double t = t2 - t1;
				double wt = speed - t;
				
				if (wt > 0) {
					System.out.println("sleeping for: "+(int)wt);
					try { Thread.sleep((int)wt); } 
					catch (InterruptedException e) { e.printStackTrace(); }
				}
				
				repaintPanel();
			}
		}
	}
	
	@Override
	protected void paintComponent(Graphics g){
		g.setColor(background);
		g.drawRect(0, 0, width, height);
		
		if (draw) {
			// draw grid of cells
			for (int i=0, y=100; i<n; i++, y+=cellsize)
			for (int j=0, x=10 ; j<n; j++, x+=cellsize) {
				if (this.g[i][j] == 1) {
					g.setColor(alive);
					g.fillRect(x, y, cellsize, cellsize);
				} else {
					g.setColor(dead);
					g.fillRect(x, y, cellsize, cellsize);
				}
				g.setColor(grid);
				
				// turn off when simulating xx or higher
				//g.drawRect(x, y, cellsize, cellsize);
			}
		}
		simulate();
	}
	
	TThread timer = new TThread();
	
	public void repaintPanel () {
		
		if (iterations == 5000) {
			simulate.set(false);
			System.out.println("totaltime:"+totaltime);
		}
		iterations++;

		totaltime = timer.getTime() / 1000; // covert to seconds
		avgtime = (totaltime / iterations);
		
		
		// System.out.println("TT:"+totaltime);
		//System.out.println("Total time spent    : "+df.format(totaltime));
		//System.out.println("Avg. itr. duratation: "+df.format(avgtime));
		
		updateLabel();
		super.repaint();
	}
	
	public void updateLabel () {
		rightinfo.setText(
				"<html>"
				+ simulate.get() + "<br>"
				+ Main.nthreads + "<br>"
				+ df.format(avgtime) + " ms<br>"
				+ iterations + ""
				+ "</html>"
		);
	}
	
	public void status () {
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++) 
				System.out.print(g[i][j]+" ");
			System.out.println();
		}
		System.out.println("-----------");
	}
	
	public void addComponents () {
		// buttons
		pause = new JButton("continue");
		pause.setBounds(width-10-100, 10, 100, 25);
		pause.addActionListener(l);
		
		reset = new JButton("reset");
		reset.setBounds(width-10-100, 40, 100, 25);
		reset.addActionListener(l);
		
		// slider
		slider = new JSlider();
		slider.setBounds(width-10-100, 70, 100, 25);
		slider.setOpaque(true);
		slider.setBackground(new Color(180,180,255));
		slider.addChangeListener(l);
		
		// labels
		leftinfo = new JLabel(
				"<html>"
				+ " Simulating: <br>"
				+ " Number of threads simulating: <br>"
				+ " Average time to compute one iteration: <br>"
				+ " Number of iterations: "
		);
		leftinfo.setBounds(10, 10, width/3, 100-15);
		leftinfo.setOpaque(true);
		leftinfo.setBackground(new Color(180,180,255));
		
		rightinfo = new JLabel("", SwingConstants.LEFT);
		rightinfo.setBounds((width/3)+10+5, 10, width/3, 100-15);	// start at: 10 + 5 -> (gap between left and right size), end at width/2 -> (width/4)
		rightinfo.setOpaque(true);
		rightinfo.setBackground(new Color(180,180,255));
		
		speedinfo = new JLabel(speed+" ms", SwingConstants.CENTER);
		speedinfo.setBounds(width-10-10-100-50, 70, 50, 25);
		speedinfo.setOpaque(true);
		speedinfo.setBackground(new Color(180,180,255));
		
		this.add(pause);
		this.add(reset);
		this.add(slider);
		this.add(leftinfo);
		this.add(rightinfo);
		this.add(speedinfo);
	}

}
