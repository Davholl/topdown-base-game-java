package com.hollanda.main.entity;

import java.awt.image.BufferedImage;

import com.hollanda.main.graficos.Spritesheet;

public class Heart extends Entity{
	
	public static Spritesheet itensSpritesheet = new Spritesheet("/itens.png");

	
	public static BufferedImage HEART = itensSpritesheet.getSprite(16, 0, 16, 16);
	
	public Heart(int x, int y, int width, int height) {
		super(x, y, width, height, HEART);
	}

}
