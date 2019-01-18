package Try;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Listener implements ActionListener, MouseListener, ChangeListener {
	
	Painter ref;
	
	public Listener (Painter pref) {
		ref = pref;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("clicked");
		
		if (e.getSource().equals(Menu.threadedinput)) {
			// change text of button
			if (Menu.threadedinput.getText().equals("disabled")) Menu.threadedinput.setText("enabled");
			else Menu.threadedinput.setText("disabled");
		} 
		else if (e.getSource().equals(Menu.drawinput)) {
			// change text of button
			if (Menu.drawinput.getText().equals("disabled")) Menu.drawinput.setText("enabled");
			else Menu.drawinput.setText("disabled");
		} 
		if (e.getSource().equals(Painter.pause)) {
			// pause button
			if (Painter.pause.getText().equals("pause")) Painter.pause.setText("continue");
			else Painter.pause.setText("pause");
			
			Painter.simulate.set(!Painter.simulate.get());
			//System.out.println("PAUSED");
			System.out.println("status: "+Painter.simulate.get());
			
			System.out.println(Painter.totaltime);
			
			//if (Painter.simulate.get()) ref.repaintPanel();
			if (ref.threaded) {
				//System.out.println("resetting");
				//Main.barrier.reset();
			}
			else ref.repaintPanel();
			ref.updateLabel();
		}
		else if (e.getSource().equals(Painter.reset)){
			if (ref.threaded) Barrier.reset = true;
			else {
				switch(Painter.gridSize) {
					case "X":
						GridX.initGrid();
						break;
					case "XX":
						GridXX.initGrid();
						break;
					case "XXX":
						GridXXX.initGrid();
						break;
					case "XXXX":
						GridXXXX.initGrid();
					break;
				}
				ref.repaintPanel();
			}
		}
		else if (e.getSource().equals(Menu.run)){
			// run button, menu
			if (Menu.sizeinput.getText().equals("")) Menu.sizeinput.setText("5");
			int input = Integer.parseInt(Menu.sizeinput.getText());
			
			boolean threaded = false, draw = false;
			if (Menu.threadedinput.getText().equals("enabled")) threaded = true;
			if (Menu.drawinput.getText().equals("enabled")) draw = true;
			
			// run the simulation and close menu frame
			Menu.frame.dispose();
			if (threaded) Main.nthreads = 4;
			Painter p = new Painter(input, draw, threaded);
		}
	}
	
	public void status () {
		System.out.println("G after pause:");
		for (int i=0; i<Painter.n; i++) {
			for (int j=0; j<Painter.n; j++) 
				System.out.print(Painter.g[i][j]+" ");
			System.out.println();
		}
		System.out.println("-----------");
		System.out.println("TG after pause:");
		for (int i=0; i<Painter.n; i++) {
			for (int j=0; j<Painter.n; j++) 
				System.out.print(Painter.tg[i][j]+" ");
			System.out.println();
		}
		System.out.println("-----------");
	}
	

	@Override
	public void stateChanged(ChangeEvent e) {
		// slider value return bounds: [0, 100], in 2 digit int value
		Painter.speed = Painter.slider.getValue();
		Painter.speedinfo.setText(Painter.speed+" ms");
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// grid starts at position (10, 100)
		// get position of the mouse, and calculate what cell (g[][]) was clicked
		// then set that cell to either alive or dead - 0, 1
		int mx = e.getX(), my = e.getY();
		
		int i = (my-100) / Painter.cellsize,
			j = (mx-10)  / Painter.cellsize;
		
		if ((i >= 0 && i <Painter.n) && (j >= 0 && j < Painter.n))
			if (Painter.g[i][j] == 1) Painter.g[i][j] = 0;
			else Painter.g[i][j] = 1;
		
		ref.repaint();
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
