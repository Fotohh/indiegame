package fotoh.objects;

import fotoh.Main;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.handler.Collider;

import java.awt.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Block extends GameObject {

    public Block(float x, float y, float width, float height, ID id, Main main) {
        super(x, y, width, height, id, main);
    }

    @Override
    public void handleCollision(List<GameObject> other, ConcurrentHashMap<Collider.CollisionDirection, Boolean> direction) {

        /*if (direction == Collider.CollisionDirection.BOTTOM) {
            velY = 0;
            y = other.getY() + other.getHeight();
            gravity.setOnGround(true);
        }*/

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

}
