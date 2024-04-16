package fotoh;

import fotoh.player.Player;
import fotoh.util.ImageLoader;
import fotoh.util.KeyboardEvent;
import fotoh.window.Handler;
import fotoh.window.Window;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable {

    private boolean running;
    private final Thread thread;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 960;

    @Getter
    private final Window window = new Window(WIDTH, HEIGHT, "Indie Game", this);
    @Getter
    private final Handler handler = new Handler();
    @Getter
    private final KeyboardEvent event;

    public Main() {
        thread = new Thread(this);
        thread.start();
        running = true;
        event = new KeyboardEvent(this);
        Player player = new Player(200, 860, 32, 32, this);
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
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                if (ENABLED && getMousePosition() != null)
                    System.out.println("mx: " + getMousePosition().getX() + " my: " + getMousePosition().getY());
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
    }

    private static final boolean ENABLED = true;

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

        g.dispose();
        bufferStrategy.show();
    }

    public void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
