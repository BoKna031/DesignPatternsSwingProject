package observer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Observer {
	
	private boolean buttonDeleteEnabled;
	private boolean buttonModifyEnabled;
	private boolean buttonToFrontEnabled;
	private boolean buttonToBackEnabled;
	private boolean buttonBringFrontEnabled;
	private boolean buttonBringBackEnabled;
	private boolean buttonUndoEnabled;
	private boolean buttonRedoEnabled;
	
	
	private PropertyChangeSupport propertyChangeSupport;
	
	
	public Observer() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
	}
	
	public void setButtonDeleteEnabled(boolean buttonDeleteEnabled) {
		propertyChangeSupport.firePropertyChange("buttonDelete", this.buttonDeleteEnabled, buttonDeleteEnabled);
		this.buttonDeleteEnabled = buttonDeleteEnabled;
	}
	
	public void setButtonModifyEnabled(boolean buttonModifyEnabled) {
		propertyChangeSupport.firePropertyChange("buttonModify", this.buttonModifyEnabled, buttonModifyEnabled);
		this.buttonModifyEnabled = buttonModifyEnabled;
	}
	
	public void setButtonToFrontEnabled(boolean buttonToFrontEnabled) {
		propertyChangeSupport.firePropertyChange("buttonToFront", this.buttonToFrontEnabled, buttonToFrontEnabled);
		this.buttonToFrontEnabled = buttonToFrontEnabled;
	}
	
	public void setButtonToBackEnabled(boolean buttonToBackEnabled) {
		propertyChangeSupport.firePropertyChange("buttonToBack", this.buttonToBackEnabled, buttonToBackEnabled);
		this.buttonToBackEnabled = buttonToBackEnabled;
	}
	
	public void setButtonBringFrontEnabled(boolean buttonBringFrontEnabled) {
		propertyChangeSupport.firePropertyChange("buttonBringFront", this.buttonBringFrontEnabled, buttonBringFrontEnabled);
		this.buttonBringFrontEnabled = buttonBringFrontEnabled;
	}
	
	public void setButtonBringBackEnabled(boolean buttonBringBackEnabled) {
		propertyChangeSupport.firePropertyChange("buttonBringBack", this.buttonBringBackEnabled, buttonBringBackEnabled);
		this.buttonBringBackEnabled = buttonBringBackEnabled;
	}
	
	public void setButtonUndoEnabled(boolean buttonUndoEnabled) {
		propertyChangeSupport.firePropertyChange("buttonUndo", this.buttonUndoEnabled, buttonUndoEnabled);
		this.buttonUndoEnabled = buttonUndoEnabled;
	}
	
	public void setButtonRedoEnabled(boolean buttonRedoEnabled) {
		propertyChangeSupport.firePropertyChange("buttonRedo", this.buttonRedoEnabled, buttonRedoEnabled);
		this.buttonRedoEnabled = buttonRedoEnabled;
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pc1) {
		propertyChangeSupport.addPropertyChangeListener(pc1);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pc1) {
		propertyChangeSupport.removePropertyChangeListener(pc1);
	}
	
	
}
