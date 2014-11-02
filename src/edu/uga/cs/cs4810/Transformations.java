package edu.uga.cs.cs4810;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Transformations {
	
	private static int lineNumber;
	private static boolean accept = false;
	
	public static double[][] BasicTranslate(int Tx, int Ty, int Tz) {
		double[][] matrix = {{1,  0,  0,  0},
							 {0,  1,  0,  0},
							 {0,  0,  1,  0},
							 {Tx, Ty, Tz, 0}};
		return matrix;
	}
	
	public static double[][] BasicScale(double Sx, double Sy, double Sz) {
		if(Sx == 0) Sx=1;
		if(Sy == 0) Sy=1;
		if(Sz == 0)	Sz=1;
		double[][] matrix = {{Sx, 0,  0,  0}, 
							 {0,  Sy, 0,  0},
							 {0,  0,  Sz, 0},
							 {0,  0,  0,  1}}; 
		return matrix;
	}
	
	public static double[][] BasicZRotate(double angle) {
		double theta = Math.toRadians(angle);
		double cos = Math.cos(theta);
		double sin =  Math.sin(theta);
		double[][] matrix = {{ cos,  sin, 0, 0}, 
							 {-sin,  cos, 0, 0}, 
							 {  0,    0,  1, 0},
						   	 {  0,    0,  0, 1}};
		return matrix;
	}
	
	public static double[][] BasicYRotate(double angle) {
		double theta = Math.toRadians(angle);
		double cos = Math.cos(theta);
		double sin =  Math.sin(theta);
		double[][] matrix = {{cos, 0, -sin, 0}, 
							 { 0,  1,   0,  0}, 
							 {sin, 0,  cos, 0},
						   	 { 0,  0,   0,  1}};
		return matrix;
	}
	
	public static double[][] BasicXRotate(double angle) {
		double theta = Math.toRadians(angle);
		double cos = Math.cos(theta);
		double sin =  Math.sin(theta);
		double[][] matrix = {{1,   0,   0,  0}, 
							 {0,  cos, sin, 0}, 
							 {0, -sin, cos, 0},
						   	 {0,   0,   0,  1}};
		return matrix;
	}
	
	public static double[][] ClippingMatrix(double D, double S) {
		double x = D/S;
		double[][] N = {{x, 0, 0, 0}, 
				 		{0, x, 0, 0}, 
				 		{0, 0, 1, 0},
				 		{0, 0, 0, 1}};
		return N;
	}
	
	public static double[][] Concatenate(double[][] matrix1, double[][] matrix2) {
		double[][] product = new double[4][4];

			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 4; j++) {
					for(int k = 0; k < 4; k++) {
						product[i][j] += matrix1[i][k]*matrix2[k][j]; 
					}
						
				}
			}
			return product;
		}
	
	public static BufferedImage ViewportSpec(int width, int height) {
		Color black = new Color(0,0,0);
		int rgb = black.getRGB();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int i=0; i<width; i++) {
			for(int j=0; j<height; j++) {
				image.setRGB(i, j, rgb);			
			}
		}		
		return image;
	}
	
	private static int bitCode(int x, int y) {
		int code = 0;   
		if(x<0) code |= 1;
		else if(x>UserInput.getWidth()) code |= 2;
		if(y<0) code |= 8;
		else if(y>UserInput.getHeight()) code |= 4;
		return code;
	}
	
	private static double[] Clipping(int x1, int y1, int x2, int y2) {
		double[] newValues = new double[4];
		boolean visible = false;
		boolean done = false;
		int x = 0, y = 0;
		int code1, code2, codeOut;

		code1 = bitCode(x1, y1);
		code2 = bitCode(x2, y2);

		do{
			if((code1 | code2) == 0) {
				visible = true;
				done = true;
			}
			else if((code1 & code2) != 0) {
				done = true;
			}
			else {
				//First Step
				if(code1 != 0) {
					codeOut = code1;
				}
				else {
					codeOut = code2;
				}

				//Second Step
				if((codeOut & 8) != 0) {x = x1 + (x2 - x1)*(0 - y1)/(y2 - y1); y = 0;}
				else if((codeOut & 4) != 0) { x = x1 + (x2 - x1)*(UserInput.getHeight() - y1)/(y2 - y1); y = UserInput.getHeight(); }
				else if((codeOut & 2) != 0) {y = y1 + (y2 - y1)*(UserInput.getWidth() - x1)/(x2 - x1); x = UserInput.getWidth();}
				else if((codeOut & 1) != 0) {y = y1 + (y2 - y1)*(0 - x1)/(x2 - x1); x = 0;}

				//Third Step
				if(codeOut == code1) {x1 = x; y1 = y; code1 = bitCode(x1, y1);}
				else { x2 = x; y2 = y; code2 = bitCode(x2, y2); }
			}
		}while(done==false);
		
		if(visible == true) {
			setTrue();
			newValues[0] = x1;
			newValues[1] = y1;
			newValues[2] = x2;
			newValues[3] = y2;
		}
		else{
			setFalse();
		}
		
		return newValues;
	}
	
	public static double[][] Inputlines(String data) throws FileNotFoundException {
		
		int i=0;
		int j=0;

		File file = new File(data);
		int number = 0;
		Scanner scanner = new Scanner(file);
		while(scanner.hasNextLine())  {
			number++; 
			scanner.nextLine();
		}
		
		double[][] datalines = new double[number][6];
		Scanner scanner2 = new Scanner(file);
		while(scanner2.hasNextInt()) {
				datalines[i][j] = scanner2.nextInt();
				//if(i==(number)) i=0;
				if(j==5) {j=-1; i++;};
				j++;
		}
		setLineNumber(number);
		return datalines;
	}
	
