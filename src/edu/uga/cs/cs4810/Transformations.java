package edu.uga.cs.cs4810;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;


public class Transformations {
	
	private static int lineNumber;
	private static boolean accept = false;
	
////Transformations/////////////
	public static double[][] BasicTranslate(double Tx, double Ty, double Tz) {
		double[][] matrix = {{1,  0,  0,  0},
							 {0,  1,  0,  0},
							 {0,  0,  1,  0},
							 {Tx, Ty, Tz, 1}};
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
		double cos = Math.floor(Math.cos(theta)*100000)/100000;
		double sin =  Math.floor(Math.sin(theta)*100000)/100000;
		double[][] matrix = {{ cos,  sin, 0, 0}, 
							 {-sin,  cos, 0, 0}, 
							 {  0,    0,  1, 0},
						   	 {  0,    0,  0, 1}};
		return matrix;
	}
	
	public static double[][] BasicYRotate(double angle) {
		double theta = Math.toRadians(angle);
		double cos = Math.floor(Math.cos(theta)*100000)/100000;
		double sin =  Math.floor(Math.sin(theta)*100000)/100000;
		double[][] matrix = {{cos, 0, -sin, 0}, 
							 { 0,  1,   0,  0}, 
							 {sin, 0,  cos, 0},
							 { 0,  0,   0,  1}};
		return matrix;
	}

	public static double[][] BasicXRotate(double angle) {
		double theta = Math.toRadians(angle);
		double cos = Math.floor(Math.cos(theta)*100000)/100000;
		double sin =  Math.floor(Math.sin(theta)*100000)/100000;
		double[][] matrix = {{1,   0,   0,  0}, 
							 {0,  cos, sin, 0}, 
							 {0, -sin, cos, 0},
							 {0,   0,   0,  1}};
		return matrix;
	}

	//////Concatenate Two Matrices
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
	
