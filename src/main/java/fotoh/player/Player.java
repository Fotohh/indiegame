package fotoh.player;

import fotoh.Main;
import fotoh.game.HealthBar;
import fotoh.game.LivingEntity;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.handler.Collider;
import fotoh.util.ResourceManager;
import lombok.Getter;

import java.awt.*;
import java.awt.event.KeyEvent;

@Getter
public class Player extends LivingEntity {

    private static final float ACCELERATION_FACTOR = 0.8f;
    private static final float MAX_SPEED = 7.0f;
    protected boolean d_down = false;
    protected boolean a_down = false;
    protected boolean w_down = false;
    protected boolean s_down = false;
    private final HealthBar healthBar;

    public Player(float x, float y, float width, float height, Main main) {
        super(x, y, width, height, ID.Player, main);
        Image image = ResourceManager.getImage(getClass().getResource("/person.png").getFile());
        setEntityImage(image.getScaledInstance((int) width, (int) height, Image.SCALE_DEFAULT));
        getCollider().onCollide(gameObject -> handleCollision(gameObject, getCollider().getCollisionDirection(this, gameObject)));
        getControllable().setEnabled(true);
        healthBar = new HealthBar(this, 35, 5, width * 0.2F, -20);
    }

    @Override
    protected void handleCollision(GameObject other, Collider.CollisionDirection collisionDirection) {
        switch (collisionDirection) {
            case LEFT -> {
                velX = 0;
                x = other.getX() - width;
            }
            case RIGHT -> {
                velX = 0;
                x = other.getX() + width;
            }
            case BOTTOM -> {
                velY = 0;
                y = other.getY() - height;
            }
            case TOP -> {
                velY = 0;
                y = other.getY() + height;
            }
        }
    }

    @Override
    public void tick(float dt) {

        if(!isEnabled()) return;

        if(controllable.isEnabled()) handleMovement(dt);

        healthBar.tick(dt);

        x = Math.max(0, Math.min(x, main.getWidth() - width));
        if (x == 0 || x + width == main.getWidth()) velX = 0;

        y = Math.max(0, Math.min(y, main.getHeight() - height));
        if (y == 0 || y + height == main.getHeight()) velY = 0;
    }

    private enum Direction { LEFT_RIGHT, UP_DOWN }

    private void handleInput(Direction direction, float dt) {
        boolean positiveKey = direction == Direction.UP_DOWN ? s_down : d_down;
        boolean negativeKey = direction == Direction.UP_DOWN ? w_down : a_down;
        float velocity = direction == Direction.UP_DOWN ? velY : velX;

        if (positiveKey && negativeKey) velocity = 0;
        else if (positiveKey) velocity = Math.min(velocity + ACCELERATION_FACTOR * dt, MAX_SPEED);
        else if (negativeKey) velocity = Math.max(velocity - ACCELERATION_FACTOR * dt, -MAX_SPEED);
        else velocity = velocity > 0 ? Math.max(velocity - ACCELERATION_FACTOR * dt, 0) : Math.min(velocity + ACCELERATION_FACTOR * dt, 0);

        if (direction == Direction.LEFT_RIGHT) {
            velX = velocity;
            x += velX * dt;
        } else {
            velY = velocity;
            y += velY * dt;
        }
    }

    @Override
    protected void handleMovement(float dt) {
        handleInput(Direction.LEFT_RIGHT, dt);
        handleInput(Direction.UP_DOWN, dt);
    }

    @Override
    public void initializeControls() {
        controllable.keyPressed(KeyEvent.VK_D, _ -> d_down = true);
        controllable.keyReleased(KeyEvent.VK_D, _ -> d_down = false);

        controllable.keyPressed(KeyEvent.VK_A, _ -> a_down = true);
        controllable.keyReleased(KeyEvent.VK_A, _ -> a_down = false);

        controllable.keyPressed(KeyEvent.VK_W, _ -> w_down = true);
        controllable.keyReleased(KeyEvent.VK_W, _ -> w_down = false);

        controllable.keyPressed(KeyEvent.VK_S, _ -> s_down = true);
        controllable.keyReleased(KeyEvent.VK_S, _ -> s_down = false);
    }

    @Override
    public void render(Graphics g) {
        if(!isEnabled()) return;
        g.setColor(Color.BLUE);
        g.drawImage(getEntityImage(), (int) x, (int) y, main.getWindow().getJFrame());
        healthBar.render(g.create());
    }

}
