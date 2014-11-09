package edu.uga.cs.cs4810;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;


public class PerspectiveProjection extends JFrame{ 

	public static void createAndShowGUI() {

		JFrame frame = new JFrame("2D Basic Transformations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.add(new PaintPanel());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0 , screenSize.width, screenSize.height);
		frame.setVisible(true);


	}

	public static void main(String[] args) {//start main
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}



class PaintPanel extends JPanel {
	private boolean clicked = false;
	private boolean clicked2 = false;
	
	PaintPanel() {
		setLayout(new BorderLayout());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(screenSize);
		add(new InputPanel(), BorderLayout.LINE_START);
		
		InputPanel.drawLines.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				UserInput.setPerspectiveData(Transformations.EyeToPerspective(UserInput.getEyeData()));
				//System.out.println(Arrays.deepToString(UserInput.getEyeData()));
				System.out.println(Arrays.deepToString(UserInput.getPerspectiveData()));
				clicked2 = true;
				repaint();
			}
		});	
		
		InputPanel.createViewport.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				UserInput.setWidth(InputPanel.viewportWidth.getText());
				UserInput.setHeight(InputPanel.viewportHeight.getText());
				UserInput.setXLocation(InputPanel.xLocation.getText());
				UserInput.setYLocation(InputPanel.yLocation.getText()); 
				//System.out.println(UserInput.getWidth() + "   " +UserInput.getHeight()+ "   " +UserInput.getXLocation()+ "   " + UserInput.getYLocation());
				clicked = true;
				repaint();
			}
		});	
		
		InputPanel.open.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JFileChooser fc = new JFileChooser();
				int returnVal = fc.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String data = fc.getSelectedFile().getAbsolutePath();
					try {
						UserInput.setWorldData(Transformations.Inputlines(data));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();

					}
				}
			}
			
		});
		
		InputPanel.setViewpoint.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				UserInput.setViewpointX(InputPanel.xPointField.getText());
				UserInput.setViewpointY(InputPanel.yPointField.getText());
				UserInput.setViewpointZ(InputPanel.zPointField.getText());
				double[][] transformationMatrix = Transformations.WorldToEye(UserInput.getWorldData(), UserInput.getViewpointX(), 
																					   UserInput.getViewpointY(), 
																					   UserInput.getViewpointZ());
				
				//double[][] eyeMatrix = Transformations.ApplyTransformations(UserInput.getWorldData(), transformationMatrix);
				UserInput.setEyeData(transformationMatrix);
				//System.out.println(Arrays.deepToString(UserInput.getEyeData()));
			}
		});

		InputPanel.setDisplay.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				UserInput.setDistance(InputPanel.distanceField.getText());
				UserInput.setSize(InputPanel.sizeField.getText());

			}
		});
		InputPanel.applyTransformations.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				double[][] translate = Transformations.BasicTranslate(Integer.parseInt(InputPanel.translateXField.getText()), 
											   						  Integer.parseInt(InputPanel.translateYField.getText()), 
											   						  Integer.parseInt(InputPanel.translateZField.getText()));
				double[][] scale = Transformations.BasicScale(Integer.parseInt(InputPanel.scaleXField.getText()), 
															  Integer.parseInt(InputPanel.scaleYField.getText()), 	
															  Integer.parseInt(InputPanel.scaleZField.getText()));
				double[][] rotateX = Transformations.BasicXRotate(Integer.parseInt(InputPanel.rotateXField.getText()));
				double[][] rotateY = Transformations.BasicYRotate(Integer.parseInt(InputPanel.rotateYField.getText()));	
				double[][] rotateZ = Transformations.BasicZRotate(Integer.parseInt(InputPanel.rotateZField.getText()));

				double[][] concat = Transformations.Concatenate(Transformations.Concatenate(translate, scale), rotateZ);
				double[][] transformation = Transformations.Concatenate(Transformations.Concatenate(concat, rotateY), rotateX);	
				
				double[][] eyeData = Transformations.ApplyTransformation(UserInput.getEyeData(), transformation);
				UserInput.setEyeData(eyeData);
				
				UserInput.setPerspectiveData(Transformations.EyeToPerspective(UserInput.getEyeData()));
				clicked2 = true;
				repaint();

			}
		});
		
	}

	
	public void paintComponent(Graphics g) {
		if(clicked == true) {
			super.paintComponent(g);
			double[][] data;
			BufferedImage viewport = Transformations.ViewportSpec(UserInput.getWidth(), UserInput.getHeight());
			
			if(clicked2==true) {
			
			viewport = Transformations.Displaypixel(UserInput.getPerspectiveData());
			}
			 
			g.drawImage(viewport, UserInput.getXLocation(), UserInput.getYLocation(), this);
		}
	}
	
} 



