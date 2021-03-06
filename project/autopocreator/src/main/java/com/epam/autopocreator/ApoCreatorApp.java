package com.epam.autopocreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

import com.epam.autopocreator.navigation.SingleBrowser;
import com.epam.autopocreator.pageobject.ChosenNode;
import com.epam.autopocreator.pageobject.Page;
import com.epam.autopocreator.settings.SavePath;
//удалить, заменить любой другой компонент браузера, например djproject
import com.teamdev.jxbrowser.chromium.dom.DOMNodeAtPoint;
import com.teamdev.jxbrowser.chromium.dom.internal.Node;
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
	//объект браузера, заменить
	private BrowserView browserView;
	
	public ApoCreatorApp() {
		super("Auto Page Object Creator");
		initComponents();
		// обработчик действий мыши
		browserView.addMouseListener(new MouseAdapter() {
			// мышь нажата - браузер получает ноду под курсором, передает ее в объект ChosenNode, создается объект страницы и туда добавляется ChosenNode
			@Override
            public void mousePressed(MouseEvent e) {
				DOMNodeAtPoint node = SingleBrowser.getSingleBrowser().getBrowser().getNodeAtPoint(e.getX(), e.getY()); //заменить на javascript document.elementFromPoint(x, y)
            	ChosenNode chosenNode = new ChosenNode((Node) node.getNode());
            	Page page = new Page(SingleBrowser.getSingleBrowser().getBrowser().getURL());
            	page.addWebElement(chosenNode);	
            }
			// мышь отпущена - обновляет URL в строке. в jxbrowser нет подходящих событий. если будет в другом заменить на что-нибудь вроде pageLoaded
			@Override
            public void mouseReleased(MouseEvent e) {
				urlField.setText(SingleBrowser.getSingleBrowser().getBrowser().getURL());
            }
        });
		
		//обработчики кнопок
		
		changePathButton.addActionListener(new ActionListener() {
			// Кнопка "..."
			// Открывает диалог для выбора каталога сохранения
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
		
		// Кнопка "Start", загружает в браузере страницу из строки URL
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SingleBrowser.getSingleBrowser().getBrowser().loadURL(urlField.getText());
				urlField.setText(SingleBrowser.getSingleBrowser().getBrowser().getURL());
			}
		});
		
	}
	
	/**
	 * Организует внешний вид приложения
	 */
	private void initComponents() {
		setBounds(100, 100, 800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		browserView = new BrowserView(SingleBrowser.getSingleBrowser().getBrowser());
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
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
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
	                // По умолчанию стоят жирные шрифты, это их отключает
	                UIManager.put("swing.boldMetal", Boolean.FALSE);
	                new ApoCreatorApp().setVisible(true);
	            }
	        });
		
	}
	
	
}
