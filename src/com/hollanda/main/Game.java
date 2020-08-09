package com.hollanda.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import com.hollanda.main.entity.Entity;
import com.hollanda.main.entity.Player;
import com.hollanda.main.world.World;

public class Game extends Canvas implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6159070902267584169L;
	
	//Thread
	public Thread thread;
	
	private Boolean isRunning;
	
	//Frame
	public static JFrame frame;
	public final static int HEIGHT = 160;
	public final static int WIDTH = 240;
	private final int SCALE = 3;
	
	private BufferedImage image;
	
	public static List<Entity> entities;
	
	public static World world;
	public static Player player;
	
	public Game() {
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		
		
		
		player = new Player(0, 0, 16, 16);
		world = new World("/map.png");
		entities.add(player);
		addKeyListener(player);
	}
	
	private void initFrame() {
		frame = new JFrame("Frame-Teste");
		frame.add(this);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] args) {
		Game game = new Game();
		game.start();
	}
	
	//Lógica
	public void tick() {
		for (Entity entity : entities) {
			entity.tick();
		}
	}
	
	//Renderizar os Gráficos
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		//Fundo
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		for (Entity entity : entities) {
			entity.render(g);
		}
		
		g.dispose();
		
		//Mostrar tudo
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		bs.show();
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		
		while(isRunning) {
			//A cada vez que rodar, verifique quanto tempo passou
			//e some ao delta. Quando o delta for maior que 1,
			//Significa que passou 1 frame, então chame os métodos
			//tick para lógica e render para os gráficos
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			
			if (delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			
			
			if (System.currentTimeMillis() - timer >= 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer += 1000;
			}
		}
		
		stop();
		
	}

}
