package com.hollanda.main.entity;

import java.awt.image.BufferedImage;

import com.hollanda.main.graficos.Spritesheet;

public class Bolt extends Entity{
	
	public static Spritesheet itensSpritesheet = new Spritesheet("/itens.png");

	
	public static BufferedImage BOLT = itensSpritesheet.getSprite(32, 0, 16, 16);
	
	
	public Bolt(int x, int y, int width, int height) {
		super(x, y, width, height, BOLT);
	}

}
