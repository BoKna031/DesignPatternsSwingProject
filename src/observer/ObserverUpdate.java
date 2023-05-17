package observer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import mvc.DrawingFrame;

public class ObserverUpdate implements PropertyChangeListener {
		
	
	private DrawingFrame frame;
	
	
	public ObserverUpdate(DrawingFrame frame) {
		this.frame = frame;
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent pce) {
		
		if(pce.getPropertyName().equals("buttonDelete")) {
			frame.getBtnDelete().setEnabled((boolean)pce.getNewValue());
			
		}
		if(pce.getPropertyName().equals("buttonModify")) {
			frame.getBtnModify().setEnabled((boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonToFront")) {
			frame.getBtnToFront().setEnabled((boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonToBack")) {
			frame.getBtnToBack().setEnabled((boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonBringFront")) {
			frame.getBtnBringFront().setEnabled((boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonBringBack")) {
			frame.getBtnBringBack().setEnabled((boolean)pce.getNewValue());
		}
		if(pce.getPropertyName().equals("buttonUndo")) {
			frame.getBtnUndo().setEnabled((boolean)pce.getNewValue());
		}
		if(pce.getNewValue().equals("buttonRedo")) {
			frame.getBtnRedo().setEnabled((boolean)pce.getNewValue());
		}
	}
}
