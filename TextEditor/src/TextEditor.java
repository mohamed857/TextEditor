import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener {

	JTextArea textArea;
	JScrollPane scrollPane;
	JSpinner fontSizeSpinner;
	JLabel fontLabel;
	JButton fontColorButton;
	JComboBox fontBox;
	
	//----- menu -------
	JMenuBar menuBar;
	JMenu fileMenu;
	JMenuItem openItem;
	JMenuItem saveItem;
	JMenuItem exitItem;
	//----- menu -------
	
	//----- clock -------
	Calendar calendre;
	SimpleDateFormat timeFormate;
	SimpleDateFormat dayFormate;
	SimpleDateFormat dateFormate;	
	JLabel timeLabel;
	JLabel dayLabel;
	JLabel dateLabel;
	String time;
	String day;
	String date;
	//----- clock -------
	TextEditor(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Ramadan text editor");
		this.setSize(500, 580);
		this.setLayout(new FlowLayout());
		this.setLocationRelativeTo(null); // center of screen
		
		textArea= new JTextArea(); 	
		textArea. setOpaque(false);	
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		textArea.setForeground(Color.black);
		
		scrollPane =new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(450,450));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		fontLabel = new JLabel("Font: ");
		
		fontSizeSpinner = new  JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(), Font.PLAIN, (int) fontSizeSpinner.getValue()));
				
			}
		});
		
		fontColorButton = new JButton("Color");
		fontColorButton.addActionListener(this);
		
		String [] font = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		
		fontBox = new JComboBox(font);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
		// -------- menuBar --------
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open");
		saveItem = new JMenuItem("Save");
		exitItem = new JMenuItem("Exit");
		
		openItem.addActionListener(this);
		saveItem.addActionListener(this);
		exitItem.addActionListener(this);
		
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(exitItem);
		menuBar.add(fileMenu);
		
		// -------- menuBar --------
		
		// -------- Clock --------
		timeFormate = new SimpleDateFormat("hh:mm:ss a");
		dayFormate = new SimpleDateFormat("EEEE");
		dateFormate = new SimpleDateFormat("MMMM dd, yyyy");
	
		timeLabel = new JLabel();
		dayLabel = new JLabel();
		dateLabel = new JLabel();
		
		timeLabel.setFont(new Font("Verdana",Font.PLAIN,20));
		dayLabel.setFont(new Font("Verdana",Font.PLAIN,20));
		dateLabel.setFont(new Font("Verdana",Font.PLAIN,20));
		
//		day = timeFormate.format(calendre.getInstance().g)
		// -------- Clock --------
		
		this.setJMenuBar(menuBar);
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(fontColorButton);
		this.add(fontBox);
		
		this.add(scrollPane);
		this.add(timeLabel);
		this.add(dayLabel);
		this.add(dateLabel);
		this.setVisible(true);
		setTime();
	}
	public void setTime() {
		while(true) {
		time = timeFormate.format(calendre.getInstance().getTime());
		timeLabel.setText(time+"     ");
		day = dayFormate.format(calendre.getInstance().getTime());
		dayLabel.setText(day+"     ");
		date = dateFormate.format(calendre.getInstance().getTime());
		dateLabel.setText(date);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==fontColorButton) {
			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null,"Choose a color", Color.black);
			textArea.setForeground(color);
		}
		if (e.getSource()==fontBox) {
			textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
		}
		if (e.getSource()==openItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			FileNameExtensionFilter filter = new FileNameExtensionFilter("text file","txt");
			fileChooser.setFileFilter(filter);
			
			int respone = fileChooser.showOpenDialog(null);
			
			if(respone == fileChooser.APPROVE_OPTION) {
				textArea.setText("");
				File file= new File(fileChooser.getSelectedFile().getAbsolutePath());
				Scanner fileIn = null;
				try {
					fileIn = new Scanner(file);
					if(file.isFile()) {
						while(fileIn.hasNextLine()) {
							String line = fileIn.nextLine()+"\n";
							textArea.append(line);
						}
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileIn.close();
				}
			}
		}
		if (e.getSource()==saveItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null);
			
			if(response == fileChooser.APPROVE_OPTION) {
				File file;
				PrintWriter fileOut = null;
				
				file=new File(fileChooser.getSelectedFile().getAbsolutePath());
				
				try {
					fileOut = new PrintWriter(file);
					fileOut.println(textArea.getText());
				}
				catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					fileOut.close();
				}
			}
			
		}
		if (e.getSource()==exitItem) {
			System.exit(0);
		}
		
	}

}
