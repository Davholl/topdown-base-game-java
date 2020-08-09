package com.hollanda.main.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.hollanda.main.Game;
import com.hollanda.main.entity.Bolt;
import com.hollanda.main.entity.Enemy;
import com.hollanda.main.entity.Heart;
import com.hollanda.main.entity.Weapon;

public class World {
	
	private static final int TILE_SIZE = 16;
	private static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			tiles = new Tile[pixels.length];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			for (int xx = 0; xx < map.getWidth(); xx++) {
				for (int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy*WIDTH)] = new FloorTile(xx*TILE_SIZE, yy*TILE_SIZE);
					if (pixelAtual == 0xFFD22828) {
						System.out.println("Vermelho");
						//Inimigo
						Game.entities.add(new Enemy(xx*TILE_SIZE, yy*TILE_SIZE, TILE_SIZE, TILE_SIZE));
					}
					if (pixelAtual == 0xFFF9F9F9) {
						System.out.println("Branco");
						//Wall
						tiles[xx + (yy*WIDTH)] = new WallTile(xx*TILE_SIZE, yy*TILE_SIZE);
					}
					if (pixelAtual == 0xFF000000) {
						System.out.println("Preto");
						//Chãozinho
					}
					if (pixelAtual == 0xFF2E4CD2) {
						System.out.println("Azul");
						//Player
						Game.player.setX(xx*TILE_SIZE);
						Game.player.setY(yy*TILE_SIZE);
					}
					if (pixelAtual == 0xFFD0D22E) {
						System.out.println("Amarelo");
						//Weapon
						Game.entities.add(new Heart(xx*TILE_SIZE, yy*TILE_SIZE, TILE_SIZE, TILE_SIZE));
					}
					if (pixelAtual == 0xFFD22EBD) {
						System.out.println("Rosa");
						//Life
						Game.entities.add(new Weapon(xx*TILE_SIZE, yy*TILE_SIZE, TILE_SIZE, TILE_SIZE));
					}
					if (pixelAtual == 0xFFD2992E) {
						System.out.println("Laranja");
						//Bolt
						Game.entities.add(new Bolt(xx*TILE_SIZE, yy*TILE_SIZE, TILE_SIZE, TILE_SIZE));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 4;
		int ystart = Camera.y >> 4;
		int xfinal = xstart + (Game.WIDTH >> 4);
		int yfinal = ystart + (Game.HEIGHT >> 4);
		
		for (int xx = xstart; xx <= xfinal; xx++) {
			for (int yy = ystart; yy <= yfinal; yy++) {
				if (xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) 
					continue;
				Tile tile = tiles[xx + (yy*WIDTH)];
				tile.render(g);
			}
		}
	}
	
	public static boolean isFree(int xNext, int yNext) {
		int x1 = xNext/TILE_SIZE;
		int y1 = yNext/TILE_SIZE;
		
		int x2 = xNext + TILE_SIZE-1/TILE_SIZE;
		int y2 = yNext / TILE_SIZE;
		
		int x3 = xNext / TILE_SIZE;
		int y3 = yNext + TILE_SIZE -1 / TILE_SIZE;
		
		int x4 = xNext + TILE_SIZE - 1/ TILE_SIZE;
		int y4 = yNext + TILE_SIZE-1/TILE_SIZE;
		
		return !(tiles[x1 + (y1*World.WIDTH)] instanceof WallTile)
				|| !(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile)
				|| !(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile)
				|| !(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile);
	}
}
