package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.filechooser.FileNameExtensionFilter;

import geometry.Point;
import mvc.toolbars.ShapesToolbar;

public class DrawingFrame extends JFrame {
	
	
	private static final long serialVersionUID = 1L;
	private DrawingController controller;
	private DrawingView view;
	
	
	//Grupa za dugmice
	private final ButtonGroup tools = new ButtonGroup();
	private final ButtonGroup colors = new ButtonGroup();
	
	//variable
	public Color innerColor = Color.RED;
	public Color outerColor = Color.RED;
	public Point startPoint = null;
	
	//Ikonice
	ImageIcon iconToBack = new ImageIcon("src/resources/iconToBack.png");
	ImageIcon iconToFront = new ImageIcon("src/resources/iconToFront.png");
	ImageIcon iconBringBack = new ImageIcon("src/resources/iconBringBack.png");
	ImageIcon iconBringFront = new ImageIcon("src/resources/iconBringFront.png");
	ImageIcon iconUndo = new ImageIcon("src/resources/iconUndo.png");
	ImageIcon iconRedo = new ImageIcon("src/resources/iconRedo.png");
	ImageIcon iconSelect = new ImageIcon("src/resources/iconSelect.png");
	ImageIcon iconModify = new ImageIcon("src/resources/iconModify.png");
	ImageIcon iconDelete = new ImageIcon("src/resources/iconDelete.png");

	ImageIcon iconNext = new ImageIcon("src/resources/iconNext.png");

	private final ShapesToolbar leftPanel = new ShapesToolbar();
	private final JPanel topPanel = new JPanel();
	private final JPanel rightPanel = new JPanel();
	
	
	//Komponente
	private final JToggleButton btnSelect = new JToggleButton();
	private final JButton btnModify = new JButton();
	private final JButton btnDelete = new JButton();
	private final JButton btnToBack = new JButton();
	private final JButton btnToFront = new JButton();
	private final JButton btnBringBack = new JButton();
	private final JButton btnBringFront = new JButton();
	private JButton btnUndo = new JButton();
	private JButton btnRedo = new JButton();
	private final JButton btnInnerColor = new JButton("InnerColor");
	private final JButton btnOuterColor = new JButton("OuterColor");
	private final JTextArea logArea = new JTextArea();
	private final JScrollPane scrollPane = new JScrollPane(logArea);
	private final JButton btnNext = new JButton();
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu menuFile = new JMenu("File");
	private final JMenuItem openItem = new JMenuItem("Open");
	private final JMenuItem saveItem = new JMenuItem("Save");
	
