package com.unipi.alexandris.game.MASHLE;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import com.unipi.alexandris.game.MASHLE.sensors.*;
import com.unipi.alexandris.game.MASHLE.util.*;
import com.unipi.alexandris.game.MASHLE.core.*;


public class Game extends Canvas implements Runnable {
    private static final long serialVersionUID = 5839527675529025230L;

    //=========== GLOBAL VARIABLES ===========//
    public static int coordX, coordY;
    public static final int WIDTH = 1000 , HEIGHT = WIDTH / 12 * 9;
    public static int i_p_x, i_p_y;
    public static GameObject player;

    //========================================//

    private Thread thread;
    private Camera camera;
    private boolean running = false;
    private Handler handler;
    private long fps = 0;


    public Game(){
        //BufferedImageLoader loader = new BufferedImageLoader();
        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(new MouseInput(handler));
        this.addMouseMotionListener(new MouseMotionSensor(handler));
        new Window(WIDTH, HEIGHT, "Clasic Environment!", this);
    }

//	private void reset() {
//		i_p_x = 0;
//		i_p_y = 0;
//		handler.object.clear();
//	}

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }


    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void run() {
        //common game loop.
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        long frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                fps = frames;
                frames = 0;
            }
        }
        stop();
    }


    private void tick() {

        for(int i = 0; i < handler.object.size(); i++) {
            if(handler.object.get(i).getId() == ID.Player) {
                camera.tick(handler.object.get(i));
            }
        }

        //touchOrb.tick();
        handler.tick();
    }


    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d = (Graphics2D) g;

        g.setColor(Color.black);
        g.fillRect(0, 0, 2000, 2000);

        if(handler.object.size() > 0) g2d.translate(-camera.getX(), -camera.getY());

        handler.render(g);

        if(handler.object.size() > 0) g2d.translate(camera.getX(), camera.getY());

        g.setColor(Color.white);
        g.drawString("FPS: " + fps, 10, 10);
        if(player != null) g.drawString("X: " + player.x, 10, 20);
        if(player != null) g.drawString("Y: " + player.y, 10, 30);

        g.dispose();
        bs.show();

    }

    public static double clamp(double var, double min, double max) {
        if(var >= max) return var = max;
        else if(var <= min) return var = min;
        else return var;
    }

    public static void main(String[] args) {
        new Game();
    }

}
