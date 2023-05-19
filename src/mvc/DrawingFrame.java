package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import geometry.Point;
import mvc.components.*;

public class DrawingFrame extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private DrawingController controller;
	private DrawingView view;
	public Point startPoint = null;

	private Menu menu = new Menu();
	private final ShapesToolbar leftPanel = new ShapesToolbar(new Dimension(110, 200));
	private final ModificationToolbar topPanel = new ModificationToolbar(null, controller);
	private final ColorPanel rightPanel = new ColorPanel(new Dimension(120, 200));

	private final LogPanel logPanel = new LogPanel(new Dimension(0, 150));

	public DrawingFrame(DrawingModel model) {

		this.setJMenuBar(menu);
		view = new DrawingView(new Dimension(800, 600), model);

		setupListeners();

		//dodavanje panela
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(view, BorderLayout.CENTER);
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(rightPanel, BorderLayout.EAST);
		getContentPane().add(logPanel, BorderLayout.PAGE_END);

	}
	
	public DrawingView getView() {
		return view;
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
		topPanel.setController(controller);
		menu.setController(controller);
	}
	
	private void setupListeners() {

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
		return logPanel.getLogArea();
	}

	public void appendLog(String log){
		logPanel.addLog(log);
	}

	public void clearLogArea(){
		logPanel.clearLogArea();
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
}
