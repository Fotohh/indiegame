package fotoh;

import fotoh.game.GameObject;
import fotoh.game.state.GameState;
import fotoh.handler.CollisionManager;
import fotoh.util.KeyboardEvent;
import fotoh.window.Handler;
import fotoh.window.Window;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public final class Main extends Canvas implements Runnable {

    public static Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    private boolean running;
    private final Thread thread;
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 960;

    private static final boolean DEBUG = true;

    public static final Logger LOGGER = Logger.getLogger("Main-Thread", null);

    @Getter
    private final Timer timer = new Timer("Main-Thread-Timer");

    @Getter
    private final ConcurrentLinkedQueue<GameObject> gameObjects = new ConcurrentLinkedQueue<>();

    @Getter
    private final Window window = new Window(WIDTH, HEIGHT, "Indie Game", this);
    @Getter
    private final Handler handler = new Handler();
    @Getter
    private final KeyboardEvent event;
    @Getter
    CollisionManager collisionManager = new CollisionManager();

    @Setter
    @Getter
    private GameState state = GameState.DEFAULT(this).onEnable();

    private int fps;
    private double mX, mY;

    public Main() {
        thread = new Thread(this);
        thread.start();
        running = true;
        event = new KeyboardEvent(this);
    }

    public void onDisable(){
        state.onDisable();
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
        state.tick(dt);
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
        state.render(g);

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
