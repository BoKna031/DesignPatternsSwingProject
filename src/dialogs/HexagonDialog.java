package dialogs;

import geometry.HexagonAdapter;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;

public class HexagonDialog extends TemplateDialog implements ActionListener, KeyListener {

	private JTextField x_cordinate;
	private JTextField y_cordinate;
	private JTextField radius;
	private JButton btnOuterColor;
	private JButton btnInnerColor;

	private final boolean editable;

	public HexagonDialog(HexagonAdapter hexagon, boolean editable) {

		this.editable = editable;
		setTitle("Hexagon Dialog");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setModal(true);

		createComponents(hexagon);
		createContentPanel();
	}

	private void createComponents(HexagonAdapter hexagon){
		x_cordinate = new JTextField(Integer.toString(hexagon.getX()));
		y_cordinate = new JTextField(Integer.toString(hexagon.getY()));

		radius = new JTextField(Integer.toString(hexagon.getR()));
		radius.addKeyListener(this);

		btnInnerColor = new JButton();
		btnOuterColor = new JButton();

		x_cordinate.setEditable(editable);
		y_cordinate.setEditable(editable);
		x_cordinate.addKeyListener(this);
		y_cordinate.addKeyListener(this);

		btnInnerColor.setBackground(hexagon.getInnerColor());
		btnInnerColor.addActionListener(this);
		btnOuterColor.setBackground(hexagon.getOuterColor());
		btnOuterColor.addActionListener(this);

	}

	private void createContentPanel() {
		addComponent("X Cordinate: ", x_cordinate);
		addComponent("Y Cordinate: ", y_cordinate);
		addComponent("Radius:",radius);
		addComponent("Inner Color", btnInnerColor);
		addComponent("Outer Color", btnOuterColor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnOuterColor) {
			Color outerColor = JColorChooser.showDialog(this, "Odaberite svoju unutrasnju boju", Color.RED);
			btnOuterColor.setBackground(outerColor);
		}
		else if(e.getSource() == btnInnerColor) {
			Color innerColor = JColorChooser.showDialog(this, "Odaberite svoju unutrasnju boju", Color.RED);
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
		return x_cordinate;
	}
	public JTextField getTextFieldY() {
		return y_cordinate;
	}
	public JButton getBtnOuterColor() {
		return btnOuterColor;
	}
	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}
	public JTextField getTextFieldRadius() {
		return radius;
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}

