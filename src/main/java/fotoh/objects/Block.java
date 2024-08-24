package fotoh.objects;

import fotoh.Main;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.handler.Collider;

import java.awt.*;

public class Block extends GameObject {

    public Block(float x, float y, float width, float height, ID id, Main main) {
        super(x, y, width, height, id, main);
        getCollider().onCollide(gameObject -> handleCollision(gameObject, getCollider().getCollisionDirection(this, gameObject)));
    }

    @Override
    public void tick(float dt) {

        if(controllable.isEnabled()) handleMovement(dt);
        if(!gravity.isOnGround()) gravity.fall(dt);
        y += velY * dt;
        gravity.applyGravity();

        if (y < 0) {
            y = 0;
            velY = 0;
        } else if (y + height > main.getHeight()) {
            y = main.getHeight() - height;
            velY = 0;
            getGravity().setOnGround(true);
        }

    }

    @Override
    protected void handleCollision(GameObject other, Collider.CollisionDirection collisionDirection) {
        switch (collisionDirection) {
            case TOP -> {
                other.setVelY(0);
                other.setY(y - other.getHeight());
                other.getGravity().setOnGround(true);
            }
            case BOTTOM -> {
                velY = 0;
                y = other.getY() - height;
                gravity.setOnGround(true);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

}
