package edu.uga.cs.cs4810;

public class Transformations {
	
	public double[][] BasicTranslate(int Tx, int Ty, int Tz) {
		double[][] matrix = {{1,  0,  0,  0},
							 {0,  1,  0,  0},
							 {0,  0,  1,  0},
							 {Tx, Ty, Tz, 0}};
		return matrix;
	}
	
	public double[][] BasicScale(double Sx, double Sy, double Sz) {
		if(Sx == 0) Sx=1;
		if(Sy == 0) Sy=1;
		double[][] matrix = {{Sx, 0,  0,  0}, 
							 {0,  Sy, 0,  0},
							 {0,  0,  Sz, 0},
							 {0,  0,  0,  1}}; 
		return matrix;
	}
	
	public double[][] BasicZRotate(double angle) {
		double theta = Math.toRadians(angle);
		double cos = Math.cos(theta);
		double sin =  Math.sin(theta);
		double[][] matrix = {{ cos,  sin, 0, 0}, 
							 {-sin,  cos, 0, 0}, 
							 {  0,    0,  1, 0},
						   	 {  0,    0,  0, 1}};
		return matrix;
	}
	
	public double[][] BasicYRotate(double angle) {
		double theta = Math.toRadians(angle);
		double cos = Math.cos(theta);
		double sin =  Math.sin(theta);
		double[][] matrix = {{cos, 0, -sin, 0}, 
							 { 0,  1,   0,  0}, 
							 {sin, 0,  cos, 0},
						   	 { 0,  0,   0,  1}};
		return matrix;
	}
	
	public double[][] BasicXRotate(double angle) {
		double theta = Math.toRadians(angle);
		double cos = Math.cos(theta);
		double sin =  Math.sin(theta);
		double[][] matrix = {{1,   0,   0,  0}, 
							 {0,  cos, sin, 0}, 
							 {0, -sin, cos, 0},
						   	 {0,   0,   0,  1}};
		return matrix;
	}
	
	public double[][] Concatenate(double[][] matrix1, double[][] matrix2) {
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

}
