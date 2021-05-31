package Window;

import javax.swing.JTextField;

public class TextField extends JTextField implements IComponentFunction {
	
	private static final long serialVersionUID = 1L;
	IComponentFunction function;
	
	public TextField(String text) {
		super(text);
	}
	
	public void setAction(IComponentFunction function) {
		this.function = function;
	}
	
	public void performAction() {
		function.performAction();
	}
}
