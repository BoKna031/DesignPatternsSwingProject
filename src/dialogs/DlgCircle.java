package dialogs;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class DlgCircle extends DlgSurfaceShape implements ActionListener, KeyListener {
	
	private final JPanel mainPanel = new JPanel(new GridLayout(0,2,5,5));
	
	//private final JButton btnAccept = new JButton("Accept");
	//private final JButton btnDecline = new JButton("Decline");
	//private final JButton btnOuterColor = new JButton("");
	private final JLabel lbOuterColor = new JLabel("Outer Color");
	//private final JButton btnInnerColor = new JButton("");
	private final JLabel lbInnerColor = new JLabel("Inner Color");
	//private final JLabel lbXCoord = new JLabel("X Coordinate:");
	//private final JLabel lbYCoord = new JLabel("Y Coordinate:");
	private final JLabel lbRadius = new JLabel("Radius:");
	//private final JTextField textFieldX = new JTextField();
	//private final JTextField textFieldY = new JTextField();
	private final JTextField textRadius = new JTextField();
	
	public JTextField getTextFieldRadius() {
		return textRadius;
	}

	private boolean accepted = false;
	
	public DlgCircle(int x, int y, Color innerColor, Color outerColor) {
		
		// Setting the dialog
		setTitle("Circle Dialog");
		setSize(370, 370);
		setLocationRelativeTo(null); // For center screen
		setModal(true);
		
		// Components
		setXCoordinate(Integer.toString(x));
		setYCoordinate(Integer.toString(y));
		//btnInnerColor.setBackground(innerColor);
		//btnOuterColor.setBackground(outerColor);
		
		// Adding components
		mainPanel.add(getLblXCoordinate());
		mainPanel.add(getTxtXCoordinate());
		mainPanel.add(getLblYCoordinate());
		mainPanel.add(getTxtYCoordinate());
		mainPanel.add(lbRadius);
		mainPanel.add(textRadius);
		mainPanel.add(lbInnerColor);
		mainPanel.add(getBtnFillColor());
		mainPanel.add(lbOuterColor);
		mainPanel.add(getBtnEdgeColor());
		mainPanel.add(getOkButton());
		mainPanel.add(getCancelButton());
		
		// Listeners
		getBtnFillColor().addActionListener(this);
		getBtnEdgeColor().addActionListener(this);
		getOkButton().addActionListener(this);
		getCancelButton().addActionListener(this);
		textRadius.addKeyListener(this);
		
		// Content Pane
		getContentPane().add(mainPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == getBtnEdgeColor()) {
			Color outerColor = JColorChooser.showDialog(this, "Choose your inner color", Color.RED);
			getBtnEdgeColor().setBackground(outerColor);
		}
		else if(e.getSource() == getBtnFillColor()) {
			Color innerColor = JColorChooser.showDialog(this, "Choose your inner color", Color.RED);
			getBtnFillColor().setBackground(innerColor);
		}
		else if(e.getSource() == getOkButton()) {
			accepted = true;
			dispose();
		}
		else if(e.getSource() == getCancelButton()) {
			dispose();
		}
		
	}

	public String getTextRadius() {
		return textRadius.getText();
	}

	public void setTextRadius(String textField) {
		this.textRadius.setText(textField);
	}

	public void setTxtRadiusEditable(boolean value) {
		textRadius.setEditable(value);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	 
	
}