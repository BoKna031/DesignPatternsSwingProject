package dialogs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public abstract class DlgShape extends JDialog{
	private static final long serialVersionUID = 1L;
	private JTextField textFieldX;
	private JTextField textFieldY;
	JLabel lblXCoord;
	JLabel lblYCoord;
	JButton btnEdgeColor;
	private JButton btnAccept;
	private JButton btnDecline;
	private boolean accepted;
	private Color edgeColor;
	
	public DlgShape() {
		
		textFieldX = new JTextField();
		textFieldX.setColumns(10);

		textFieldY = new JTextField();
		textFieldY.setColumns(10);
		
		textFieldX.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textFieldY.setFont(new Font("Tahoma", Font.PLAIN, 16));

		lblXCoord = new JLabel("X koordinata");
		lblYCoord = new JLabel("Y koordinata");
		
		lblXCoord.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblYCoord.setFont(new Font("Tahoma", Font.PLAIN, 16));

		btnEdgeColor = new JButton("Edge color");

		btnAccept = new JButton("OK");
		btnAccept.setActionCommand("OK");

		btnDecline = new JButton("Cancel");
		btnDecline.setActionCommand("Cancel");

		btnDecline.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});

		btnEdgeColor.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				edgeColor = JColorChooser.showDialog(null, "Choose a color", Color.BLACK);
			}
		});
		
	}

	public JTextField getTxtXCoordinate() {
		return textFieldX;
	}

	public String getXCoordinate() {
		return textFieldX.getText();
	}

	public void setXCoordinate(String xCoordinate) {
		textFieldX.setText(xCoordinate);
	}

	public String getYCoordinate() {
		return textFieldY.getText();
	}

	public void setYCoordinate(String yCoordinate) {
		textFieldY.setText(yCoordinate);
	}
	
	public JTextField getTxtYCoordinate() {
		return textFieldY;
	}

	public JLabel getLblXCoordinate() {
		return lblXCoord;
	}

	public JLabel getLblYCoordinate() {
		return lblYCoord;
	}

	public JButton getCancelButton() {
		return btnDecline;
	}
	
	public JButton getOkButton() {
		return btnAccept;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setIsOk(boolean isOk) {
		this.accepted = isOk;
	}
	
	public JButton getBtnEdgeColor() {
		return btnEdgeColor;
	}
	
	public Color getEdgeColor() {
		return edgeColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}

	public void setTxtXCoordinateEditable(boolean value) {
		textFieldX.setEditable(value);
	}

	public void setTxtYCoordinateEditable(boolean value) {
		textFieldY.setEditable(value);
	}
}
