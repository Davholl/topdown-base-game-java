package com.hollanda.main.entity;

import java.awt.image.BufferedImage;

import com.hollanda.main.graficos.Spritesheet;

public class Weapon extends Entity{
	
	public static Spritesheet itensSpritesheet = new Spritesheet("/itens.png");

	
	public static BufferedImage WEAPON = itensSpritesheet.getSprite(0, 0, 16, 16);

	public Weapon(int x, int y, int width, int height) {
		super(x, y, width, height, WEAPON);
	}

}
