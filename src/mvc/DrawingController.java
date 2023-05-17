package mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import command.CommandManager;
import command.commands.CmdBringToBack;
import command.commands.CmdBringToFront;
import command.commands.CircleModify;
import command.commands.CmdAdd;
import command.commands.CmdRemove;
import command.commands.CmdDeselect;
import command.commands.DonutModify;
import command.commands.HexModify;
import command.commands.LineModify;
import command.commands.PointModify;
import command.commands.RectangleModify;
import command.commands.CmdSelect;
import command.commands.CmdToBack;
import command.commands.CmdToFront;
import dialogs.DlgCircle;
import dialogs.DlgDonut;
import dialogs.DlgHexagon;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.HexagonAdapter;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.Observer;
import observer.ObserverUpdate;
import observer.SelectedObjects;
import strategy.SaveLog;
import strategy.SaveManager;
import strategy.SavePainting;

public class DrawingController extends MouseAdapter implements ActionListener {

	
	private DrawingModel model;
	private DrawingFrame frame;
	private SelectedObjects selectedObjects = new SelectedObjects();
	CommandManager commandManager = CommandManager.getInstance();
	final JFileChooser fc = new JFileChooser();
	List<Shape> shapes = new ArrayList<Shape>();
	ArrayList<String> txtFileLines = new ArrayList<String>();
	int i = 0;
	
	
	ObserverUpdate observerUpdate;
	Observer observer =  new Observer();
	
	//Brojaic
	int pointerCount = 1;
	int lineCount = 1;
	int rectangleCount = 1;
	int circleCount = 1;
	int donutCount = 1;
	int hexagonCount = 1;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		commandManager.setFrame(frame);
		observerUpdate = new ObserverUpdate(frame);
		observer.addPropertyChangeListener(observerUpdate);
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		Color innerColor = frame.innerColor;
		Color outerColor = frame.outerColor;
		
		//Komande
		
		//Select
		if(frame.getBtnSelect().isSelected() == true) {
			if(model.getShapes() != null) {
				
				for (int i = model.getShapes().size() - 1; i >= 0; i--) {
					Shape shape = model.get(i);
					
					if (shape.contains(x, y) && !shape.isSelected()) {
						
						CmdSelect selectCommand = new CmdSelect(shape, selectedObjects, shape.getName() + " - Selected");
						commandManager.execute(selectCommand);
						
						notifyAllObservers(selectedObjects.size());
						break;
					} else if (shape.contains(x, y) && shape.isSelected()) {
						CmdDeselect deselectCommand = new CmdDeselect(shape, selectedObjects, shape.getName() + " - Deselected");
						commandManager.execute(deselectCommand);
						notifyAllObservers(selectedObjects.size());
						break;
					} else if (i == 0) {
						for (int j = 0; selectedObjects.size() > 0;) {
							CmdDeselect deselectCommand = new CmdDeselect(selectedObjects.get(j), selectedObjects, selectedObjects.get(j).getName() + " - Deselected");
							commandManager.execute(deselectCommand);
						}
						
						notifyAllObservers(selectedObjects.size());
					}
				}
			}
		}
		
		//Iscrtavanje oblika
		
