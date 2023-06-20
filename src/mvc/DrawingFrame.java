package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

import model.entity.geometry.Point;
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

	public void enableButton(ButtonType buttonType, boolean enable){
		switch(buttonType){
			case UNDO: topPanel.getUndo().setEnabled(enable); break;
			case REDO: topPanel.getRedo().setEnabled(enable); break;
			case NEXT: topPanel.getBtnNext().setEnabled(enable); break;
			case MODIFY: topPanel.getBtnModify().setEnabled(enable); break;
			case DELETE: topPanel.getBtnDelete().setEnabled(enable); break;
			case TO_FRONT: topPanel.getToFront().setEnabled(enable); break;
			case TO_BACK: topPanel.getToBack().setEnabled(enable); break;
			case BRING_BACK: topPanel.getBringBack().setEnabled(enable); break;
			case BRING_FRONT: topPanel.getBringFront().setEnabled(enable); break;
		}
	}

	public void updateView(){
		view.repaint();
	}

	public void appendLog(String log){
		logPanel.addLog(log);
	}

	public void clearLogArea(){
		logPanel.clearLogArea();
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
