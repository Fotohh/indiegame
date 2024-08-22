package fotoh.player;

import fotoh.Main;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.util.KeyboardEvent;
import fotoh.util.ResourceManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    public Player(float x, float y, float width, float height, Main main) {
        super(x, y, width, height, ID.Player, main);
        Image image = ResourceManager.getImage(getClass().getResource("/person.png").getFile());
        setEntityImage(image.getScaledInstance((int) width, (int) height, Image.SCALE_DEFAULT));

        getCollider().onCollide(this::handleCollision);
    }

    private void handleCollision(GameObject gameObject) {
        String collisionDirection = getCollider().getCollisionDirection(this, gameObject);
        switch (collisionDirection) {
            case "LEFT" -> {
                setVelX(0);
                setX(gameObject.getX() - getWidth());
            }
            case "RIGHT" -> {
                setVelX(0);
                setX(gameObject.getX() + gameObject.getWidth());
            }
            case "TOP" -> {
                setVelY(0);
                setY(gameObject.getY() - getHeight());
                onGround = true;
            }
            case "BOTTOM" -> {
                setVelY(0);
                setY(gameObject.getY() + gameObject.getHeight());
            }
        }
    }

    @Override
    public void tick(float dt) {
        handleMovement(dt);
        applyGravity();
        checkBounds();
    }

    private static final float accelerationX = 0.5f;
    private static final float accelerationY = 0.5f;
    private static final float MAX_SPEED_Y = 10.0f;
    private static final float MAX_SPEED_X = 10.0f;
    private static final float GRAVITY = 1f;
    private static final float JUMP_FORCE = -15.0f;
    private boolean onGround = true;
    private boolean d_down = false;
    private boolean a_down = false;
    private boolean w_down = false;

    private void jump() {
        if (onGround) {
            velY = JUMP_FORCE;
            onGround = false;
        }
    }

    private void handleMovement(float dt) {

        if(d_down && a_down){
            velX = 0;
        } else if(d_down){
            velX += accelerationX * dt;
            if (velX > MAX_SPEED_X) velX = MAX_SPEED_X;
        } else if(a_down){
            velX -= accelerationX * dt;
            if (velX < -MAX_SPEED_X) velX = -MAX_SPEED_X;
        } else {
            if (velX > 0) {
                velX -= accelerationX * dt;
                if (velX < 0) velX = 0;
            } else if (velX < 0) {
                velX += accelerationX * dt;
                if (velX > 0) velX = 0;
            }
        }

        if (w_down) {
            velY -= accelerationY * dt;
            if (velY < -MAX_SPEED_Y) velY = -MAX_SPEED_Y;
        } else {
            if (velY > 0) {
                velY -= accelerationY * dt;
                if (velY < 0) velY = 0;
            } else if (velY < 0) {
                velY += accelerationY * dt;
                if (velY > 0) velY = 0;
            }
        }

        y += velY * dt;
        x += velX * dt;
    }

    private void applyGravity() {
        velY += GRAVITY;
        if (velY > MAX_SPEED_Y) velY = MAX_SPEED_Y;
        y += velY;
    }

    private void checkBounds() {
        if (x < 0) {
            x = 0;
            velX = 0;
        } else if (x + width > main.getWidth()) {
            x = main.getWidth() - width;
            velX = 0;
        }

        if (y < 0) {
            y = 0;
            velY = 0;
        } else if (y + height > main.getHeight()) {
            y = main.getHeight() - height;
            velY = 0;
            onGround = true;
        }
    }

    @Override
    public void initializeControls() {
        getEvent().add(event -> {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_D -> d_down = true;
                case KeyEvent.VK_A -> a_down = true;
                case KeyEvent.VK_W -> jump();
            }
        }, KeyboardEvent.Type.PRESSED, this);
        getEvent().add(event -> {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_D -> {
                    setVelX(0);
                    d_down = false;
                }
                case KeyEvent.VK_A -> {
                    setVelX(0);
                    a_down = false;
                }
                case KeyEvent.VK_W -> w_down = false;
            }
        }, KeyboardEvent.Type.RELEASED, this);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(getEntityImage(), (int) x, (int) y, main.getWindow().getJFrame());
    }

}
