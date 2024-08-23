package fotoh;

import fotoh.game.ID;
import fotoh.handler.CollisionManager;
import fotoh.player.Player;
import fotoh.util.KeyboardEvent;
import fotoh.window.Handler;
import fotoh.window.Window;
import lombok.Getter;
import fotoh.objects.Block;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public final class Main extends Canvas implements Runnable {

    private boolean running;
    private final Thread thread;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 960;

    private static final boolean DEBUG = true;

    @Getter
    private final Window window = new Window(WIDTH, HEIGHT, "Indie Game", this);
    @Getter
    private final Handler handler = new Handler();
    @Getter
    private final KeyboardEvent event;
    @Getter
    private final CollisionManager collisionManager = new CollisionManager();

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private int fps;
    private double mX, mY;

    public Main() {
        thread = new Thread(this);
        thread.start();
        running = true;
        event = new KeyboardEvent(this);
        Player player = new Player(500, getHeight(), 32, 32, this);
        Block block = new Block(400, 880, 32,32, ID.Block, this);
        Block block2 = new Block(400, 800, 32,32, ID.Block, this);
    }

    public static void main(String[] args) {
        new Main();
    }

    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60f;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick((float) delta);
                delta--;
            }
            if (running)
                render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                if (DEBUG) {
                    fps = frames;
                    if (getMousePosition() != null){
                        mX = getMousePosition().getX();
                        mY = getMousePosition().getY();
                    }
                }
                frames = 0;
            }
        }
        stop();
    }

    private void tick(float dt) {
        handler.tick(dt);
        collisionManager.update();
    }

    private void render() {

        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bufferStrategy.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        handler.render(g);

        if (DEBUG) {
            g.setColor(Color.BLACK);
            g.drawString("FPS: " + fps, 10, 20);
            g.drawString("Mouse X: " + mX, 10, 40);
            g.drawString("Mouse Y: " + mY, 10, 60);
        }

        g.dispose();
        bufferStrategy.show();
    }

    public void stop() {
        try {
            running = false;
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
