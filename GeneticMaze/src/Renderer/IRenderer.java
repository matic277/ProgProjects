package Renderer;

import java.awt.Color;
import java.awt.Graphics2D;

public interface IRenderer {
	
	Color start = Color.green;
	Color finish = Color.red;
	
	void draw(Graphics2D g);
	
}
