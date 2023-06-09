package dialogs;

import geometry.Line;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;

public class LineDialog extends TemplateDialog implements ActionListener, KeyListener {

	private JTextField textFieldX;
	private JTextField textFieldY;
	private JTextField textFieldX2;
	private JTextField textFieldY2;
	private JButton btnColor;
	private final boolean editable;

	public LineDialog(Line line, boolean editable) {
		this.editable = editable;
		setTitle("Line Dialog");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setModal(true);

		createComponents(line);
		createContentPanel();
	}

	private void createComponents(Line line) {
		textFieldX = new JTextField(Integer.toString(line.getStart().getX()));
		textFieldX.setEditable(editable);
		textFieldX.addKeyListener(this);

		textFieldY = new JTextField(Integer.toString(line.getStart().getY()));
		textFieldY.setEditable(editable);
		textFieldY.addKeyListener(this);

		textFieldX2 = new JTextField(Integer.toString(line.getEnd().getX()));
		textFieldX2.setEditable(editable);
		textFieldX2.addKeyListener(this);

		textFieldY2 = new JTextField(Integer.toString(line.getEnd().getY()));
		textFieldY2.setEditable(editable);
		textFieldY2.addKeyListener(this);

		btnColor = new JButton("");
		btnColor.setBackground(line.getColor());
		btnColor.addActionListener(this);
	}

	private void createContentPanel(){
		addComponent("X Cordinate: ", textFieldX);
		addComponent("Y Cordinate: ", textFieldY);
		addComponent("X2 Cordinate: ", textFieldX2);
		addComponent("Y2 Cordinate: ", textFieldY2);
		addComponent("Color: ", btnColor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnColor) {
			Color color = JColorChooser.showDialog(this, "Odaberite svoju boju", Color.RED);
			btnColor.setBackground(color);
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
	public String getTextFieldX() {
		return textFieldX.getText();
	}
	public String getTextFieldY() {
		return textFieldY.getText();
	}
	public String getTextFieldX2() {
		return textFieldX2.getText();
	}
	public String getTextFieldY2() {
		return textFieldY2.getText();
	}
	public Color getBtnColor() {
		return btnColor.getBackground();
	}
	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}

