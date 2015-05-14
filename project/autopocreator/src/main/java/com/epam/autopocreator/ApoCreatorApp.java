package com.epam.autopocreator;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import com.epam.autopocreator.pageobject.ChosenNode;
import com.epam.autopocreator.pageobject.Page;
import com.epam.autopocreator.settings.SavePath;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

public class ApoCreatorApp extends JFrame {

	private static final long serialVersionUID = 2803346310477075081L;

	private JButton startButton;
	private JTextField urlField;
	private JTextField pathField;
	private JLabel pathLabel;
	private JLabel urlLabel;
	private JButton changePathButton;
	private JFileChooser pathChooser;
	private Browser browser;
	private BrowserView browserView;
	
	final static Color ERROR_COLOR = Color.RED;
	
	
	public ApoCreatorApp() {
		super("Auto Page Object Creator");
		initComponents();
		
		browserView.addMouseListener(new MouseAdapter() {
			@Override
            public void mouseClicked(MouseEvent e) {
            	ChosenNode chosenNode = new ChosenNode(browser.getNodeAtPoint(e.getX(), e.getY()).getNode());
            	Page page = new Page(browser.getURL());
            	page.addWebElement(chosenNode);
            }
        });
        
		//обработчики кнопок
		
		changePathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pathChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					try {
						pathField.setText(pathChooser.getSelectedFile().getCanonicalPath());
						SavePath.getSavePath().setPath(pathChooser.getSelectedFile().getCanonicalPath());
					} catch (IOException e) {
						e.printStackTrace();
					}
				};
			}
		});
		
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				browser.loadURL(urlField.getText());
				urlField.setText(browser.getURL());
			}
		});
		
	}
	
	private void initComponents() {
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		browser = new Browser();
		browserView = new BrowserView(browser);
		pathLabel = new JLabel();
		pathField = new JTextField();
		changePathButton = new JButton("...");
		pathChooser = new JFileChooser();
		urlLabel = new JLabel();
		urlField = new JTextField();
		startButton = new JButton("Start");
		
		pathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		pathChooser.setCurrentDirectory(new File("."));
		
		pathLabel.setText("Choose result folder:");
		urlLabel.setText("Input URL:");
		
		try {
			pathField.setText(new File(".").getCanonicalPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		
		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(browserView)
						.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(pathLabel)
								.addComponent(pathField)
								.addComponent(urlLabel)
								.addComponent(urlField))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(changePathButton)
								.addComponent(startButton))))
		);
		
		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(pathLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(pathField)
						.addComponent(changePathButton))
				.addComponent(urlLabel)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(urlField)
						.addComponent(startButton))
				.addComponent(browserView)
		);
		
	}
	
	public static void main(String[] args) {
		  SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                //Turn off metal's use of bold fonts
	                UIManager.put("swing.boldMetal", Boolean.FALSE);
	                new ApoCreatorApp().setVisible(true);
	            }
	        });
		
	}
	
	
}
