package dialogs;

import geometry.Point;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JTextField;

public class DlgPoint extends DialogTemplate{

	private final Point point;
	private final boolean editable;
	
	public DlgPoint(Point point, boolean editable) {
		this.point = point;
		this.editable = editable;
		setTitle("Point Dialog");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setModal(true);

		createContentPanel();
	}


	private void createContentPanel(){
		JTextField textFieldX = new JTextField(Integer.toString(point.getX()));
		textFieldX.setEditable(editable);
		addComponent("X Cordinate: ", textFieldX, "x_cord");

		JTextField textFieldY = new JTextField(Integer.toString(point.getY()));
		textFieldY.setEditable(editable);
		addComponent("Y Cordinate: ", textFieldY, "y_cord");

		JButton btnColor = new JButton("");
		btnColor.setBackground(point.getColor());
		btnColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Color color = JColorChooser.showDialog(null, "Odaberite svoju boju", Color.RED);
				point.setColor(color);
				btnColor.setBackground(color);
			}
		});

		addComponent("Color: ", btnColor, "color");
	}

	public String getTextFieldX() {
		JTextField txt =  (JTextField) getComponentByKey("x_cord");
		return txt.getText();
	}
	public String getTextFieldY() {
		JTextField txt =  (JTextField) getComponentByKey("y_cord");
		return txt.getText() ;
	}
	public Color getBtnColor() {
		JButton btn = (JButton) getComponentByKey("color");
		return btn.getBackground();
	}


}