class InputPanel extends JPanel {

	final static Font defaultFont = new JLabel().getFont();
	static JButton open = new JButton("Open File");
	static JLabel viewportSize = new JLabel("ViewPort Dimesions");
	static JLabel widthLabel = new JLabel("Width:");
	static JLabel heightLabel = new JLabel("Height:");
	static JTextField viewportWidth = new JTextField(40);
	static JTextField viewportHeight = new JTextField(40);
	static JLabel viewportLocation = new JLabel("ViewPort Location:");
	static JLabel x = new JLabel("x:       ");
	static JLabel y = new JLabel("y:        ");
	static JTextField xLocation = new JTextField(50);
	static JTextField yLocation = new JTextField(50);
	static JButton createViewport = new JButton("Create Viewport");
	static JButton drawLines = new JButton("Draw Lines");
	static JLabel display = new JLabel("Display Characteristics");
	static JLabel distance = new JLabel("D:");
	static JLabel size = new JLabel("S:");
	static JButton setDisplay = new JButton("Set Display");
	
	static JLabel viewpoint = new JLabel("Viewpoint Coordinates");
	static JLabel viewpointX = new JLabel("x:");
	static JTextField xPointField = new JTextField(40);
	static JLabel viewpointY = new JLabel("y:");
	static JTextField yPointField = new JTextField(40);
	static JLabel viewpointZ = new JLabel("z:");
	static JTextField zPointField = new JTextField(40);
	static JButton setViewpoint = new JButton("Set Viewpoint");
	
	static JTextField distanceField = new JTextField(40);
	static JTextField sizeField = new JTextField(40);
	static JLabel translate = new JLabel("Translate:");
	static JLabel scale = new JLabel("Scale:");
	static JLabel rotate = new JLabel("Rotate:");
	static JLabel translateX = new JLabel("x:");
	static JLabel translateY = new JLabel("y:");
	static JLabel translateZ = new JLabel("z:");
	static JLabel scaleX = new JLabel("x:");
	static JLabel scaleY = new JLabel("y:");
	static JLabel scaleZ = new JLabel("z:");
	static JLabel rotateX = new JLabel("x:");
	static JLabel rotateY = new JLabel("y:");
	static JLabel rotateZ = new JLabel("z:");
	static JTextField translateXField = new JTextField(30);
	static JTextField translateYField = new JTextField(30);
	static JTextField translateZField = new JTextField(30);
	static JTextField scaleXField = new JTextField(40);
	static JTextField scaleYField = new JTextField(40);
	static JTextField scaleZField = new JTextField(40);
	static JTextField rotateXField = new JTextField(40); 
	static JTextField rotateYField = new JTextField(40); 
	static JTextField rotateZField = new JTextField(40); 
	static JButton applyTransformations = new JButton("Apply Transformations");
	private boolean clicked = false;
	private boolean clicked2 = false;
	
	InputPanel() {
		setPreferredSize(new Dimension(300, 786));
		setBackground(Color.GRAY);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(300, 150));
		inputPanel.setBackground(Color.GRAY);

		JPanel inputPanel2 = new JPanel();
		inputPanel2.setPreferredSize(new Dimension(300, 130));
		inputPanel2.setBackground(Color.GRAY);
		
