package Renderer;

import Main.Environment;
import Main.Listener;
import Main.Var;

public class GraphicsRenderer extends Thread {
	
	public Painter painter;
	
	Environment env;
	
	public GraphicsRenderer(Environment env) {
		this.env = env;
		Listener list = new Listener(env);
		painter = new Painter(env, list);
		painter.setRenderer(new EditorRenderer(env));
		list.painter = painter;
	}
	
	@Override
	public void run() {
		while (true) {
			painter.repaint();
			try { Thread.sleep(Var.FPS); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}
