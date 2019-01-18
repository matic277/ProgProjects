package Try;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Menu extends JPanel {
	
	static JFrame frame;
	
	static JTextField sizeinput;
	static JButton threadedinput;
	static JButton drawinput;
	
	private JLabel sizeinfo;
	private JLabel threadinfo;
	private JLabel drawinfo;
	
	static JButton run;
	
	Listener l;
	
	public Menu() {
		this.setLayout(null);
		this.setPreferredSize(new Dimension(375, 240));
		l = new Listener(null);
		
		setcomponents();
		
		frame = new JFrame();
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	}
	
	private void setcomponents () {
		// labels
		sizeinfo = new JLabel("Size of cells: ");
		sizeinfo.setBounds(50, 50, 140, 30);
		sizeinfo.setOpaque(true);
		sizeinfo.setBackground(new Color(200, 100, 100));
		
		threadinfo = new JLabel("Enable multithreading: ");
		threadinfo.setBounds(50, 100, 140, 30);
		threadinfo.setOpaque(true);
		threadinfo.setBackground(new Color(100, 200, 100));
		
		drawinfo = new JLabel("Draw grid of cells: ");
		drawinfo.setBounds(50, 150, 140, 30);
		drawinfo.setOpaque(true);
		drawinfo.setBackground(new Color(100, 100, 200));
		
		
		// inputs
		sizeinput = new JTextField("");
		sizeinput.setBounds(200, 50, 90, 30);
        
		threadedinput = new JButton("disabled");
		threadedinput.setBounds(200, 100, 90, 30);
		threadedinput.addActionListener(l);
		
		drawinput = new JButton("enabled");
		drawinput.setBounds(200, 150, 90, 30);
		drawinput.addActionListener(l);
		
		run = new JButton("Run");
		run.setBounds(300, 195, 60, 30);
		run.addActionListener(l);
		
		
		this.add(sizeinput);
		this.add(threadedinput);
		this.add(sizeinfo);
		this.add(threadinfo);
		this.add(drawinfo);
		this.add(drawinput);
		this.add(run);
	}

}
