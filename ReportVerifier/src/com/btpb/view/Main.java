package com.btpb.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import com.btpb.service.LogFileHandler;
import com.btpb.service.ReportValidator;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2452718314801424205L;


	public Main() {
		
		
		super("BLC Report Analyser 1.0.2");
		setBackground(Color.decode("#E5E7E9"));
		
		ReportValidator reportValidator = new ReportValidator();
		setSize(350, 256);
		ImageIcon earth = new ImageIcon("resources/BLC_Report_Analyser.png");
		setIconImage(earth.getImage());
		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());
		content.setBackground(Color.decode("#edd9c0"));
		content.setOpaque(false);

		JLabel label1 = new JLabel("Path of Template File:");
		JLabel space = new JLabel(" ");
		JLabel label2 = new JLabel("Path of BLC Log File:");
		JTextField templatePath = new JTextField(15);
		JTextField logPath = new JTextField(15);
		LogFileHandler.jb = new JProgressBar(0,100);
		LogFileHandler.jb.setBounds(40,40,160,30);         
		LogFileHandler.jb.setValue(0);    
		LogFileHandler.jb.setStringPainted(true);
		LogFileHandler.jb.setVisible(false);

		JButton view1 = new JButton("Edit");
		view1.setBackground(Color.decode("#a8b6bf"));
		view1.setForeground(Color.decode("#e9ece5"));
		view1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Runtime r = Runtime.getRuntime();
				try {
					r.exec("cmd.exe /c notepad "+templatePath.getText());
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
		JButton open1 = new JButton("...");
		open1.setBackground(Color.decode("#a8b6bf"));
		open1.setForeground(Color.decode("#e9ece5"));
		open1.setMinimumSize(getMinimumSize());
		open1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();

				int rVal = c.showOpenDialog(Main.this);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					templatePath.setText(c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName());
				}
				if (rVal == JFileChooser.CANCEL_OPTION) {
					templatePath.setText("You pressed cancel");

				}

			}
		});

		JButton open2 = new JButton("...");
		open2.setForeground(Color.decode("#e9ece5"));
		open2.setBackground(Color.decode("#a8b6bf"));
		open2.setMinimumSize(getMinimumSize());
		open2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();

				int rVal = c.showOpenDialog(Main.this);
				if (rVal == JFileChooser.APPROVE_OPTION) {
					logPath.setText(c.getCurrentDirectory().toString() + "\\" + c.getSelectedFile().getName());
				}
				if (rVal == JFileChooser.CANCEL_OPTION) {
					logPath.setText("You pressed cancel");

				}

			}
		});

		label1.setForeground(Color.BLACK);


		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		content.add(label1,gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 40;
		content.add(templatePath,gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 40;
		gbc.gridy = 1;
		content.add(open1,gbc);
		gbc.gridx = 42;
		gbc.gridy = 1;
		content.add(view1,gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 2;
		content.add(label2,gbc);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 40;
		content.add(logPath,gbc);
		gbc.gridwidth = 1;
		gbc.gridx = 40;
		gbc.gridy = 3;
		content.add(open2,gbc);
		gbc.gridx = 40;
		gbc.gridy = 4;
		content.add(space,gbc);		
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 41;
		JButton run = new JButton("Start Analysis");
		run.setBackground(Color.decode("#a8b6bf"));
		run.setForeground(Color.decode("#e9ece5"));
		run.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				
			    Runnable runner = new Runnable()
			    {
			        public void run() {
			        
						try {
							run.setText("Analysing..");
							LogFileHandler.jb.setVisible(true);
							LogFileHandler.jb.setValue(0);
							
							String templateFilePath = templatePath.getText();
							String logFilePath = logPath.getText();
							String logOutputPath = logFilePath.replace(".htm", "_analysis_report.htm");
							ArrayList<String> lines = LogFileHandler.readFile(logFilePath);
							FileWriter fw = new FileWriter(new File(logOutputPath));
							fw.write(String.join("\r\n", lines));
							fw.close();
							
							
							reportValidator.validateExtAdvReports(templateFilePath, logOutputPath);
							reportValidator.validatePerAdvReports(templateFilePath, logOutputPath);
							LogFileHandler.jb.setValue(99);
							run.setText("Opening Report..");
							try{Thread.sleep(2000);}catch(Exception e){}
							LogFileHandler.jb.setValue(100);
							Runtime r = Runtime.getRuntime();
							r.exec("cmd.exe /c "+logOutputPath);
							run.setText("Start Analysis");
							LogFileHandler.jb.setVisible(false);
							
						} catch (IOException e) {

							e.printStackTrace();
						}
			        }
			    };
			    Thread t = new Thread(runner, "Code Executer");
			    t.start();
				
			}
			
		});
		content.add(run,gbc);
		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 41;
		content.add(LogFileHandler.jb,gbc);

		setLayout(new GridBagLayout());
		add(content);
		((JPanel) getContentPane()).setOpaque(false);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}


	public static void main(String[] args) {
		new Main();
	}

}
