package com.fotohh.indiegame;

import com.fotohh.indiegame.window.Handler;
import com.fotohh.indiegame.window.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    private boolean running = false;
    private Thread thread;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = WIDTH / 12 * 9;
    private Window window = new Window(WIDTH,HEIGHT, "Indie Game", this);
    private Handler handler = new Handler();

    public Main(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public static void main(String[] args) {
        new Main();
    }

    public void run(){
        long lastTime = System.nanoTime();
        double amountOfTicks = 60f;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);

                frames = 0;
            }
        }
        stop();
    }

    public synchronized void start(){

    }

    private void tick(){
        handler.tick();
    }

    private void render(){
        BufferStrategy bs = getBufferStrategy();
        if(bs == null){
            createBufferStrategy(3);
            return;
        }

        if(getMousePosition() != null)
            System.out.println("mx: " + getMousePosition().getX() + " my: " + getMousePosition().getY());

        Graphics g = bs.getDrawGraphics();



        handler.render(g);

        System.out.flush();
    }

    public void stop(){
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
