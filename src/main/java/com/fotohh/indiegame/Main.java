package com.fotohh.indiegame;

import com.fotohh.indiegame.window.Handler;
import com.fotohh.indiegame.window.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    private boolean running = false;
    private Thread thread;
    private Window window;
    private Handler handler;

    public Main(){
        start();
        window = new Window(1280,960, "Indie Game", this);
        handler = new Handler();
    }

    public static void main(String[] args) {
        new Main();
    }

    public synchronized void run(){
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

    public void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    private void tick(){
        handler.tick();
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
        }

        System.out.println("mx: " + getMousePosition().x + " my: " + getMousePosition().y);

        Graphics g = bs.getDrawGraphics();

        handler.render(g);

        System.out.flush();
    }

    public void stop(){
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
