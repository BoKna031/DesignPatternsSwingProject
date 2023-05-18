package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import geometry.Point;
import mvc.components.ColorPanel;
import mvc.components.Menu;
import mvc.components.ModificationToolbar;
import mvc.components.ShapesToolbar;

public class DrawingFrame extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private DrawingController controller;
	private DrawingView view;
	public Point startPoint = null;

	private Menu menu = new Menu();
	private final ShapesToolbar leftPanel = new ShapesToolbar();
	private final ModificationToolbar topPanel = new ModificationToolbar(controller);
	private final ColorPanel rightPanel = new ColorPanel();
	private final JTextArea logArea = new JTextArea();
	private final JScrollPane scrollPane = new JScrollPane(logArea);
	
	public DrawingFrame() {
		setJMenuBar(menu);

		
		
		//Panel layouts
		leftPanel.setLayout(new GridLayout(0, 1, 10, 10));
		topPanel.setLayout(new FlowLayout()); //iako je podrazumevano
		rightPanel.setLayout(new GridLayout(0, 1, 3, 3));
		view = new DrawingView();
		
		//velicina panela
		view.setPreferredSize(new Dimension(800, 600));
		leftPanel.setPreferredSize(new Dimension(110, 200));
		rightPanel.setPreferredSize(new Dimension(120, 200));

		
		
		//logArea
		scrollPane.setPreferredSize(new Dimension(0, 150));
		logArea.setEditable(false);
		
		//podesavajne listenera
		setupListeners();
		
		
		//dodavanje panela
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(view, BorderLayout.CENTER);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(rightPanel, BorderLayout.EAST);
		getContentPane().add(scrollPane, BorderLayout.PAGE_END);

	}
	
	public DrawingView getView() {
		return view;
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
		topPanel.setController(controller);
		menu.setController(controller);
	}
	
	public JToggleButton getBtnSelect() {
		return topPanel.getBtnSelect();
	}
	
	public JButton getBtnModify() {
		return topPanel.getBtnModify();
	}
	
	public JToggleButton getBtnPoint() {
		return leftPanel.getBtnPoint();
	}
	
	public JToggleButton getBtnLine() {
		return leftPanel.getBtnLine();
	}
	
	public JToggleButton getBtnRectangle() {
		return leftPanel.getBtnRectangle();
	}
	
	public JToggleButton getBtnCircle() {
		return leftPanel.getBtnCircle();
	}
	
	public JToggleButton getBtnDonut() {
		return leftPanel.getBtnDonut();
	}
	
	public JToggleButton getBtnHex() {
		return leftPanel.getBtnHex();
	}
	
	private void setupListeners() {
		
		
		//View
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});

	}
	
	public JButton getBtnDelete() {
		return topPanel.getBtnDelete();
	}
	
	public JButton getBtnUndo() {
		return topPanel.getBtnUndo();
	}
	
	public JButton getBtnRedo() {
		return topPanel.getBtnRedo();
	}
	
	public JTextArea getLogArea() {
		return logArea;
	}
	
	public JButton getBtnToBack() {
		return topPanel.getBtnToBack();
	}
	
	public JButton getBtnToFront() {
		return topPanel.getBtnToFront();
	}

	public JButton getBtnBringBack() {
		return topPanel.getBtnBringBack();
	}
	
	public JButton getBtnBringFront() {
		return topPanel.getBtnBringFront();
	}
	
	public JButton getBtnNext() {
		return topPanel.getBtnNext();
	}

	public Color getInnerColor(){
		return rightPanel.getInnerColor();
	}
	public Color getOuterColor(){
		return rightPanel.getOuterColor();
	}
}
