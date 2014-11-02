package edu.uga.cs.cs4810;

public class UserInput {
	
	private static int width, height, xLocation, yLocation, translateX, translateY;
	private static double scaleX, scaleY, rotateX, rotateY, rotateZ, distance, size;
	private static double[][] datalines, clippingData, PerspectiveData;
	
	public static void setDatalines(double[][] data) {
		datalines = data;
	}
	
	public static void setWidth(String w) {
		width = Integer.parseInt(w);
	}
	
	public static void setHeight(String h) {
		height = Integer.parseInt(h);
	}
	
	public static void setXLocation(String x) {
		xLocation = Integer.parseInt(x);
	}
	
	public static void setYLocation(String y) {
		yLocation = Integer.parseInt(y);
	}
	
	public static void setDistance(String d) {
		distance = Double.parseDouble(d);
	}
	
	public static void setSize(String s) {
		size = Double.parseDouble(s);
	}
	
	public static void setTranslateX(String t) {
		translateX = Integer.parseInt(t);
	}
	
	public static void setTranslateY(String t) {
		translateY = Integer.parseInt(t);
	}
	
	public static void setScaleX(String s) {
		scaleX = Double.parseDouble(s);
	}
	
	public static void setScaleY(String s) {
		scaleY = Double.parseDouble(s);
	}
	
	public static void setRotateX(String r) {
		rotateX = Double.parseDouble(r);
	}
	
	public static void setRotateY(String r) {
		rotateY = Double.parseDouble(r);
	}
	
	public static void setRotateZ(String r) {
		rotateZ = Double.parseDouble(r);
	}
	
	public static int getWidth() {
		return width;
	}
	
	public static int getHeight() {
		return height;
	}
	
	public static double getDistance() {
		return distance;
	}
	
	public static double getSize() {
		return size;
	}
	
	public static int getTranslateX() {
		return translateX;
	}
	
	public static int getTranslateY() {
		return translateY;
	}
	
	public static double getScaleX() {
		return scaleX;
	}
	
	public static double getScaleY() {
		return scaleY;
	}
	
	public static double getRotateX() {
		return rotateX;
	}
	
	public static double getRotateY() {
		return rotateY;
	}
	
	public static double getRotateZ() {
		return rotateZ;
	}
	
	public static int getXLocation() {
		return xLocation;
	}
	
	public static int getYLocation() {
		return yLocation;
	
	}
}


