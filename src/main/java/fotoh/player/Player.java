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
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Player extends LivingEntity {

    private final float accelerationX = 0.5f;
    private final float MAX_SPEED_X = 10.0f;
    protected boolean d_down = false;
    protected boolean a_down = false;
    private boolean canMoveLeft = true;
    private boolean canMoveRight = true;


    public Player(float x, float y, float width, float height, Main main) {
        super(x, y, width, height, ID.Player, main);
        Image image = ResourceManager.getImage(getClass().getResource("/person.png").getFile());
        entityImage = image.getScaledInstance((int) width, (int) height, Image.SCALE_DEFAULT);
        gravity.setEnabled(true);
        controllable.setEnabled(true);
    }

    @Override
    public void handleCollision(List<GameObject> other, ConcurrentHashMap<Collider.CollisionDirection, Boolean> directions) {

        boolean bottom = directions.get(Collider.CollisionDirection.BOTTOM);
        boolean top = directions.get(Collider.CollisionDirection.TOP);
        boolean left = directions.get(Collider.CollisionDirection.LEFT);
        boolean right = directions.get(Collider.CollisionDirection.RIGHT);

        for(GameObject o : other) {

            if (bottom) {
                velY = 0;
                y = o.getY() + height;
            }
            gravity.setOnGround(bottom);

            if (top) {
                velY = 0;
                y = o.getY() - height;
            }

            if (left) {
                velX = 0;
                x = o.getX() + o.getWidth();
                canMoveLeft = false;
            } else {
                canMoveLeft = true;
            }


            if (right) {
                velX = 0;
                x = o.getX() - o.getWidth();
                canMoveRight = false;
            } else {
                canMoveRight = true;
            }

        }

    }

    private void moveLeftRight(){
        if (d_down) {
            if(!canMoveRight) return;
            velX = 5;
        }
        if (a_down) {
            if(!canMoveLeft) return;
            velX = -5;
        }else {
            velX = 0;
        }
    }

    @Override
    protected void handleMovement(float dt) {

        moveLeftRight();

        if (w_down) {
            velY -= gravity.getAccelerationY() * dt;
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
            w_down = true;
            if (gravity.isOnGround()) {
                velY = gravity.getJUMP_FORCE();
                gravity.setOnGround(false);
            }
        });
        controllable.keyReleased(KeyEvent.VK_W, _ -> w_down = false);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(getEntityImage(), (int) x, (int) y, main.getWindow().getJFrame());
    }

}
