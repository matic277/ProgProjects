package Window;

import javax.swing.JButton;

public class Button extends JButton implements IComponentFunction {

	private static final long serialVersionUID = 1L;
	IComponentFunction function;
	
	public Button(String text) {
		super(text);
	}
	
	public void setAction(IComponentFunction function) {
		this.function = function;
	}
	
	public void performAction() {
		function.performAction();
	}
	
}
