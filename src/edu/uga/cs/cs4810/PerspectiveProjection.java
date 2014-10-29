package edu.uga.cs.cs4810;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;

public class PerspectiveProjection {
	public static void createAndShowGUI() {
		JFrame frame = new JFrame("2D Basic Transformations");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		InterfaceComponents.addComponent(frame.getContentPane());
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0 , screenSize.width, screenSize.height);
		frame.setVisible(true);
	}
	
	 public static void main(String[] args) {
	        //Schedule a job for the event-dispatching thread:
	        //creating and showing this application's GUI.
	        javax.swing.SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createAndShowGUI();
	            }
	        });
	    }
}
