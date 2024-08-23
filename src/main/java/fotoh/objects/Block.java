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
    protected void handleCollision(GameObject other, Collider.CollisionDirection direction) {
        switch (direction) {
            case LEFT -> {
                velX = 0;
                x = other.getX() - width;
            }
            case RIGHT -> {
                velX = 0;
                x = other.getX() + other.getWidth();
            }
            case TOP -> {
                velY = 0;
                y = other.getY() - height;
                gravity.setOnGround(true);
            }
            case BOTTOM -> {
                velY = 0;
                y = other.getY() + other.getHeight();
            }
        }

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

}
