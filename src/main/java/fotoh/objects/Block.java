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
        gravity.fall(dt);
        y += velY * dt;
        gravity.applyGravity();
        if(getCollider().isEnabled()) {
            getCollider().checkCollision(this);
        }
    }

    @Override
    protected void handleCollision(GameObject other, Collider.CollisionDirection collisionDirection) {
        switch (collisionDirection) {
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
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

}
