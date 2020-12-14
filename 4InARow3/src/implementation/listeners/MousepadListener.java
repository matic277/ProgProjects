package implementation.listeners;


import interfaces.IMouseObserver;
import interfaces.IObservable;
import interfaces.IObserver;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class MousepadListener implements MouseMotionListener, MouseListener, IObservable {
	
	ArrayList<IObserver> observers;
	ArrayList<IMouseObserver> mouseObservers;
	
	public MousepadListener() {
		observers = new ArrayList<>(5);
		mouseObservers = new ArrayList<>(2);
	}
	
	public void mousePressed(MouseEvent e)	{ 
		observers.forEach(obs -> obs.notifyMousePressed(e));
		mouseObservers.forEach(obs -> obs.onMouseClick(e));
		if(e.getButton() == MouseEvent.BUTTON1) observers.forEach(obs -> obs.notifyLeftPress(e.getPoint()));
		else if(e.getButton() == MouseEvent.BUTTON3) observers.forEach(obs -> obs.notifyRightPress(e.getPoint()));
	}
	
	public void mouseReleased(MouseEvent e)	{
		observers.forEach(obs -> obs.notifyMouseReleased(e));
		if(e.getButton() == MouseEvent.BUTTON1) observers.forEach(obs -> obs.notifyLeftPress(e.getPoint()));
		else if(e.getButton() == MouseEvent.BUTTON3) observers.forEach(obs -> obs.notifyRightPress(e.getPoint()));
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseObservers.forEach(obs -> obs.onMouseMove(e));
		observers.forEach(obs -> obs.notifyMouseMoved(e.getPoint()));
	}
	
	public void mouseMoved(MouseEvent e) {
		mouseObservers.forEach(obs -> obs.onMouseMove(e));
		observers.forEach(obs -> obs.notifyMouseMoved(e.getPoint()));
	}
	
	public void addMouseObserver(IMouseObserver observer) { mouseObservers.add(observer); }
	public void addObserver(IObserver obsever)     { observers.add(obsever); }
	public void removeObserver(IObserver observer) { observers.remove(observer); }
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
