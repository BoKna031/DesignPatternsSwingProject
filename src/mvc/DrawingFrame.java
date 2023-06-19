package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import geometry.Point;
import mvc.components.*;
import mvc.components.buttons.ButtonType;

public class DrawingFrame extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private DrawingController controller;
	private DrawingView view;
	public Point startPoint = null;
	private final Menu menu = new Menu();
	private final ShapesToolbar leftPanel = new ShapesToolbar(new Dimension(110, 200));
	private final ModificationToolbar topPanel = new ModificationToolbar(null, controller);
	private final ColorPanel rightPanel = new ColorPanel(new Dimension(120, 200));
	private final LogPanel logPanel = new LogPanel(new Dimension(0, 150));

	public DrawingFrame() {

		setJMenuBar(menu);
		//view = new DrawingView(new Dimension(800, 600), controller.getShapeService());

		//dodavanje panela
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(leftPanel, BorderLayout.WEST);
		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(rightPanel, BorderLayout.EAST);
		getContentPane().add(logPanel, BorderLayout.PAGE_END);
	}
	
	public void setController(DrawingController controller) {
		this.controller = controller;
		topPanel.setController(controller);
		menu.setController(controller);
		configView(controller);
	}

	private void configView(DrawingController controller) {
		view = new DrawingView(this.getPreferredSize(), controller.getShapeService());
		getContentPane().add(view, BorderLayout.CENTER);

		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isButtonSelectActive())
					controller.select(e.getX(), e.getY());
				else
					controller.createShape(e.getX(), e.getY());
			}
		});
	}

	public JButton getBtnDelete() {
		return topPanel.getBtnDelete();
	}
	
	public JButton getBtnUndo() {
		return topPanel.getUndo();
	}

	public void enableButton(ButtonType buttonType, boolean enable){
		switch(buttonType){
			case UNDO: topPanel.getUndo().setEnabled(enable); break;
			case REDO: topPanel.getRedo().setEnabled(enable); break;
			case NEXT: topPanel.getBtnNext().setEnabled(enable); break;
		}
	}

	public void updateView(){
		view.repaint();
	}

	public JButton getBtnRedo() {
		return topPanel.getRedo();
	}

	public void appendLog(String log){
		logPanel.addLog(log);
	}

	public void clearLogArea(){
		logPanel.clearLogArea();
	}
	
	public JButton getBtnToBack() {
		return topPanel.getToBack();
	}
	
	public JButton getBtnToFront() {
		return topPanel.getBtnToFront();
	}

	public JButton getBtnBringBack() {
		return topPanel.getBtnBringBack();
	}
	
	public JButton getBtnBringFront() {
		return topPanel.getBringFront();
	}

	public Color getInnerColor(){
		return rightPanel.getInnerColor();
	}
	public Color getOuterColor(){
		return rightPanel.getOuterColor();
	}

	private boolean isButtonSelectActive(){
		return topPanel.getBtnSelect().isSelected();
	}

	public JButton getBtnModify() {
		return topPanel.getBtnModify();
	}

	public ButtonType getSelectedButtonShape(){
		if(leftPanel.getBtnPoint().isSelected())
			return ButtonType.POINT;
		if(leftPanel.getBtnLine().isSelected())
			return ButtonType.LINE;
		if(leftPanel.getBtnRectangle().isSelected())
			return ButtonType.RECTANGLE;
		if(leftPanel.getBtnCircle().isSelected())
			return ButtonType.CIRCLE;
		if(leftPanel.getBtnDonut().isSelected())
			return ButtonType.DONUT;
		if(leftPanel.getBtnHex().isSelected())
			return ButtonType.HEXAGON;
		return ButtonType.NONE;
	}
}
