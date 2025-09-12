package fotoh;

import fotoh.game.GameObject;
import fotoh.game.state.Active;
import fotoh.game.state.GameState;
import fotoh.handler.CollisionManager;
import fotoh.listener.ClickListener;
import fotoh.listener.ClickType;
import fotoh.log.GameLogger;
import fotoh.util.KeyboardEvent;
import fotoh.window.Window;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class Main extends Canvas implements Runnable {

    public static Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 12);

    private boolean running;
    private final Thread thread;
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private static final boolean DEBUG = true;

    public static final GameLogger LOGGER = new GameLogger("Main-Thread", null);

    @Getter
    private final Timer timer = new Timer("Main-Thread-Timer");

    @Getter
    private final ClickListener clickListener = new ClickListener();

    @Getter
    private final ConcurrentLinkedQueue<GameObject> gameObjects = new ConcurrentLinkedQueue<>();

    @Getter
    private final Window window;
    @Getter
    private final KeyboardEvent event;
    @Getter
    CollisionManager collisionManager = new CollisionManager();

    @Getter
    private GameState state;

    public void setState(GameState state) {
        if(this.state != null) this.state.onDisable();
        this.state = state.onEnable();
    }

    private int fps;
    private double mX, mY;

    public Main() {
        window = new Window(WIDTH, HEIGHT, "Indie Game", this);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                getClickListener().tick(e, ClickType.PRESSED);
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                getClickListener().tick(e, ClickType.RELEASED);
                super.mouseReleased(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                getClickListener().tick(e, ClickType.HOVER);
            }
        });
        thread = new Thread(this);
        thread.start();
        running = true;
        event = new KeyboardEvent(this);
        state = GameState.DEFAULT(this).onEnable();
    }

    public void onDisable(){
        running = false;
        state.onDisable();
        window.getJFrame().setVisible(false);
        window.getJFrame().dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        new Main();
        //YML yaml = new YML(new File(Main.class.getResource("/wtf.yml").getFile()));
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
        collisionManager.update();
        if(state != null) state.tick(dt);
    }

    private void render() {

        if(!running) return;

        BufferStrategy bufferStrategy = getBufferStrategy();
        if (bufferStrategy == null) {
            createBufferStrategy(3);
            return;
        }
        if(bufferStrategy.getDrawGraphics() == null) return;
        Graphics g = bufferStrategy.getDrawGraphics();

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        if(state != null) state.render(g);

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
