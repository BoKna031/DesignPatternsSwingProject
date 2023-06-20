package dialogs;

import model.entity.geometry.Donut;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;

public class DonutDialog extends TemplateDialog implements ActionListener, KeyListener {

	private JTextField x_coordinate;
	private JTextField y_coordinate;
	private JTextField inner_radius;
	private JTextField outer_radius;
	private JButton btnOuterColor;
	private JButton btnInnerColor;

	private final boolean editable;

	public DonutDialog(Donut donut, boolean editable) {

		this.editable = editable;
		setTitle("Donut Dialog");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setModal(true);

		createComponents(donut);
		createContentPanel();
	}

	private void createComponents(Donut donut){
		x_coordinate = new JTextField(Integer.toString(donut.getCenter().getX()));
		y_coordinate = new JTextField(Integer.toString(donut.getCenter().getY()));

		inner_radius = new JTextField(Integer.toString(donut.getInnerRadius()));
		inner_radius.addKeyListener(this);

		outer_radius = new JTextField(Integer.toString(donut.getRadius()));
		outer_radius.addKeyListener(this);

		btnInnerColor = new JButton();
		btnOuterColor = new JButton();

		x_coordinate.setEditable(editable);
		y_coordinate.setEditable(editable);
		x_coordinate.addKeyListener(this);
		y_coordinate.addKeyListener(this);

		btnInnerColor.setBackground(donut.getInnerColor());
		btnInnerColor.addActionListener(this);
		btnOuterColor.setBackground(donut.getOuterColor());
		btnOuterColor.addActionListener(this);
	}

	private void createContentPanel() {
		addComponent("X Cordinate: ", x_coordinate);
		addComponent("Y Cordinate: ", y_coordinate);
		addComponent("Inner Radius:",inner_radius);
		addComponent("Outer Radius:",outer_radius);
		addComponent("Inner Color", btnInnerColor);
		addComponent("Outer Color", btnOuterColor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOuterColor) {
			Color outerColor = JColorChooser.showDialog(this, "Choose your inner color", Color.RED);
			btnOuterColor.setBackground(outerColor);
		}
		else if(e.getSource() == btnInnerColor) {
			Color innerColor = JColorChooser.showDialog(this, "Choose your inner color", Color.RED);
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
		return x_coordinate;
	}

	public JTextField getTextFieldY() {
		return y_coordinate;
	}

	public JButton getBtnOuterColor() {
		return btnOuterColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public JTextField getTextFieldInRadius() {
		return inner_radius;
	}

	public JTextField getTextFieldOutRadius() {
		return outer_radius;
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}

