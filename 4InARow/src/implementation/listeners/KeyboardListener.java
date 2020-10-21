package implementation.listeners;


import interfaces.IObservable;
import interfaces.IObserver;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class KeyboardListener implements KeyListener, IObservable {
	
	ArrayList<IObserver> observers;
	Set<Character> keys = new HashSet<>(4);
	
	boolean[] keyCodes = new boolean[256];
	
	public KeyboardListener() {
		observers = new ArrayList<>(5);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyCodes[e.getKeyCode()] = true;
		keys.add(e.getKeyChar());
		
		observers.forEach(obs -> {
			obs.notifyKeysPressed(keyCodes);
			obs.notifyCharacterKeyPressed(keys);
		});
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyCodes[e.getKeyCode()] = false;
		keys.remove(e.getKeyChar());
		
		observers.forEach(obs -> {
			obs.notifyKeysPressed(keyCodes);
			obs.notifyCharacterKeyPressed(keys);
		});
	}
	
	public void addObserver(IObserver obsever) { observers.add(obsever); }
	public void removeObserver(IObserver observer) { observers.remove(observer); }
	
	public void keyTyped(KeyEvent e) {}
}
