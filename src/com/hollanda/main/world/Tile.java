package com.hollanda.main.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.hollanda.main.graficos.Spritesheet;

public class Tile {
	
	public static Spritesheet tileSpritesheet = new Spritesheet("/grass.png");

	public static BufferedImage TILE_FLOOR = tileSpritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL = tileSpritesheet.getSprite(16, 0, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
