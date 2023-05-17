package dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class DlgSurfaceShape extends DlgShape {
	private static final long serialVersionUID = 1L;
	private Color fillColor;
	private JButton btnFillColor;

	public DlgSurfaceShape() {
		btnFillColor = new JButton("Fill Color");
		
		btnFillColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				fillColor = JColorChooser.showDialog(null, "Choose a color", Color.WHITE);
			}
		});
	}

	public Color getFillColor() {
		return fillColor;
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public JButton getBtnFillColor() {
		return btnFillColor;
	}
}
