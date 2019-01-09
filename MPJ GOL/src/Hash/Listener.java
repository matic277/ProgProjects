package Hash;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Listener implements ActionListener, ChangeListener {
	
	Painter ref;
	
	public Listener (Painter pref) {
		ref = pref;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("clicked");
		
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
}
