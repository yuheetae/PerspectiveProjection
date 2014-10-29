package edu.uga.cs.cs4810;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;


public class InterfaceComponents extends JPanel{
	
	private final static Font defaultFont = new JLabel().getFont();
	private static JButton open = new JButton("Open File");
	private static JLabel viewportSize = new JLabel("ViewPort Dimesions");
	private static JLabel widthLabel = new JLabel("Width:");
	private static JLabel heightLabel = new JLabel("Height:");
	private static JTextField viewportWidth = new JTextField(40);
	private static JTextField viewportHeight = new JTextField(40);
	private static JLabel viewportLocation = new JLabel("ViewPort Location:");
	private static JLabel x = new JLabel("x:       ");
	private static JLabel y = new JLabel("y:        ");
	private static JTextField xLocation = new JTextField(50);
	private static JTextField yLocation = new JTextField(50);
	private static JButton createViewport = new JButton("Create Viewport");
	private static JButton drawLines = new JButton("Draw Lines");
	private static JLabel display = new JLabel("Display Characteristics");
	private static JLabel distance = new JLabel("D:");
	private static JLabel size = new JLabel("S:");
	private static JButton setDisplay = new JButton("Set Display");
	private static JTextField distanceField = new JTextField(40);
	private static JTextField sizeField = new JTextField(40);
	private static JLabel translate = new JLabel("Translate:");
	private static JLabel scale = new JLabel("Scale:");
	private static JLabel rotate = new JLabel("Rotate:");
	private static JLabel translateX = new JLabel("x:");
	private static JLabel translateY = new JLabel("y:");
	private static JLabel translateZ = new JLabel("z:");
	private static JLabel scaleX = new JLabel("x:");
	private static JLabel scaleY = new JLabel("y:");
	private static JLabel scaleZ = new JLabel("z:");
	private static JLabel rotateX = new JLabel("x:");
	private static JLabel rotateY = new JLabel("y:");
	private static JLabel rotateZ = new JLabel("z:");
	private static JTextField translateXField = new JTextField(30);
	private static JTextField translateYField = new JTextField(30);
	private static JTextField translateZField = new JTextField(30);
	private static JTextField scaleXField = new JTextField(40);
	private static JTextField scaleYField = new JTextField(40);
	private static JTextField scaleZField = new JTextField(40);
	private static JTextField rotateXField = new JTextField(40); 
	private static JTextField rotateYField = new JTextField(40); 
	private static JTextField rotateZField = new JTextField(40); 
	private static JButton applyTransformations = new JButton("Apply Transformations");
	
	//public static void createAndShowGUI() {
	public static void addComponent(Container pane) {

		/*
		JFrame frame = new JFrame("2D Basic Transformations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		*/
		
		pane.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 786));
		panel.setBackground(Color.GRAY);
		//frame.getContentPane().add(panel, BorderLayout.LINE_START);
		pane.add(panel, BorderLayout.LINE_START);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(300, 170));
		inputPanel.setBackground(Color.GRAY);
		
		JPanel inputPanel2 = new JPanel();
		inputPanel2.setPreferredSize(new Dimension(300, 200));
		inputPanel2.setBackground(Color.GRAY);
	
		JPanel inputPanel3 = new JPanel();
		inputPanel3.setPreferredSize(new Dimension(300, 300));
		inputPanel3.setBackground(Color.GRAY);
		
		inputPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
		inputPanel2.setAlignmentX( Component.LEFT_ALIGNMENT );
		inputPanel3.setAlignmentX( Component.LEFT_ALIGNMENT );
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(inputPanel);
		panel.add(inputPanel2);
		panel.add(inputPanel3);		
		
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
				"57[80:80:80]20[80:80:80]0",
				"[][]15[]30[][]15[]30[][][]"
				);
		inputPanel2.setLayout(mig2);
		
		
		MigLayout mig3 = new MigLayout(
				"",
				"23[80:80:80][80:80:80][80:80:80]",
				"[][]10[][]10[][]20[]"
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
		
		
		inputPanel3.add(translate, "cell 0 0");
		inputPanel3.add(translateX, "cell 0 1, split 2");
		inputPanel3.add(translateXField);
		inputPanel3.add(translateY, "cell 1 1, split 2");
		inputPanel3.add(translateYField);
		inputPanel3.add(translateZ, "cell 2 1, split 2");
		inputPanel3.add(translateZField);
		
		
		inputPanel3.add(scale, "cell 0 2");
		inputPanel3.add(scaleX, "cell 0 3, split 2");
		inputPanel3.add(scaleXField);
		inputPanel3.add(scaleY, "cell 1 3, split 2");
		inputPanel3.add(scaleYField);
		inputPanel3.add(scaleZ, "cell 2 3, split 2");
		inputPanel3.add(scaleZField);
		
		
		
		inputPanel3.add(rotate, "cell 0 4");
		inputPanel3.add(rotateX, "cell 0 5, split 2");
		inputPanel3.add(rotateXField);
		inputPanel3.add(rotateY, "cell 1 5, split 2");
		inputPanel3.add(rotateYField);
		inputPanel3.add(rotateZ, "cell 2 5, split 2");
		inputPanel3.add(rotateZField);
		inputPanel3.add(applyTransformations, "cell 0 7, gapleft 40");
		
		setDisplay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				UserInput.setDistance(distanceField.getText());
				UserInput.setDistance(sizeField.getText());
				System.out.println("dnfjsdnfkds");
			}
		});
		
	}
	


	
	
}
