package fotoh.player;

import fotoh.Main;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.util.ImageLoader;
import fotoh.util.KeyboardEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    public Player(float x, float y, float width, float height, Main main) {
        super(x, y, width, height, ID.Player, main);
        Image image = ImageLoader.loadImage(getClass().getResource("/person.png").getFile());
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
    public void tick() {
        handleMovement();
        applyGravity();
        checkBounds();
    }

    private void handleMovement() {
        if (d_down && a_down) velX = 0;
        else if (d_down) velX = 5;
        else if (a_down) velX = -5;
        x += velX;
    }

    private void applyGravity() {
        velY += 1;
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

    private boolean onGround = true;
    private boolean d_down = false;
    private boolean a_down = false;

    @Override
    public void initializeControls() {
        getEvent().add(event -> {
                    switch (event.getKeyCode()) {
                        case KeyEvent.VK_D -> d_down = true;
                        case KeyEvent.VK_A -> a_down = true;
                        case KeyEvent.VK_W -> {
                            if (onGround) {
                                velY = -18;
                                onGround = false;
                            }
                        }
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
                        case KeyEvent.VK_W -> {
                            if (velY < -6.0) velY = -6;
                        }
                    }
                }, KeyboardEvent.Type.RELEASED, this);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(getEntityImage(), (int) x, (int) y, main.getWindow().getJFrame());
    }

}
