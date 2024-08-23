package fotoh.player;

import fotoh.Main;
import fotoh.game.Entity;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.util.KeyboardEvent;
import fotoh.util.ResourceManager;
import lombok.Getter;

import java.awt.*;
import java.awt.event.KeyEvent;

@Getter
public class Player extends Entity {

    private final float accelerationX = 0.5f;
    private final float MAX_SPEED_X = 10.0f;
    protected boolean d_down = false;
    protected boolean a_down = false;
    protected boolean w_down = false;

    public Player(float x, float y, float width, float height, Main main) {
        super(x, y, width, height, ID.Player, main);
        Image image = ResourceManager.getImage(getClass().getResource("/person.png").getFile());
        setEntityImage(image.getScaledInstance((int) width, (int) height, Image.SCALE_DEFAULT));
        getCollider().onCollide(gameObject -> handleCollision(gameObject, getCollider().getCollisionDirection(this, gameObject)));
        getGravity().setEnabled(true);
    }

    @Override
    protected void handleCollision(GameObject other, String collisionDirection) {
        switch (collisionDirection) {
            case "LEFT" -> {
                setVelX(0);
                setX(other.getX() - getWidth());
            }
            case "RIGHT" -> {
                setVelX(0);
                setX(other.getX() + other.getWidth());
            }
            case "TOP" -> {
                setVelY(0);
                setY(other.getY() - getHeight());
                getGravity().setOnGround(true);
            }
            case "BOTTOM" -> {
                setVelY(0);
                setY(other.getY() + other.getHeight());
            }
        }
    }

    @Override
    public void tick(float dt) {
        handleMovement(dt);
        getGravity().applyGravity();


        // Prevent player from going out of bounds
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
            getGravity().setOnGround(true);
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
            velY -= getGravity().getAccelerationY()  * dt;
            if (velY < -getGravity().getMAX_SPEED_Y()) velY = -getGravity().getMAX_SPEED_Y();
        } else {
            getGravity().fall(dt);
        }

        y += velY * dt;
        x += velX * dt;
    }

    @Override
    public void initializeControls() {
        getEvent().add(event -> {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_D -> d_down = true;
                case KeyEvent.VK_A -> a_down = true;
                case KeyEvent.VK_W -> {
                    if (getGravity().isOnGround()) {
                        velY = getGravity().getJUMP_FORCE();
                        getGravity().setOnGround(false);
                    }
                }
            }
        }, KeyboardEvent.Type.PRESSED, this)
                .add(event -> {
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