	public DrawingFrame() {
		
		//Meni
		menuFile.add(openItem);
		menuFile.add(saveItem);
		menuBar.add(menuFile);
		setJMenuBar(menuBar);
		
		//podesavanje icona
		setupIconBtn(btnNext, iconNext, 50, 50);
		setupIconBtn(btnToBack, iconToBack, 50, 50);
		setupIconBtn(btnToFront, iconToFront, 50, 50);
		setupIconBtn(btnBringBack, iconBringBack, 50, 50);
		setupIconBtn(btnBringFront, iconBringFront, 50, 50);
		setupIconBtn(btnUndo, iconUndo, 50, 50);
		setupIconBtn(btnRedo, iconRedo, 50, 50);
		setupIconBtn(btnModify, iconModify, 50, 50);
		setupIconBtn(btnDelete, iconDelete, 50, 50);
		setupIconTlg(btnSelect, iconSelect, 50, 50);

		
		
		//Panel layouts
		leftPanel.setLayout(new GridLayout(0, 1, 10, 10));
		topPanel.setLayout(new FlowLayout()); //iako je podrazumevano
		rightPanel.setLayout(new GridLayout(0, 1, 3, 3));
		view = new DrawingView();
		
		//velicina panela
		view.setPreferredSize(new Dimension(800, 600));
		leftPanel.setPreferredSize(new Dimension(110, 200));
		rightPanel.setPreferredSize(new Dimension(120, 200));
		
		//dodavanje btnNext
		btnNext.setEnabled(false);
		tools.add(btnNext);
		topPanel.add(btnNext);
		
		//dodavanje btnSelect
		tools.add(btnSelect);
		topPanel.add(btnSelect);
		
		//dodavanje btnModify
		tools.add(btnModify);
		topPanel.add(btnModify);
		
		//dodavanje btnDelete
		tools.add(btnDelete);
		topPanel.add(btnDelete);
		
		//dodavanje btnToBack
		tools.add(btnToBack);
		topPanel.add(btnToBack);
		
		//dodavanje btnToFront
		tools.add(btnToFront);
		topPanel.add(btnToFront);
		
		//dodavanje btnBringBack
		tools.add(btnBringBack);
		topPanel.add(btnBringBack);
		
		//dodavanje btnBringFront
		tools.add(btnBringFront);
		topPanel.add(btnBringFront);

		
		//dodavanje dugmica Undo and Redo
		topPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		topPanel.add(btnUndo);
		topPanel.add(btnRedo);
		
		//dodavanje boje dugmica
		btnInnerColor.setBackground(innerColor);
		btnOuterColor.setBackground(outerColor);
		colors.add(btnInnerColor);
		colors.add(btnOuterColor);
		rightPanel.add(btnInnerColor);
		rightPanel.add(btnOuterColor);
		
		//Iskljucivanje dugmica Delete  i Modify jer jos ni je nije selektovano
		btnModify.setEnabled(false);
		btnDelete.setEnabled(false);
		
		//podesavanje dugmica koja ce biti iskljucena
		btnBringBack.setEnabled(false);
		btnBringFront.setEnabled(false);
		btnToBack.setEnabled(false);
		btnToFront.setEnabled(false);
		
		//postavljanje Undo i Redo na disable
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);
		
		
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
	}
	
	public JToggleButton getBtnSelect() {
		return btnSelect;
	}
	
	public JButton getBtnModify() {
		return btnModify;
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
	
	private void setupIconBtn(JButton btn, ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);
		btn.setIcon(icon);
	}
	
	private void setupIconTlg(JToggleButton btn, ImageIcon icon, int width, int height) {
		Image img = icon.getImage();
		Image newImg =img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		icon = new ImageIcon(newImg);
		btn.setIcon(icon);
	}
	
	private void setupListeners() {
		
		
		//View
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		
		//Modify
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformed(e);
			}
		});
		
		//dugme Delete
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformed(e);
			}
		});
		
		//dugme Undo
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformed(e);
			}
		});
		
		//dugme Redo
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformed(e);
			}
		});
		
		//dugme ToBack
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformed(e);
			}
		});
		
		//dugme ToFront
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformed(e);
			}
		});
		
		//dugme BringBack
		btnBringBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformed(e);
			}
		});
		
		//dugme BringFornt
		btnBringFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.actionPerformed(e);
			}
		});
		
		
		//Save
		saveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				JFileChooser c = new JFileChooser();
				FileNameExtensionFilter f = new FileNameExtensionFilter("Bin", "bin");
				c.setFileFilter(f);
				
				int userSelection = c.showSaveDialog(null);
				
				if(userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToSave = c.getSelectedFile();
					File fileToSaveLog;
					String filePath = fileToSave.getAbsolutePath();
					
					if(!filePath.endsWith(".bin") && !filePath.contains(".")) {
						fileToSave = new File(filePath + ".bin");
						fileToSaveLog = new File(filePath + ".txt");
					}
					
					String filename = fileToSave.getPath();
					
					if(filename.substring(filename.lastIndexOf("."), filename.length()).contentEquals(".bin")) {
						try {
							
								filename = fileToSave.getAbsolutePath().substring(0, filename.lastIndexOf(".")) + ".txt";
								System.out.println(filename);
								fileToSaveLog = new File(filename);
								controller.save(fileToSave, fileToSaveLog);
							} catch (IOException e) {
								e.printStackTrace();
							}
						
					} else {
						JOptionPane.showMessageDialog(null, "File is not valid");
					}
				}
			}
		});
		
		//Open
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				FileNameExtensionFilter f = new FileNameExtensionFilter("bin", "bin", "txt");
				
				c.setFileFilter(f);
				
				c.setDialogTitle("Open");
				int userSelection = c.showOpenDialog(null);
				
				if(userSelection == JFileChooser.APPROVE_OPTION) {
					File fileToLoad = c.getSelectedFile();
					String filename = fileToLoad.getPath();
					if(filename.substring(filename.lastIndexOf("."), filename.length()).contentEquals(".bin")) {
						try {
								controller.load(fileToLoad);
						} catch (IOException m) {
							m.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
					} else if (filename.substring(filename.lastIndexOf("."), filename.length()).contentEquals(".txt")) {
						try {
								controller.loadOneByOne(fileToLoad);
						} catch (IOException m) {
							m.printStackTrace();
						}
					} else {
							JOptionPane.showMessageDialog(null, "The file is not valid");
					}
				}
			}
		});
		
		btnNext.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
						controller.loadNext();
				} catch(Exception e) {
						e.printStackTrace();
				}
			}
		});
	}
	
	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	public JButton getBtnUndo() {
		return btnUndo;
	}
	
	public void setBtnUndo(JButton btnUndo) {
		this.btnUndo = btnUndo;
	}
	
	public JButton getBtnRedo() {
		return btnRedo;
	}
	
	public void setBtnRedo(JButton btnRedo) {
		this.btnRedo = btnRedo;
	} 
	
	public JTextArea getLogArea() {
		return logArea;
	}
	
	public JButton getBtnToBack() {
		return btnToBack;
	}
	
	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnBringBack() {
		return btnBringBack;
	}
	
	public JButton getBtnBringFront() {
		return btnBringFront;
	}
	
	public JButton getBtnNext() {
		return btnNext;
	}
}
