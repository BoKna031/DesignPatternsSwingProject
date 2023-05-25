package dialogs;

import geometry.Rectangle;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;

public class RectangleDialog extends DialogTemplate implements ActionListener, KeyListener{
	private JTextField x_cord;
	private JTextField y_cord;

	private JTextField height;
	private JTextField width;
	private JButton btnOuterColor;
	private JButton btnInnerColor;

	private final boolean editable;
	
	public RectangleDialog(Rectangle rectangle, boolean editable) {
		this.editable = editable;

		setTitle("Rectangle Dialog");
		setSize(300, 300);
		setLocationRelativeTo(null); // For center screen
		setModal(true);

		createComponents(rectangle);
		createContentPanel();
		

	}

	private void createComponents(Rectangle rectangle){
		x_cord = new JTextField(Integer.toString(rectangle.getUpperLeftPoint().getX()));
		y_cord = new JTextField(Integer.toString(rectangle.getUpperLeftPoint().getY()));

		height = new JTextField(Integer.toString(rectangle.getHeight()));
		height.addKeyListener(this);
		width = new JTextField(Integer.toString(rectangle.getWidth()));
		width.addKeyListener(this);

		btnInnerColor = new JButton();
		btnOuterColor = new JButton();

		x_cord.setEditable(editable);
		y_cord.setEditable(editable);
		x_cord.addKeyListener(this);
		y_cord.addKeyListener(this);

		btnInnerColor.setBackground(rectangle.getInnerColor());
		btnInnerColor.addActionListener(this);
		btnOuterColor.setBackground(rectangle.getOuterColor());
		btnOuterColor.addActionListener(this);

	}

	private void createContentPanel() {
		addComponent("X Cordinate: ", x_cord);
		addComponent("Y Cordinate: ", y_cord);
		addComponent("Width:",width);
		addComponent("Height:",height);
		addComponent("Inner Color", btnInnerColor);
		addComponent("Outer Color", btnOuterColor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOuterColor) {
			Color outerColor = JColorChooser.showDialog(this, "Choose your outer color", btnOuterColor.getBackground());
			btnOuterColor.setBackground(outerColor);
		}
		else if(e.getSource() == btnInnerColor) {
			Color innerColor = JColorChooser.showDialog(this, "Choose your inner color", btnInnerColor.getBackground());
			btnInnerColor.setBackground(innerColor);
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

	public JTextField getTextFieldX() {
		return x_cord;
	}

	public JTextField getTextFieldY() {
		return y_cord;
	}

	public JButton getBtnOuterColor() {
		return btnOuterColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JTextField getTextFieldHeight() {
		return height;
	}

	public JTextField getTextFieldWidth() {
		return width;
	}



	@Override
	public void keyPressed(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}