	public static double[][] ApplyTransformation(double[][] eyeData, double[][] transformMatrix) {
		
		double[][] result = transformMatrix;
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				result[i][j] = Math.floor(result[i][j]*100000)/100000;
			}
		}

		double[][]newEyeData = new double[getLineNumber()][6];
		for(int i = 0; i<getLineNumber(); i++) {
			newEyeData[i][0] = eyeData[i][0]*result[0][0] + eyeData[i][1]*result[1][0] + eyeData[i][2]*result[2][0] + result[3][0];
			newEyeData[i][1] = eyeData[i][0]*result[0][1] + eyeData[i][1]*result[1][1] + eyeData[i][2]*result[2][1] + result[3][1];
			newEyeData[i][2] = eyeData[i][0]*result[0][2] + eyeData[i][1]*result[1][2] + eyeData[i][2]*result[2][2] + result[3][2];
			newEyeData[i][3] = eyeData[i][3]*result[0][0] + eyeData[i][4]*result[1][0] + eyeData[i][5]*result[2][0] + result[3][0];
			newEyeData[i][4] = eyeData[i][3]*result[0][1] + eyeData[i][4]*result[1][1] + eyeData[i][5]*result[2][1] + result[3][1];
			newEyeData[i][5] = eyeData[i][3]*result[0][2] + eyeData[i][4]*result[1][2] + eyeData[i][5]*result[2][2] + result[3][2];
		}
		//System.out.println(Arrays.deepToString(eyeData));
		return newEyeData;
	}

	//Coordinate Conversions
	public static double[][] WorldToEye(double[][] worldData, double x, double y, double z) {
		double[][] t1 = BasicTranslate(-x, -y, -z);
		double[][] t2 = BasicXRotate(-90);
		double cos = y/Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));
		double sin = x/Math.sqrt(Math.pow(y, 2) + Math.pow(x, 2));
		double[][] t3 = {{-cos, 0,  sin, 0},
						 {  0,  1,   0,  0},	
						 {-sin, 0, -cos, 0},
						 {  0,  0,   0,  1}};
		double cos2 = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2))/Math.sqrt(Math.pow(z, 2) + Math.pow(x, 2) + Math.pow(y, 2));
		double sin2 = z/Math.sqrt(Math.pow(z, 2) + Math.pow(x, 2) + Math.pow(y, 2));
		double[][] t4 = {{1,   0,    0,   0},
				 		 {0,  cos2, sin2, 0},	
				 		 {0, -sin2, cos2, 0},
				 		 {0,   0,    0,   1}};
		
		double[][] t5 = {{1,  0,  0,  0},
						 {0,  1,  0,  0},
						 {0,  0, -1,  0},
						 {0,  0,  0,  1}};
		
		double[][] result = Concatenate(Concatenate(Concatenate(Concatenate(t1, t2), t3), t4), t5);
		
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				result[i][j] = Math.floor(result[i][j]*100000)/100000;
			}
		}

		/*
		System.out.println("T1:\n" + Arrays.deepToString(t1) + "\nT2:\n" + Arrays.deepToString(t2)+ "\nT3:\n" + Arrays.deepToString(t3)
				+ "\nT4:\n" + Arrays.deepToString(t4)+ "\nT5:\n" + Arrays.deepToString(t5));
		
		System.out.println("T1*T2:\n" + Arrays.deepToString(Concatenate(t1, t2)) + "\nT1*T2 * T3:\n" + Arrays.deepToString(Concatenate(Concatenate(t1, t2), t3))
				+ "\nT1*T2*T3 * T4:\n" + Arrays.deepToString(Concatenate(Concatenate(Concatenate(t1, t2), t3), t4)) + "\nT1*T2*T3*T4 * T5:\n" + Arrays.deepToString(result));
		*/
		
		double[][]eyeData = new double[getLineNumber()][6];
		for(int i = 0; i<getLineNumber(); i++) {
			eyeData[i][0] = worldData[i][0]*result[0][0] + worldData[i][1]*result[1][0] + worldData[i][2]*result[2][0] + result[3][0];
			eyeData[i][1] = worldData[i][0]*result[0][1] + worldData[i][1]*result[1][1] + worldData[i][2]*result[2][1] + result[3][1];
			eyeData[i][2] = worldData[i][0]*result[0][2] + worldData[i][1]*result[1][2] + worldData[i][2]*result[2][2] + result[3][2];
			eyeData[i][3] = worldData[i][3]*result[0][0] + worldData[i][4]*result[1][0] + worldData[i][5]*result[2][0] + result[3][0];
			eyeData[i][4] = worldData[i][3]*result[0][1] + worldData[i][4]*result[1][1] + worldData[i][5]*result[2][1] + result[3][1];
			eyeData[i][5] = worldData[i][3]*result[0][2] + worldData[i][4]*result[1][2] + worldData[i][5]*result[2][2] + result[3][2];
		}
		//System.out.println(Arrays.deepToString(eyeData));
		return eyeData;
	}
	
	public static double[][] EyeToPerspective(double[][] eyeData) {
		double[][] perspectiveData = new double[getLineNumber()][4];
		double D = UserInput.getDistance();
		double S = UserInput.getSize();
		double Vsx = UserInput.getWidth()/2;
		double Vsy = UserInput.getHeight()/2;
		double Vcx = UserInput.getWidth()/2;
		double Vcy = UserInput.getHeight()/2;
		for(int i = 0; i<getLineNumber(); i++) {
			perspectiveData[i][0] = (D*eyeData[i][0])/(S*eyeData[i][2])*Vsx+Vcx;
			perspectiveData[i][1] = (D*eyeData[i][1])/(S*eyeData[i][2])*Vcy+Vcy;
			perspectiveData[i][2] = (D*eyeData[i][3])/(S*eyeData[i][5])*Vsx+Vcx;
			perspectiveData[i][3] = (D*eyeData[i][4])/(S*eyeData[i][5])*Vcy+Vcy;
		}
		//System.out.println(Arrays.deepToString(perspectiveData));
		return perspectiveData;
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
		else if(y>(UserInput.getHeight()-1)) code |= 4;
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
				else if((codeOut & 4) != 0) { x = x1 + (x2 - x1)*(UserInput.getHeight() - y1)/(y2 - y1); y = (UserInput.getHeight()-1); }
				else if((codeOut & 2) != 0) {y = y1 + (y2 - y1)*(UserInput.getWidth() - x1)/(x2 - x1); x = (UserInput.getWidth()-1);}
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

	
public static BufferedImage Displaypixel(double[][] datalines) {
	
		//PerspectiveData(datalines);
		double[][] array = UserInput.getPerspectiveData();
		double[] clippingArray;
		
		BufferedImage image = new BufferedImage(UserInput.getWidth(), UserInput.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		Color black = new Color(0,0,0);
		int blackRGB = black.getRGB();
		for(int i=0; i<UserInput.getWidth(); i++) {
			for(int j=0; j<UserInput.getHeight(); j++) {
				image.setRGB(i, j, blackRGB);			
			}
		}		
		
		Color green = new Color(128, 255, 0); 
		int rgb = green.getRGB();
		int x1, y1, x2, y2;
		int dx, dy, E, ystep;
		
		//System.out.println("NUMBER OF LINES:   " + num );
		for(int i=0; i<getLineNumber(); i++) {
			
			x1 = (int) array[i][0];
			y1 = (int) array[i][1];
			x2 = (int) array[i][2];
			y2 = (int) array[i][3];
			
			clippingArray = Clipping(x1, y1, x2, y2);
			boolean acceptValue = getAccept();
			if(acceptValue == false) {
				continue;
			}

			x1 = (int) clippingArray[0];
			y1 = (int) clippingArray[1];
			x2 = (int) clippingArray[2];
			y2 = (int) clippingArray[3];

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
