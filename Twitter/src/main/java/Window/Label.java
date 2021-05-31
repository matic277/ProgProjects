package Window;

import javax.swing.JLabel;

public class Label extends JLabel implements IComponentFunction {
	
	private static final long serialVersionUID = 1L;
	IComponentFunction function;
	
	public Label(String text) {
		super(text);
	}
	
	public void setAction(IComponentFunction function) {
		this.function = function;
	}
	
	public void performAction() {
		function.performAction();
	}

}
