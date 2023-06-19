package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.DrawingFrame;
import mvc.components.buttons.ButtonType;

public class ObserverUpdate implements PropertyChangeListener {

	private DrawingFrame frame;
	
	
	public ObserverUpdate(DrawingFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		if(pce.getPropertyName().equals("buttonDelete")) {
			frame.enableButton(ButtonType.DELETE, (boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonModify")) {
			frame.enableButton(ButtonType.MODIFY, (boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonToFront")) {
			frame.enableButton(ButtonType.TO_FRONT, (boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonToBack")) {
			frame.enableButton(ButtonType.TO_BACK, (boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonBringFront")) {
			frame.enableButton(ButtonType.BRING_FRONT, (boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonBringBack")) {
			frame.enableButton(ButtonType.BRING_BACK, (boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonUndo")) {
			frame.enableButton(ButtonType.UNDO, (boolean)pce.getNewValue());
		}
		if(pce.getNewValue().equals("buttonRedo")) {
			frame.enableButton(ButtonType.REDO, (boolean)pce.getNewValue());
		}
	}
}
