package com.hollanda.main.world;

public class Camera {
	public static int x = 60;
	public static int y = 60;
	
	public static int clamp(int xAtual, int xMin, int xMax) {
		if (xAtual < xMin) {
			xAtual = xMin;
		}
		if (xAtual > xMax) {
			xAtual = xMax;
		}
		
		return xAtual;
	}
}
