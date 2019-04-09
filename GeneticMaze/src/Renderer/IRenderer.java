package Renderer;

import java.awt.Color;
import java.awt.Graphics2D;

public interface IRenderer {
	
	Color startClr = Color.green;
	Color endClr = Color.red;
	
	Color infoClr = Color.black;
	Color bgClr = new Color(240, 240, 240);
	Color upperControlPanelClr = Color.LIGHT_GRAY;
	Color rightControlPanelClr = Color.LIGHT_GRAY;
	
	void draw(Graphics2D g);
	
}
