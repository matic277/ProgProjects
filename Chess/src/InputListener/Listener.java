package InputListener;
import java.awt.event.MouseEvent;
import Main.Board;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Listener implements MouseListener, MouseMotionListener{
	
	InputHandler inputHandler;
	
	public Listener(Board _board) {
		inputHandler = new InputHandler(_board);
	}


	@Override
	public void mouseClicked(MouseEvent e) {	
		int mx = e.getX();
		int my = e.getY();
		
		inputHandler.mouseClicked(mx, my);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		inputHandler.mouseMoved(mx, my);
	}
	
	
	

	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
}
