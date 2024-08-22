package objects;

import fotoh.Main;
import fotoh.game.GameObject;
import fotoh.game.ID;

import java.awt.*;

public class Block extends GameObject {

    public Block(float x, float y, float width, float height, ID id, Main main) {
        super(x, y, width, height, id, main);
    }

    @Override
    public void tick() {
        // No updates needed for a static block
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

}