		//POINT
		else if (frame.getBtnPoint().isSelected() == true) {
			try {
					Point point = pointDialog(x, y, outerColor, false);
					if (point != null) {
						point.setNameString("Point" + pointerCount++ + "," + point.toString() + ",color," + String.valueOf(point.getColor().getRGB()));
						
						CmdAdd cmd = new CmdAdd(model, point, point.getName() + " - Add");
						commandManager.execute(cmd);
						
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
					}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
		
		//LINE
		else if (frame.getBtnLine().isSelected() == true) {
			
			if(frame.startPoint == null) {
				frame.startPoint = new Point(x, y);
			} else {
				try {
					Line line = lineDialog(frame.startPoint.getX(), frame.startPoint.getY(), x, y, outerColor, false);
					if (line != null) {
						line.setNameString("Line" + lineCount++ + "," + line.toString());
						CmdAdd cmd = new CmdAdd(model, line, line.getName() + " - Add");
						commandManager.execute(cmd);
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
				
				frame.startPoint = null;
			}
		}
		
		//RECTANGLE
		else if (frame.getBtnRectangle().isSelected() == true) {

			try {

				Rectangle rect = rectDialog(x, y, 0, 0, innerColor, outerColor, false);
				if (rect != null) {
					rect.setNameString("Rectangle" + rectangleCount++ + "," + rect.toString());
					CmdAdd cmd = new CmdAdd(model, rect, rect.getName() + " - Add");
					commandManager.execute(cmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
		
		//CIRCLE
		else if (frame.getBtnCircle().isSelected() == true) {

			try {
				Circle circle = circleDialog(x, y, 0, innerColor, outerColor, false);

				if (circle != null) {
					circle.setNameString("Circle" + circleCount++ + "," + circle.toString());
					CmdAdd cmd = new CmdAdd(model, circle, circle.getName() + " - Add");
					commandManager.execute(cmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}

		}
		
		//DONUT
		else if (frame.getBtnDonut().isSelected() == true) {

			try {
				Donut donut = donutDialog(x, y, 0, 0, innerColor, outerColor, false);
				if (donut != null) {
					donut.setNameString("Donut" + donutCount++ + "," + donut.toString());
					CmdAdd cmd = new CmdAdd(model, donut, donut.getName() + " - Add");
					commandManager.execute(cmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
				}

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
		
		//Hexagon
		else if (frame.getBtnHex().isSelected() == true) {
			
			try {
				
				HexagonAdapter hex = hexDialog(x, y, 0, innerColor, outerColor, false);
				if(hex != null) {
					
					hex.setNameString("Hexagon" + hexagonCount++ + "," + hex.toString());
					CmdAdd cmd = new CmdAdd(model, hex, hex.getName() + " - Add");
					commandManager.execute(cmd);
					frame.getBtnUndo().setEnabled(true);
					frame.getBtnRedo().setEnabled(false);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
		}
		frame.getView().repaint();
		frame.getBtnNext().setEnabled(false);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//DELETE
		if (e.getSource() == frame.getBtnDelete() && model.getShapes().size() > 0 && selectedObjects.size() > 0) {
			int input = JOptionPane.showConfirmDialog(null, "Da li ste sigurni?");
			if (input == 0) {
				while (selectedObjects.size() > 0) {
					Shape shape = selectedObjects.get(0);
					if(shape.isSelected()) {
						int index = model.getShapes().indexOf(shape);
						CmdRemove cmdRemove = new CmdRemove(model, shape, index, shape.getName() + " - Deleted", selectedObjects);
						selectedObjects.remove(shape);
						commandManager.execute(cmdRemove);
						
						frame.getBtnUndo().setEnabled(true);
						frame.getBtnRedo().setEnabled(false);
					}
				}
				notifyAllObservers(selectedObjects.size());
				frame.getView().repaint();
			}
		}
		
		//MODIFY
		else if (e.getSource() == frame.getBtnModify()) {
			if (selectedObjects.size() == 1) {
				Shape shape = selectedObjects.get(0);
				String s = shape.getClass().getSimpleName();
				
				switch (s) {
				//Tacka
				case "Point":
					try {
						Point p = (Point) shape;
						Point point = pointDialog(p.getX(), p.getY(), p.getColor(), true);
						if (point != null) {
							point.setSelected(true);
							
							String[] attributes = p.getName().split(",");
							String name = attributes[0];
							int index = model.getShapes().indexOf(shape);
							
							point.setNameString(name + "," + point.toString() + ",color," + String.valueOf(point.getColor().getRGB()));
							PointModify pointModify = new PointModify(model, p, point, index, p.getName() + ",Modify to," + point.getName(), selectedObjects);
							commandManager.execute(pointModify);
							
							frame.getBtnUndo().setEnabled(true);
							frame.getBtnRedo().setEnabled(false);
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
					break;
					//Linija
				case "Line":
					try {
						Line l = (Line) shape;
						Line line = lineDialog(l.getStartPoint().getX(), l.getStartPoint().getY(), l.getEndPoint().getX(), l.getEndPoint().getY(), l.getColor(), true);
						if (line != null) {
							int index = model.getShapes().indexOf(shape);
							
							String[] attributes = l.getName().split(",");
							String name = attributes[0];
							
							line.setNameString(name + "," + line.toString());
							line .setSelected(true);
							
							LineModify lineModify = new LineModify(model, l, line, index, l.getName() + ",Modify to," + line.getName(), selectedObjects);
							commandManager.execute(lineModify);
							
							frame.getBtnUndo().setEnabled(true);
							frame.getBtnRedo().setEnabled(false);
						}
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
					break;
					//Pravougaonik
				case "Rectangle":
						try {
							Rectangle r = (Rectangle) shape;
							Rectangle rect = rectDialog(r.getUpperLeftPoint().getX(), r.getUpperLeftPoint().getY(),
									r.getHeight(), r.getWidth(), r.getInnerColor(), r.getOuterColor(), true);
							if (rect != null) {
								int index = model.getShapes().indexOf(shape);
								rect.setSelected(true);
								String[] attributes = r.getName().split(",");
								String name = attributes[0];
								rect.setNameString(name + "," + rect.toString());

								RectangleModify rectangleModify = new RectangleModify(model, r, rect, index,
										r.getName() + ",Modify to," + rect.getName(), selectedObjects);
								commandManager.execute(rectangleModify);

								frame.getBtnUndo().setEnabled(true);
								frame.getBtnRedo().setEnabled(false);
							}

						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, ex.getMessage());
						}
						break;
					//Krug
				case "Circle":
					try {
						Circle c = (Circle) shape;
						Circle circle = circleDialog(c.getCenter().getX(), c.getCenter().getY(), c.getRadius(),
								c.getInnerColor(), c.getOuterColor(), true);
						if (circle != null) {
							circle.setSelected(true);

							String[] attributes = c.getName().split(",");
							String name = attributes[0];
							circle.setNameString(name + "," + circle.toString());
							int index = model.getShapes().indexOf(shape);

							CircleModify circleModify = new CircleModify(model, c, circle, index,
									c.getName() + ",Modify to," + circle.getName(), selectedObjects);
							commandManager.execute(circleModify);

							frame.getBtnUndo().setEnabled(true);
							frame.getBtnRedo().setEnabled(false);
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
					break;
					//Krofna
				case "Donut":
					try {
						Donut d = (Donut) shape;
						Donut donut = donutDialog(d.getCenter().getX(), d.getCenter().getY(), d.getInnerRadius(),
								d.getRadius(), d.getInnerColor(), d.getOuterColor(), true);
						if (donut != null) {
							donut.setSelected(true);
							
							String[] attributes = d.getName().split(",");
							String name = attributes[0];
							donut.setNameString(name + "," + donut.toString());
							int index = model.getShapes().indexOf(shape);
							
							DonutModify donutModify = new DonutModify(model, d, donut, index,
									d.getName() + ",Modify to," + donut.getName(), selectedObjects);
							commandManager.execute(donutModify);

							frame.getBtnUndo().setEnabled(true);
							frame.getBtnRedo().setEnabled(false);
						}

					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
					break;
					//Heksagon
				case "HexagonAdapter":
					try {
						HexagonAdapter h = (HexagonAdapter) shape;
						HexagonAdapter hex = hexDialog(h.getX(), h.getY(), h.getR(), h.getInnerColor(), h.getOuterColor(), true);
						
						if (hex != null) {
							hex.setSelected(true);
							
							String[] attributes = h.getName().split(",");
							String name = attributes[0];
							hex.setNameString(name + "," + hex.toString());
							int index = model.getShapes().indexOf(shape);
							
							HexModify hexModify = new HexModify(model, h, hex, index, h.getName() + ",Modify to," + hex.getName(), selectedObjects);
							commandManager.execute(hexModify);
							
							frame.getBtnUndo().setEnabled(true);
							frame.getBtnRedo().setEnabled(false);
						}
						
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, ex.getMessage());
					}
					break;
					
				default:
					
					break;
				}
				
				frame.getView().repaint();
			} else {
				JOptionPane.showMessageDialog(null, "Izaberite 1 objekat!");
			}
		}
		
		//Undo
		else if (e.getSource() == frame.getBtnUndo()) {
			commandManager.undo();
			frame.getBtnRedo().setEnabled(true);
			if(commandManager.sizeNormal() == 0)
				frame.getBtnUndo().setEnabled(false);
			else
				frame.getBtnUndo().setEnabled(true);
			notifyAllObservers(selectedObjects.size());
			frame.getView().repaint();
		}
		
		
		//Redo
		else if (e.getSource() == frame.getBtnRedo()) {
			commandManager.redo();
			frame.getBtnUndo().setEnabled(true);
			if (commandManager.sizeReverse() == 0)
				frame.getBtnRedo().setEnabled(false);
			else
				frame.getBtnRedo().setEnabled(true);
			notifyAllObservers(selectedObjects.size());
			frame.getView().repaint();
		}
		
		
		//ToBack
		else if (e.getSource() == frame.getBtnToBack()) {
			if (selectedObjects.size() == 1) {
				Shape shape = selectedObjects.get(0);
				int index = model.getShapes().indexOf(shape);
				CmdToBack cmd = new CmdToBack(model, shape, index, shape.getName() + " - To Back");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getView().repaint();
			}
		}
		
		
		//ToFront
		else if (e.getSource() == frame.getBtnToFront()) {
			if (selectedObjects.size() == 1) {
				
				Shape shape = selectedObjects.get(0);
				int index = model.getShapes().indexOf(shape);
				
				CmdToFront cmd = new CmdToFront(model, shape, index, shape.getName() + " - To Front");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getView().repaint();
			}
		}
		
		
		//BringBack
		else if (e.getSource() == frame.getBtnBringBack()) {
			if (selectedObjects.size() == 1) {
				Shape shape = selectedObjects.get(0);
				int index = model.getShapes().indexOf(shape);
				CmdBringToBack cmd = new CmdBringToBack(model, shape, index, shape.getName() + " - Bring Back");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getView().repaint();
			}
		}
		
		
		//BringFront
		else if (e.getSource() == frame.getBtnBringFront()) {
			if (selectedObjects.size() == 1) {
				Shape shape = selectedObjects.get(0);
				int index = model.getShapes().indexOf(shape);
				int length = model.getShapes().size();
				CmdBringToFront cmd = new CmdBringToFront(model, shape, index, length, shape.getName() + " - Bring Front");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				frame.getView().repaint();
			}
		}
		
	}
	
	//Metode za dijaloge
	
	private Point pointDialog(int x, int y, Color outerColor, boolean editable) {
		
		DlgPoint dlg = new DlgPoint(x, y, outerColor);
		dlg.getTextFieldX().setEditable(editable);
		dlg.getTextFieldY().setEditable(editable);
		dlg.setVisible(true);
		
		if(dlg.isAccepted()) {
			x = Integer.parseInt(dlg.getTextFieldX().getText());
			y = Integer.parseInt(dlg.getTextFieldY().getText());
			outerColor = dlg.getBtnColor().getBackground();
			return new Point(x, y, outerColor);
		}
		
		return null;
	}
	
	private Line lineDialog(int x1, int y1, int x2, int y2, Color outerColor, boolean editable) {
		
		DlgLine dlg = new DlgLine(x1, y1, x2, y2, outerColor);
		dlg.getTextFieldX().setEditable(editable);
		dlg.getTextFieldY().setEditable(editable);
		dlg.getTextFieldX2().setEditable(editable);
		dlg.getTextFieldY2().setEditable(editable);
		dlg.setVisible(true);
		
		if (dlg.isAccepted()) {
			x1 = Integer.parseInt(dlg.getTextFieldX().getText());
			y1 = Integer.parseInt(dlg.getTextFieldY().getText());
			x2 = Integer.parseInt(dlg.getTextFieldX2().getText());
			y2 = Integer.parseInt(dlg.getTextFieldY2().getText());
			outerColor = dlg.getBtnColor().getBackground();
			Point p1 = new Point(x1, y1);
			Point p2 = new Point(x2, y2);
			return new Line(p1, p2, outerColor);
		}
		
		return null;
	}
	
	private Rectangle rectDialog(int x, int y, int height, int width, Color innerColor, Color outerColor,
			boolean editable) throws Exception {

		DlgRectangle dlg = new DlgRectangle(x, y, innerColor, outerColor);
		dlg.getTextFieldX().setEditable(editable);
		dlg.getTextFieldY().setEditable(editable);
		if (editable) {
			dlg.getTextFieldHeight().setText(String.valueOf(height));
			dlg.getTextFieldWidth().setText(String.valueOf(width));
		}
		dlg.setVisible(true);

		if (dlg.isAccepted()) {
			x = Integer.parseInt(dlg.getTextFieldX().getText());
			y = Integer.parseInt(dlg.getTextFieldY().getText());
			innerColor = dlg.getBtnInnerColor().getBackground();
			outerColor = dlg.getBtnOuterColor().getBackground();
			height = Integer.parseInt(dlg.getTextFieldHeight().getText());
			width = Integer.parseInt(dlg.getTextFieldWidth().getText());

			Point p = new Point(x, y);
			return new Rectangle(p, width, height, innerColor, outerColor);
		}

		return null;
	}
	
	private Circle circleDialog(int x, int y, int radius, Color innerColor, Color outerColor, boolean editable)
			throws Exception {

		DlgCircle dlg = new DlgCircle(x, y, innerColor, outerColor);
		if (editable)
			dlg.getTextFieldRadius().setText(String.valueOf(radius));
		dlg.getTxtXCoordinate().setEditable(editable);
		dlg.getTxtYCoordinate().setEditable(editable);

		dlg.setVisible(true);

		if (dlg.isAccepted()) {

			x = Integer.parseInt(dlg.getTxtXCoordinate().getText());
			y = Integer.parseInt(dlg.getTxtYCoordinate().getText());

			Point p = new Point(x, y);

			radius = Integer.parseInt(dlg.getTextFieldRadius().getText());
			innerColor = dlg.getBtnFillColor().getBackground();
			outerColor = dlg.getBtnEdgeColor().getBackground();

			return new Circle(p, radius, innerColor, outerColor);
		}

		return null;
	}
	
	private Donut donutDialog(int x, int y, int innerRadius, int outerRadius, Color innerColor, Color outerColor,
			boolean editable) throws Exception {

		DlgDonut dlg = new DlgDonut(x, y, innerColor, outerColor);
		if (editable) {
			dlg.getTextFieldInRadius().setText(String.valueOf(innerRadius));
			dlg.getTextFieldOutRadius().setText(String.valueOf(outerRadius));
		}
		dlg.getTextFieldX().setEditable(editable);
		dlg.getTextFieldY().setEditable(editable);

		dlg.setVisible(true);

		if (dlg.isAccepted()) {

			x = Integer.parseInt(dlg.getTextFieldX().getText());
			y = Integer.parseInt(dlg.getTextFieldY().getText());
			Point p = new Point(x, y);
			innerRadius = Integer.parseInt(dlg.getTextFieldInRadius().getText());
			outerRadius = Integer.parseInt(dlg.getTextFieldOutRadius().getText());

			innerColor = dlg.getBtnInnerColor().getBackground();
			outerColor = dlg.getBtnOuterColor().getBackground();
			if(innerRadius == 0)
				JOptionPane.showMessageDialog(new JFrame(), "Inner radius must be greater than 0","Error", JOptionPane.WARNING_MESSAGE);
			else if (innerRadius >= outerRadius)
				JOptionPane.showMessageDialog(new JFrame(), "Outer radius must be greater than inner radius","Error", JOptionPane.WARNING_MESSAGE);
			else
			    return new Donut(p, innerRadius, outerRadius, innerColor, outerColor);
		}

		return null;
	}
	
	private HexagonAdapter hexDialog(int x, int y, int r, Color innerColor, Color outerColor, boolean editable) {
		
		DlgHexagon dlg = new DlgHexagon(x, y, innerColor, outerColor);
		
		if (editable) {
			dlg.getTextFieldRadius().setText(String.valueOf(r));
		}
		dlg.getTextFieldX().setEditable(editable);
		dlg.getTextFieldY().setEditable(editable);
		
		dlg.setVisible(true);
		
		if (dlg.isAccepted()) {
			
			x = Integer.parseInt(dlg.getTextFieldX().getText());
			y = Integer.parseInt(dlg.getTextFieldY().getText());
			r = Integer.parseInt(dlg.getTextFieldRadius().getText());
			innerColor = dlg.getBtnInnerColor().getBackground();
			outerColor = dlg.getBtnOuterColor().getBackground();
			
			return new HexagonAdapter(x, y, r, innerColor, outerColor);
		}
		
		return null;
	}
	
	private void notifyAllObservers(int size) {
		
		if (size > 0) {
			if (size == 1) {
				observer.setButtonModifyEnabled(true);
				observer.setButtonBringBackEnabled(true);
				observer.setButtonBringFrontEnabled(true);
				observer.setButtonToBackEnabled(true);
				observer.setButtonToFrontEnabled(true);
			} else {
				observer.setButtonModifyEnabled(false);
				observer.setButtonBringBackEnabled(false);
				observer.setButtonBringFrontEnabled(false);
				observer.setButtonToBackEnabled(false);
				observer.setButtonToFrontEnabled(false);
				
			}
			observer.setButtonDeleteEnabled(true);
		} else {
				observer.setButtonDeleteEnabled(false);
				observer.setButtonModifyEnabled(false);
				observer.setButtonBringBackEnabled(false);
				observer.setButtonBringFrontEnabled(false);
				observer.setButtonToBackEnabled(false);
				observer.setButtonToFrontEnabled(false);
		}
	}
	
	public void save(File fileToSave, File fileToSaveLog) throws IOException {
			SaveManager savePainting = new SaveManager(new SavePainting());
			SaveManager saveLog = new SaveManager(new SaveLog());
			
			savePainting.save(model, fileToSave);
			saveLog.save(frame, fileToSaveLog);
	}
	
	@SuppressWarnings("unchecked")
	public void load(File fileToLoad) throws ClassNotFoundException, IOException {
			frame.getBtnNext().setEnabled(false);
			frame.getLogArea().setText("");
			File f = new File(fileToLoad.getAbsolutePath().replaceAll("bin", "txt"));
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line;
			
			while ((line = br.readLine()) != null) {
				
				frame.getLogArea().append(line + '\n');
			}
			br.close();
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileToLoad));
			try {
					model.getShapes().clear();
					selectedObjects.clear();
					commandManager.clearNormal();
					commandManager.clearReverse();
					frame.getBtnUndo().setEnabled(false);
					frame.getBtnRedo().setEnabled(false);
					
					model.getShapes().addAll((ArrayList<Shape>) ois.readObject());
					
					frame.getView().repaint();
			} catch (SocketTimeoutException exc) {

			} catch (InvalidClassException ex) {

			} catch (EOFException exc) {
				ois.close();

			} catch (IOException exc) {
				exc.printStackTrace();
			}
			for (int i = 0; i < model.getShapes().size(); i++) {
				if (model.getShapes().get(i).isSelected()) {
					selectedObjects.add(model.getShapes().get(i));
				}
			}
			
			ois.close();
	}
	
	public void loadOneByOne(File fileToLoad) throws IOException {
		
		frame.getBtnNext().setEnabled(true);
		frame.getBtnUndo().setEnabled(false);
		frame.getBtnRedo().setEnabled(false);
		
		model.getShapes().clear();
		selectedObjects.clear();
		commandManager.clearNormal();
		commandManager.clearReverse();
		notifyAllObservers(0);
		frame.getLogArea().setText("");
		txtFileLines.clear();
		
		frame.repaint();
		
		//Reset brojaca
		i = 0;
		pointerCount = 1;
		lineCount = 1;
		rectangleCount = 1;
		circleCount = 1;
		donutCount = 1;
		hexagonCount = 1;
		
		BufferedReader br = new BufferedReader(new FileReader(fileToLoad));
		String line;
		
		while ((line = br.readLine()) != null) {
			txtFileLines.add(line);
		}
		
		br.close();
	}
	
	public void loadNext() throws Exception {
		
		String line = txtFileLines.get(i);
		Shape shape = null;
		
		if (line.contains("Undo")) {
			commandManager.undo();
			notifyAllObservers(selectedObjects.size());
			
		} else if (line.contains("Redo")) {
			commandManager.redo();
			notifyAllObservers(selectedObjects.size());
		} else if (line.contains("To Front")) {
			
			shape = selectedObjects.get(0);
			int index = model.getShapes().indexOf(shape);
			
			CmdToFront cmd = new CmdToFront(model, shape, index, shape.getName() + " - To Front");
			commandManager.execute(cmd);
			
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();
			
		} else if (line.contains("To Back")) {
			
			shape = selectedObjects.get(0);
			int index = model.getShapes().indexOf(shape);
			CmdToBack cmd = new CmdToBack(model, shape, index, shape.getName() + " - To Back");
			commandManager.execute(cmd);
			
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();
			
		} else if (line.contains("Bring Front")) {
			
			shape = selectedObjects.get(0);
			int index = model.getShapes().indexOf(shape);
			int length = model.getShapes().size();
			CmdBringToFront cmd = new CmdBringToFront(model, shape, index, length, shape.getName() + " - Bring Front");
			commandManager.execute(cmd);
			
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();
			
		} else if (line.contains("Bring Back")) {
			
			shape = selectedObjects.get(0);
			int index = model.getShapes().indexOf(shape);
			CmdBringToBack cmd = new CmdBringToBack(model, shape, index, shape.getName() + " - Bring Back");
			commandManager.execute(cmd);
			
			frame.getBtnUndo().setEnabled(true);
			frame.getBtnRedo().setEnabled(false);
			frame.getView().repaint();
		} else if (line.contains("Add")) {
			line = line.replaceAll(" - Add", "");
			if(line.contains("Point")) {
				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				Color color = new Color(Integer.parseInt(attributes[6]));
				
				Point point = new Point(x, y, color);
				
				point.setNameString("Point" + pointerCount++ + "," + point.toString() + ",color," + String.valueOf(point.getColor().getRGB()));
				
				CmdAdd cmd = new CmdAdd(model, point, point.getName() + " - Add");
				commandManager.execute(cmd);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
				
			}  else if (line.contains("Line")) {
				
				String[] attributes = line.split(",");
				int x1 = Integer.parseInt(attributes[2]);
				int y1 = Integer.parseInt(attributes[4]);
				int x2 = Integer.parseInt(attributes[6]);
				int y2 = Integer.parseInt(attributes[8]);
				Color color = new Color(Integer.parseInt(attributes[10]));
				
				Point startPoint = new Point(x1, y1);
				Point endPoint = new Point(x2, y2);
				
				Line lineObject = new Line(startPoint, endPoint, color);
				
				lineObject.setNameString("Line" + lineCount++ + "," + lineObject.toString());
				
				CmdAdd cmd =  new CmdAdd(model, lineObject, lineObject.getName() + " - Add");
				commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Rectangle")) {

				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				int height = Integer.parseInt(attributes[6]);
				int width = Integer.parseInt(attributes[8]);
				Color outerColor = new Color(Integer.parseInt(attributes[10]));
				Color innerColor = new Color(Integer.parseInt(attributes[12]));

				Point upperLeftPoint = new Point(x, y);

				Rectangle rect = new Rectangle(upperLeftPoint, width, height, innerColor, outerColor);

				rect.setNameString("Rectangle" + rectangleCount++ + "," + rect.toString());
				CmdAdd cmd = new CmdAdd(model, rect, rect.getName() + " - Add");
				commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Circle")) {

				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				int radius = Integer.parseInt(attributes[6]);
				Color outerColor = new Color(Integer.parseInt(attributes[8]));
				Color innerColor = new Color(Integer.parseInt(attributes[10]));

				Point center = new Point(x, y);

				Circle circle = new Circle(center, radius, innerColor, outerColor);

				circle.setNameString("Circle" + circleCount++ + "," + circle.toString());
				CmdAdd cmd = new CmdAdd(model, circle, circle.getName() + " - Add");
				commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);

			} else if (line.contains("Donut")) {

				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				int radius = Integer.parseInt(attributes[6]);
				Color outerColor = new Color(Integer.parseInt(attributes[8]));
				Color innerColor = new Color(Integer.parseInt(attributes[10]));
				int innerRadius = Integer.parseInt(attributes[12]);

				Point center = new Point(x, y);

				Donut donut = new Donut(center, innerRadius, radius, innerColor, outerColor);

				donut.setNameString("Donut" + donutCount++ + "," + donut.toString());
				CmdAdd cmd = new CmdAdd(model, donut, donut.getName() + " - Add");
				commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
				
			} else if (line.contains("Hexagon")) {

				String[] attributes = line.split(",");
				int x = Integer.parseInt(attributes[2]);
				int y = Integer.parseInt(attributes[4]);
				int radius = Integer.parseInt(attributes[6]);
				Color outerColor = new Color(Integer.parseInt(attributes[8]));
				Color innerColor = new Color(Integer.parseInt(attributes[10]));


				HexagonAdapter hex = new HexagonAdapter(x, y, radius, innerColor, outerColor);

				hex.setNameString("Hexagon" + hexagonCount++ + "," + hex.toString());
				CmdAdd cmd = new CmdAdd(model, hex, hex.getName() + " - Add");
				commandManager.execute(cmd);
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
					
			}
		} else if (line.contains("Modify to")) {
			
			String[] splitString = line.split(",Modify to");
			line = line.replace(",Modify to", "");
			if (line.contains("Point")) {
				
				Point p = new Point();
				int index = 0;
				
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						p = (Point) model.get(i);
						index = model.getShapes().indexOf(p);
						break;
					}
				}
				
				String[] attributes = line.split(",");
				
				String name = attributes[7];
				int x = Integer.parseInt(attributes[9]);
				int y = Integer.parseInt(attributes[11]);
				Color color = new Color(Integer.parseInt(attributes[13]));
				
				Point point = new Point(x, y, color);
				point.setSelected(true);
				point.setNameString(name + "," +point.toString() + ",color," + String.valueOf(point.getColor().getRGB()));
				
				PointModify pointModify = new PointModify(model, p, point, index, p.getName() + ",Modify to," + point.getName(), selectedObjects);
				commandManager.execute(pointModify);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Line")) {
				
				Line l = new Line();
				int index = 0;
				
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						l = (Line) model.get(i);
						index = model.getShapes().indexOf(l);
						break;
					}
				}
				
				String[] attributes = line.split(",");
				String name = attributes[11];
				int x1 = Integer.parseInt(attributes[13]);
				int y1 = Integer.parseInt(attributes[15]);
				int x2 = Integer.parseInt(attributes[17]);
				int y2 = Integer.parseInt(attributes[19]);
				Color color = new Color(Integer.parseInt(attributes[21]));
				Point startPoint = new Point(x1, y1);
				Point endPoint = new Point(x2, y2);
				
				Line line2 = new Line(startPoint, endPoint, color);
				line2.setSelected(true);
				line2.setNameString(name + "," + line2.toString());
				
				LineModify lineModify = new LineModify(model, l, line2, index, l.getName() + ",Modify to," + line2.getName(), selectedObjects);
				commandManager.execute(lineModify);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Rectangle")) {
				
				Rectangle r = new Rectangle();
				int index = 0;
				
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						r = (Rectangle) model.get(i);
						index = model.getShapes().indexOf(r);
						break;
					}
				}
				
				String[] attributes = line.split(",");
				
				String name = attributes[13];
				int x = Integer.parseInt(attributes[15]);
				int y = Integer.parseInt(attributes[17]);
				int height = Integer.parseInt(attributes[19]);
				int width = Integer.parseInt(attributes[21]);
				Color outerColor = new Color(Integer.parseInt(attributes[23]));
				Color innerColor = new Color(Integer.parseInt(attributes[25]));
				Point point = new Point(x, y);
				
				Rectangle rectangle = new Rectangle(point, width, height, innerColor, outerColor);
				rectangle.setSelected(true);
				rectangle.setNameString(name + "," + rectangle.toString());
				
				RectangleModify rectangleModify = new RectangleModify(model, r, rectangle, index, r.getName() + ",Modify to," + rectangle.getName(), selectedObjects);
				commandManager.execute(rectangleModify);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
				
			} else if (line.contains("Circle")) {

				Circle c = new Circle();
				int index = 0;

				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						c = (Circle) model.get(i);
						index = model.getShapes().indexOf(c);
						break;
					}
				}

				String[] attributes = line.split(",");

				String name = attributes[11];
				int x = Integer.parseInt(attributes[13]);
				int y = Integer.parseInt(attributes[15]);
				int radius = Integer.parseInt(attributes[17]);
				Color outerColor = new Color(Integer.parseInt(attributes[19]));
				Color innerColor = new Color(Integer.parseInt(attributes[21]));
				Point center = new Point(x, y);

				Circle circle = new Circle(center, radius, innerColor, outerColor);
				circle.setSelected(true);
				circle.setNameString(name + "," + circle.toString());

				CircleModify circleModify = new CircleModify(model, c, circle, index,
						c.getName() + ",Modify to," + circle.getName(), selectedObjects);
				commandManager.execute(circleModify);

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);

				
			} else if (line.contains("Donut")) {
				
				Donut d = new Donut();
				int index = 0;

				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						d = (Donut) model.get(i);
						index = model.getShapes().indexOf(d);
						break;
					}
				}

				String[] attributes = line.split(",");

				String name = attributes[13];
				int x = Integer.parseInt(attributes[15]);
				int y = Integer.parseInt(attributes[17]);
				int radius = Integer.parseInt(attributes[19]);
				Color outerColor = new Color(Integer.parseInt(attributes[21]));
				Color innerColor = new Color(Integer.parseInt(attributes[23]));
				int innerRadius = Integer.parseInt(attributes[25]);
				Point center = new Point(x, y);

				Donut donut = new Donut(center, innerRadius, radius, innerColor, outerColor);
				donut.setSelected(true);
				donut.setNameString(name + "," + donut.toString());

				DonutModify donutModify = new DonutModify(model, d, donut, index,
						d.getName() + ",Modify to," + donut.getName(), selectedObjects);
				commandManager.execute(donutModify);

				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);

				
			} else if (line.contains("Hexagon")) {
				
				HexagonAdapter h = new HexagonAdapter();
				int index = 0;
				
				for (int i = 0; i < model.getShapes().size(); i++) {
					if (splitString[0].equals(model.get(i).getName())) {
						h = (HexagonAdapter) model.get(i);
						index = model.getShapes().indexOf(h);
						break;
					}
				}
				
				String[] attributes = line.split(",");
				
				String name = attributes[11];
				int x = Integer.parseInt(attributes[13]);
				int y = Integer.parseInt(attributes[15]);
				int radius = Integer.parseInt(attributes[17]);
				Color outerColor = new Color(Integer.parseInt(attributes[19]));
				Color innerColor = new Color(Integer.parseInt(attributes[21]));
				
				HexagonAdapter hex = new HexagonAdapter(x, y, radius, innerColor, outerColor);
				hex.setSelected(true);
				hex.setNameString(name + "," + hex.toString());
				
				HexModify hexModify = new HexModify(model, h, hex, index, h.getName() + ",Modify to," + hex.getName(), selectedObjects);
				commandManager.execute(hexModify);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
		
				
			}
		}
		
		else if (line.contains("Deleted")) {
			line = line.replace(" - Deleted", "");
			
			shape = selectedObjects.get(0);
			if (shape.isSelected()) {
				int index = model.getShapes().indexOf(shape);
				CmdRemove cmdRemove = new CmdRemove(model, shape, index, shape.getName() + " - Deleted", selectedObjects);
				selectedObjects.remove(shape);
				commandManager.execute(cmdRemove);
				
				frame.getBtnUndo().setEnabled(true);
				frame.getBtnRedo().setEnabled(false);
			}
		} else if (line.contains("Selected")) {
			line = line.replace(" - Selected", "");
			
			for (int i = model.getShapes().size() - 1; i >= 0; i--) {
				shape = model.get(i);
				
				if (shape.getName().equals(line)) {
					CmdSelect selectCommand = new CmdSelect(shape, selectedObjects, shape.getName() + " - Selected");
					commandManager.execute(selectCommand);
					
					notifyAllObservers(selectedObjects.size());
					break;
				}
			}
		} else if (line.contains("Deselected")) {
			
			line = line.replace(" - Deselected", "");
			
			for (int i = model.getShapes().size() - 1; i >= 0; i--) {
				shape = model.get(i);
				
				if (shape.getName().equals(line)) {
					CmdDeselect deselectCommand = new CmdDeselect(shape, selectedObjects, shape.getName() + " - Deselected");
					commandManager.execute(deselectCommand);
					
					notifyAllObservers(selectedObjects.size());
					break;
				}
			}
		}
		i++;
		if (i == txtFileLines.size() || txtFileLines.get(i) == null) {
			frame.getBtnNext().setEnabled(false);
		}
		frame.repaint();
	}
}
