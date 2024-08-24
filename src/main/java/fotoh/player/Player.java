package fotoh.player;

import fotoh.Main;
import fotoh.game.LivingEntity;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.handler.Collider;
import fotoh.util.KeyboardEvent;
import fotoh.util.ResourceManager;
import lombok.Getter;

import java.awt.*;
import java.awt.event.KeyEvent;

@Getter
public class Player extends LivingEntity {

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
        gravity.setEnabled(true);
        getControllable().setEnabled(true);
    }

    @Override
    protected void handleCollision(GameObject other, Collider.CollisionDirection collisionDirection) {
        switch (collisionDirection) {
            case LEFT -> {
                setVelX(0);
                setX(other.getX() - getWidth());
            }
            case RIGHT -> {
                setVelX(0);
                setX(other.getX() + other.getWidth());
            }
            case TOP -> {
                setVelY(0);
                setY(other.getY() - getHeight());
                getGravity().setOnGround(true);
            }
            case BOTTOM -> {
                setVelY(0);
                setY(other.getY() + other.getHeight());
            }
        }
    }

    @Override
    public void tick(float dt) {
        handleMovement(dt);
        gravity.applyGravity();
        getCollider().checkBounds(this, main);
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
            velY -= gravity.getAccelerationY()  * dt;
            if (velY < -gravity.getMAX_SPEED_Y()) velY = -gravity.getMAX_SPEED_Y();
        } else {
            gravity.fall(dt);
        }

        y += velY * dt;
        x += velX * dt;
    }

    @Override
    public void initializeControls() {
        controllable.keyPressed(KeyEvent.VK_D, _ -> d_down = true);
        controllable.keyReleased(KeyEvent.VK_D, _ -> d_down = false);

        controllable.keyPressed(KeyEvent.VK_A, _ -> a_down = true);
        controllable.keyReleased(KeyEvent.VK_A, _ -> a_down = false);

        controllable.keyPressed(KeyEvent.VK_W, _ -> {
            if (gravity.isOnGround()) {
                velY = gravity.getJUMP_FORCE();
                gravity.setOnGround(false);
            }
            w_down = true;
        });
        controllable.keyReleased(KeyEvent.VK_W, _ -> w_down = false);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(getEntityImage(), (int) x, (int) y, main.getWindow().getJFrame());
    }

}
