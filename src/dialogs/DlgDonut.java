package dialogs;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DlgDonut extends JDialog implements ActionListener, KeyListener {
	
	private final JButton btnAccept = new JButton("Accept");
	private final JButton btnDecline = new JButton("Decline");
	private final JButton btnOuterColor = new JButton("");
	private final JLabel lbOuterColor = new JLabel("Outer Color");
	private final JButton btnInnerColor = new JButton("");
	private final JLabel lbInnerColor = new JLabel("Inner Color");
	private final JLabel lbXCoord = new JLabel("X Coordinate:");
	private final JLabel lbYCoord = new JLabel("Y Coordinate:");
	private final JLabel lbInner = new JLabel("Inner Radius:");
	private final JLabel lbOuter = new JLabel("Outer Radius:");
	private final JTextField textFieldX = new JTextField();
	private final JTextField textFieldY = new JTextField();
	private final JTextField textFieldInRadius = new JTextField();
	private final JTextField textFieldOutRadius = new JTextField();

	private boolean accepted = false;
	
	public DlgDonut(int x, int y, Color innerColor, Color outerColor) {
		
		// Setting the dialog
		setTitle("Donut Dialog");
		setSize(300, 300);
		setLocationRelativeTo(null); // For center screen
		setModal(true);
		
		// Creating the panel
		JPanel mainPanel = new JPanel(new GridLayout(0,2,5,5));
		
		// Components
		textFieldX.setText(Integer.toString(x));
		textFieldY.setText(Integer.toString(y));
		btnInnerColor.setBackground(innerColor);
		btnOuterColor.setBackground(outerColor);
		
		// Adding components
		mainPanel.add(lbXCoord);
		mainPanel.add(textFieldX);
		mainPanel.add(lbYCoord);
		mainPanel.add(textFieldY);
		mainPanel.add(lbInner);
		mainPanel.add(textFieldInRadius);
		mainPanel.add(lbOuter);
		mainPanel.add(textFieldOutRadius);
		mainPanel.add(lbInnerColor);
		mainPanel.add(btnInnerColor);
		mainPanel.add(lbOuterColor);
		mainPanel.add(btnOuterColor);
		mainPanel.add(btnAccept);
		mainPanel.add(btnDecline);
		
		// Listeners
		btnInnerColor.addActionListener(this);
		btnOuterColor.addActionListener(this);
		btnAccept.addActionListener(this);
		btnDecline.addActionListener(this);
		textFieldInRadius.addKeyListener(this);
		textFieldOutRadius.addKeyListener(this);
		
		// Content Pane
		getContentPane().add(mainPanel);
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
		else if(e.getSource() == btnAccept) {
			accepted = true;
			dispose();
		}
		else if(e.getSource() == btnDecline) {
			dispose();
		}
		
	}

	public JTextField getTextFieldX() {
		return textFieldX;
	}

	public JTextField getTextFieldY() {
		return textFieldY;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public JButton getBtnOuterColor() {
		return btnOuterColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}
	
	public JTextField getTextFieldInRadius() {
		return textFieldInRadius;
	}

	public JTextField getTextFieldOutRadius() {
		return textFieldOutRadius;
	}
	 

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE))) {
			getToolkit().beep();
			e.consume();
		}
		
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

