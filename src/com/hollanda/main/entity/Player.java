package com.hollanda.main.entity;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.hollanda.main.Game;
import com.hollanda.main.graficos.Spritesheet;
import com.hollanda.main.world.Camera;
import com.hollanda.main.world.World;

public class Player extends Entity implements KeyListener{
	
	int speed = 2; 
	boolean right;
	boolean left;
	boolean up;
	boolean down;
	
	private int currentSprite = 0;
	private int animationFrames = 0;
	private int animationFramesPerImage = 5;
	public static Spritesheet playerSpritesheet = new Spritesheet("/char.png");
	public static ArrayList<BufferedImage> currentAnimation = new ArrayList<BufferedImage>();
	
	public ArrayList<BufferedImage> rightAnimation= new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> leftAnimation= new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> upAnimation= new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> downAnimation= new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> idleAnimation= new ArrayList<BufferedImage>();
	
	
	private void setUpAnimations() {
		rightAnimation.add(playerSpritesheet.getSprite(0, 48, 16, 16));
		rightAnimation.add(playerSpritesheet.getSprite(16, 48, 16, 16));
		rightAnimation.add(playerSpritesheet.getSprite(32, 48, 16, 16));
		rightAnimation.add(playerSpritesheet.getSprite(48, 48, 16, 16));
		
		leftAnimation.add(playerSpritesheet.getSprite(0, 32, 16, 16));
		leftAnimation.add(playerSpritesheet.getSprite(16, 32, 16, 16));
		leftAnimation.add(playerSpritesheet.getSprite(32, 32, 16, 16));
		leftAnimation.add(playerSpritesheet.getSprite(48, 32, 16, 16));
		
		upAnimation.add(playerSpritesheet.getSprite(0, 16, 16, 16));
		upAnimation.add(playerSpritesheet.getSprite(16, 16, 16, 16));
		upAnimation.add(playerSpritesheet.getSprite(32, 16, 16, 16));
		upAnimation.add(playerSpritesheet.getSprite(48, 16, 16, 16));
		
		downAnimation.add(playerSpritesheet.getSprite(0, 0, 16, 16));
		downAnimation.add(playerSpritesheet.getSprite(16, 0, 16, 16));
		downAnimation.add(playerSpritesheet.getSprite(32, 0, 16, 16));
		downAnimation.add(playerSpritesheet.getSprite(48, 0, 16, 16));
		
		idleAnimation.add(playerSpritesheet.getSprite(0, 0, 16, 16));
		
		currentAnimation = idleAnimation;
	}
	
	public Player(int x, int y, int width, int height) {
		super(x, y, width, height, playerSpritesheet.getSprite(0, 0, 16, 16));
		setUpAnimations();
	}
	
	@Override
	public void tick() {
		if (right && World.isFree(getX() + speed, getY())) {
			setX(getX() + speed);
			setCurrentAnimation(rightAnimation);
		}else
		if (left && World.isFree(getX() - speed, getY())) {
			setX(getX() - speed);
			setCurrentAnimation(leftAnimation);
		} else
		if (up && World.isFree(getX(), getY() - speed)) {
			setY(getY() - speed);
			setCurrentAnimation(upAnimation);
		}else
		if (down && World.isFree(getX(), getY() + speed)) {
			setY(getY() + speed);
			setCurrentAnimation(downAnimation);
		}else {
			setCurrentAnimation(idleAnimation);
		}
		
		Camera.x = Camera.clamp(this.getX() - Game.WIDTH/2, 0, World.WIDTH*16 - Game.WIDTH) ;
		Camera.y = Camera.clamp(this.getY() - Game.HEIGHT/2, 0, World.HEIGHT*16 - Game.HEIGHT) ;
	}
	
	public void setCurrentAnimation(ArrayList<BufferedImage> anim) {
		if (currentAnimation != anim) {
		currentAnimation = anim;
		currentSprite = 0;
		}
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(currentAnimation.get(currentSprite), this.getX() - Camera.x, this.getY() - Camera.y, null);
		if (animationFrames > animationFramesPerImage) {
			if (currentSprite < currentAnimation.size() - 1) {
				currentSprite++;
				animationFrames = 0;
			}else {
				currentSprite = 0;
				animationFrames = 0;
			}
		}
		animationFrames++;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = true;
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = true;
		}else if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = true;
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right = false;
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left = false;
		}else if (e.getKeyCode() == KeyEvent.VK_UP) {
			up = false;
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down = false;
		}
	}

}
