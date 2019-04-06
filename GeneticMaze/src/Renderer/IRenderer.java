package Renderer;

import java.awt.Color;
import java.awt.Graphics2D;

public interface IRenderer {
	
	Color startClr = Color.green;
	Color endClr = Color.red;
	
	Color infoClr = Color.black;
	Color bgClr = new Color(230, 230, 230);
	Color controlPanelClr = Color.LIGHT_GRAY;
	
	void draw(Graphics2D g);
	
}
