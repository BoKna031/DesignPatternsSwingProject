package dialogs;

import geometry.Point;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;

public class PointDialog extends DialogTemplate implements ActionListener, KeyListener {
	private JTextField textFieldX;
	private JTextField textFieldY;

	private JButton btnColor;

	private final boolean editable;
	
	public PointDialog(Point point, boolean editable) {
		this.editable = editable;
		setTitle("Point Dialog");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setModal(true);

		createComponents(point);
		createContentPanel();
	}

	private void createComponents(Point point) {
		textFieldX = new JTextField(Integer.toString(point.getX()));
		textFieldX.setEditable(editable);
		textFieldX.addKeyListener(this);

		textFieldY = new JTextField(Integer.toString(point.getY()));
		textFieldY.setEditable(editable);
		textFieldY.addKeyListener(this);

		btnColor = new JButton("");
		btnColor.setBackground(point.getColor());
		btnColor.addActionListener(this);
	}


	private void createContentPanel(){
		addComponent("X Cordinate: ", textFieldX);
		addComponent("Y Cordinate: ", textFieldY);
		addComponent("Color: ", btnColor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnColor) {
			Color outerColor = JColorChooser.showDialog(this, "Choose your outer color", btnColor.getBackground());
			btnColor.setBackground(outerColor);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
			getToolkit().beep();
			e.consume();
		}

	}

	public String getTextFieldX() {;
		return textFieldX.getText();
	}
	public String getTextFieldY() {
		return textFieldY.getText() ;
	}
	public Color getBtnColor() {
		return btnColor.getBackground();
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}
}