public static double[][] PerspectiveData(double[][] datalines) { 
	double[][] perspectiveData = new double[getLineNumber()][4];
	int Vsx = UserInput.getWidth()/2;
	int Vsy = UserInput.getHeight()/2;
	int Vcx = UserInput.getWidth()/2;
	int Vcy = UserInput.getHeight()/2;
	double D = UserInput.getDistance();
	double S = UserInput.getSize();
	
	for(int i = 0; i<getLineNumber(); i++) {
		perspectiveData[i][0] = ((D*datalines[i][0])/(S*datalines[i][2]))*Vsx+Vcx;
		perspectiveData[i][1] = ((D*datalines[i][1])/(S*datalines[i][2]))*Vsx+Vcx;
		perspectiveData[i][2] = ((D*datalines[i][3])/(S*datalines[i][5]))*Vsx+Vcx;
		perspectiveData[i][3] = ((D*datalines[i][4])/(S*datalines[i][5]))*Vsx+Vcx; 
	}
	
	return perspectiveData;
}
	
public static BufferedImage Displaypixel(double[][] datalines) {
		
		BufferedImage image = new BufferedImage(0, 0, 0);
		Color green = new Color(128, 255, 0); 
		int rgb = green.getRGB();
		int x1, y1, x2, y2;
		int dx, dy, E, ystep;
		
		//System.out.println("NUMBER OF LINES:   " + num );
		for(int i=0; i<getLineNumber(); i++) {
			
			x1 = (int) datalines[i][0];
			y1 = (int) datalines[i][1];
			x2 = (int) datalines[i][2];
			y2 = (int) datalines[i][3];


			boolean swap = Math.abs(y2-y1) > Math.abs(x2-x1);
			if(swap) { //if slope is greater than or equal to 1
				int temp = x1;
				x1 = y1;
				y1 = temp;
				temp = x2;
				x2 = y2;
				y2 = temp;
			}
			if(x1>x2) { //switches starting point and end point
				//swap x1 and x2, swap y1 and y2
				int temp = x1;
				x1 = x2;
				x2 = temp;
				temp = y1;
				y1 = y2;
				y2 = temp;
			}
			dx = x2 - x1;
			dy = Math.abs(y2 - y1);
			E = dx/2;
			int y = y1;

			if(y1<y2) { //if positive slope
				ystep = 1;
			}
			else { //if negative slope
				ystep = -1;
			}

			for(int x = x1; x<x2;x++) {

				if(swap) {
					image.setRGB(y,x,rgb);
				}
				else {
					image.setRGB(x,y,rgb);
				}
				E = E-dy;
				if(E<0) {
					y = y+ystep;
					E = E+dx;
				}

			}
		}
		return image;
}

public static void setLineNumber(int number) {
	lineNumber = number;
}

public static int getLineNumber() {
	return lineNumber;
}

public static void setTrue() {
	accept = true;
}

public static void setFalse() {
	accept = false;
}

public static boolean getAccept() {
	return accept;
}
	
	
	
	

}//End of class