		JPanel inputPanel3 = new JPanel();
		inputPanel3.setPreferredSize(new Dimension(300, 80));
		inputPanel3.setBackground(Color.GRAY);

		JPanel inputPanel4 = new JPanel();
		inputPanel4.setPreferredSize(new Dimension(300, 280));
		inputPanel4.setBackground(Color.GRAY);

		inputPanel.setAlignmentX( Component.LEFT_ALIGNMENT );
		inputPanel2.setAlignmentX( Component.LEFT_ALIGNMENT );
		inputPanel3.setAlignmentX( Component.LEFT_ALIGNMENT );
		inputPanel4.setAlignmentX( Component.LEFT_ALIGNMENT );

		this.add(inputPanel);
		this.add(inputPanel2);
		this.add(inputPanel3);
		this.add(inputPanel4);

		Font bold = new Font(defaultFont.getFontName(), Font.BOLD, defaultFont.getSize());
		viewportSize.setFont(bold);
		viewportLocation.setFont(bold);
		viewpoint.setFont(bold);
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
				"[]30[][]10[]"
				);
		inputPanel2.setLayout(mig2);

		MigLayout mig3 = new MigLayout(
				"",
				"23[80:80:80][80:80:80][80:80:80]",
				"[][]10[]30[]"
				);
		inputPanel3.setLayout(mig3);

		MigLayout mig4 = new MigLayout(
				"",
				"23[80:80:80][80:80:80][80:80:80]",
				"[]20[][]10[][]10[][]30[]"
				);

		inputPanel4.setLayout(mig4);


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

		inputPanel2.add(createViewport, "cell 0 0, width 150:150:150, gapleft 20");

		inputPanel2.add(display, "cell 0 1, gapleft 15");
		inputPanel2.add(distance, "cell 0 2, split 2");
		inputPanel2.add(distanceField);
		inputPanel2.add(size, "cell 1 2, split 2");
		inputPanel2.add(sizeField); 
		inputPanel2.add(setDisplay, "cell 0 3, gapleft 37");
		
		inputPanel3.add(viewpoint, "cell 0 0, gapleft 50");
		inputPanel3.add(viewpointX, "cell 0 1, split 2");
		inputPanel3.add(xPointField);
		inputPanel3.add(viewpointY, "cell 1 1, split 2");
		inputPanel3.add(yPointField);
		inputPanel3.add(viewpointZ, "cell 2 1, split 2");
		inputPanel3.add(zPointField);
		inputPanel3.add(setViewpoint, "cell 0 2, gapleft 60");
		
		
		
		inputPanel4.add(drawLines, "cell 0 0, width 150:150:150, gapleft 50");


		inputPanel4.add(translate, "cell 0 1");
		inputPanel4.add(translateX, "cell 0 2, split 2");
		inputPanel4.add(translateXField);
		inputPanel4.add(translateY, "cell 1 2, split 2");
		inputPanel4.add(translateYField);
		inputPanel4.add(translateZ, "cell 2 2, split 2");
		inputPanel4.add(translateZField);


		inputPanel4.add(scale, "cell 0 3");
		inputPanel4.add(scaleX, "cell 0 4, split 2");
		inputPanel4.add(scaleXField);
		inputPanel4.add(scaleY, "cell 1 4, split 2");
		inputPanel4.add(scaleYField);
		inputPanel4.add(scaleZ, "cell 2 4, split 2");
		inputPanel4.add(scaleZField);



		inputPanel4.add(rotate, "cell 0 5");
		inputPanel4.add(rotateX, "cell 0 6, split 2");
		inputPanel4.add(rotateXField);
		inputPanel4.add(rotateY, "cell 1 6, split 2");
		inputPanel4.add(rotateYField);
		inputPanel4.add(rotateZ, "cell 2 6, split 2");
		inputPanel4.add(rotateZField);
		inputPanel4.add(applyTransformations, "cell 0 7, gapleft 40");


	}


}

