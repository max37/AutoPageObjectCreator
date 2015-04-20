package com.epam.autopocreator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.epam.autopocreator.browser.BrowserInteractor;

public class ApoCreatorApp extends JFrame {

	private static final long serialVersionUID = 2803346310477075081L;

	private JButton startButton;
	private JTextField urlField;
	private JTextField pathField;
	private JLabel pathLabel;
	private JLabel urlLabel;
	private JButton changePathButton;
	
	final static Color ERROR_COLOR = Color.RED;
	
	
	public ApoCreatorApp() {
		super("Auto Page Object Creator");
		setBounds(100, 100, 400, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pathField = new JTextField();
		urlField = new JTextField();
		startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//BrowserInteractor interactor = new BrowserInteractor();
				
			}
		});
		
		JPanel buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.add(startButton);
		add(buttonsPanel, BorderLayout.SOUTH);
		
		add(urlField, BorderLayout.NORTH);
		
		
	}
	
	public static void main(String[] args) {
		ApoCreatorApp app = new ApoCreatorApp();
		app .setVisible(true);
	}
	
	
}
