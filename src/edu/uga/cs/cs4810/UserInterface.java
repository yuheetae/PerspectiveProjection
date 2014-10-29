package edu.uga.cs.cs4810;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;


public class UserInterface extends JPanel{
	
	private final static Font defaultFont = new JLabel().getFont();
	
	public static void createAndShowGUI() {

		JFrame frame = new JFrame("2D Basic Transformations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.getContentPane().setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 786));
		panel.setBackground(Color.GRAY);
		frame.getContentPane().add(panel, BorderLayout.LINE_START);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(300, 100));
		inputPanel.setBackground(Color.GRAY);
		
		JPanel inputPanel2 = new JPanel();
		inputPanel2.setPreferredSize(new Dimension(300, 311));//242
		inputPanel2.setBackground(Color.GRAY);
		
	
		JPanel inputPanel3 = new JPanel();
		inputPanel3.setPreferredSize(new Dimension(300, 300));
		inputPanel3.setBackground(Color.GRAY);
		//inputPanel.add(inputPanel2, BorderLayout.PAGE_END);
		
		inputPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
		inputPanel2.setAlignmentX( Component.LEFT_ALIGNMENT );
		inputPanel3.setAlignmentX( Component.LEFT_ALIGNMENT );
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(inputPanel);
		panel.add(inputPanel2);
		panel.add(inputPanel3);
		
		JButton open = new JButton("Open File");
		JLabel viewportSize = new JLabel("ViewPort Dimesions");
		JLabel widthLabel = new JLabel("Width:");
		JLabel heightLabel = new JLabel("Height:");
		JTextField viewportWidth = new JTextField(40);
		JTextField viewportHeight = new JTextField(40);
		JLabel viewportLocation = new JLabel("ViewPort Location:");
		JLabel x = new JLabel("x:       ");
		JLabel y = new JLabel("y:        ");
		JTextField xLocation = new JTextField(50);
		JTextField yLocation = new JTextField(50);
		JButton createViewport = new JButton("Create Viewport");
		JButton drawLines = new JButton("Draw Lines");
		JLabel display = new JLabel("Display Characteristics");
		JLabel distance = new JLabel("D:");
		JLabel size = new JLabel("S:");
		JButton setDisplay = new JButton("Set Display");
		JTextField distanceField = new JTextField(40);
		JTextField sizeField = new JTextField(40);
		JLabel translate = new JLabel("Translate:");
		JLabel scale = new JLabel("Scale:");
		JLabel rotate = new JLabel("Rotate:");
		JLabel translateX = new JLabel("x:");
		JLabel translateY = new JLabel("y:");
		JLabel scaleX = new JLabel("x:");
		JLabel scaleY = new JLabel("y:");
		JLabel rotateX = new JLabel("x:");
		JLabel rotateY = new JLabel("y:");
		JLabel rotateZ = new JLabel("z:");
		JTextField translateXField = new JTextField(30);
		JTextField translateYField = new JTextField(30);
		JTextField scaleXField = new JTextField(40);
		JTextField scaleYField = new JTextField(40);
		JTextField rotateXField = new JTextField(40); 
		JTextField rotateYField = new JTextField(40); 
		JTextField rotateZField = new JTextField(40); 
		JButton applyTransformations = new JButton("Apply Transformations");
		
		
		Font bold = new Font(defaultFont.getFontName(), Font.BOLD, defaultFont.getSize());
		viewportSize.setFont(bold);
		viewportLocation.setFont(bold);
		display.setFont(bold);
		translate.setFont(bold);
		scale.setFont(bold);
		rotate.setFont(bold);
		
		MigLayout mig = new MigLayout(
				"",
				"[]",
				"30[]25[][]"
				);
		inputPanel.setLayout(mig);
		
		
		MigLayout mig2 = new MigLayout(
				"",
				"57[80:80:80]20[80:80:80]",
				"[][]15[]30[][]15[]30[][][]"
				);
		inputPanel2.setLayout(mig2);
		
		
		MigLayout mig3 = new MigLayout(
				"",
				"[35:35:35][50:50:50][35:35:35][50:50:50][35:35:35][50:50:50]",
				"[][]30[]"
				);
		
		inputPanel3.setLayout(mig3);
		
		
		inputPanel.add(open, "cell 0 0, gapleft 85");
		
		
		inputPanel.add(viewportSize, "cell 0 1, gapleft 70");
		
		inputPanel.add(widthLabel, "cell 0 2, split 4");
		inputPanel.add(viewportWidth);
		inputPanel.add(heightLabel);
		inputPanel.add(viewportHeight);
		
		
		inputPanel.add(viewportLocation, "cell 0 3, gapleft 70");
		
		
		inputPanel.add(x, "cell 0 4, split 4");
		inputPanel.add(xLocation);
		inputPanel.add(y);
		inputPanel.add(yLocation);
		
		inputPanel2.add(createViewport, "cell 0 1, width 150:150:150, gapleft 20");
		
		inputPanel2.add(drawLines, "cell 0 2, width 150:150:150, gapleft 20");
		
		inputPanel2.add(display, "cell 0 3");
		inputPanel2.add(distance, "cell 0 4, split 2");
		inputPanel2.add(distanceField);
		inputPanel2.add(size, "cell 1 4, split 2");
		inputPanel2.add(sizeField); 
		inputPanel2.add(setDisplay, "cell 0 5, gapleft 37");
		
		
		inputPanel2.add(translate, "cell 0 6");
		inputPanel2.add(translateX, "cell 0 7, split 2");
		inputPanel2.add(translateXField);
		inputPanel2.add(translateY, "cell 1 7, split 2");
		inputPanel2.add(translateYField);
		
		
		inputPanel2.add(scale, "cell 0 8");
		inputPanel2.add(scaleX, "cell 0 9, split 2");
		inputPanel2.add(scaleXField);
		inputPanel2.add(scaleY, "cell 1 9, split 2");
		inputPanel2.add(scaleYField);
		
		
		
		inputPanel3.add(rotate, "cell 0 0");
		inputPanel3.add(rotateX, "cell 0 1");
		inputPanel3.add(rotateXField, "cell 1 1");
		inputPanel3.add(rotateY, "cell 2 1");
		inputPanel3.add(rotateYField, "cell 3 1");
		inputPanel3.add(rotateZ, "cell 4 1");
		inputPanel3.add(rotateZField, "cell 5 1");
		inputPanel3.add(applyTransformations, "cell 0 2, gapleft 40");
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0 , screenSize.width, screenSize.height);
		frame.getContentPane().add(new UserInterface());
		frame.setVisible(true);

	}
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			} // run
		});

	} // main

}
