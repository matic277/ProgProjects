package interfaces;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface IMouseObserver  {
    void onMouseMove(MouseEvent event);
    void onMouseClick(MouseEvent event);
}