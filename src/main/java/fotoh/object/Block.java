package fotoh.object;

import fotoh.Main;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.util.ImageLoader;

import java.awt.*;

public class Block extends GameObject {

    public Block(double x, double y, int w, int h, Main main) {
        super(x, y, w, h, ID.Block, main);
        setEntityImage(ImageLoader.loadImage(getClass().getResource("/brick_block.jpg").getFile()).getScaledInstance(getBounds().getWidth(), getBounds().getHeight(), Image.SCALE_DEFAULT));
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect((int) getBounds().getX(), (int) getBounds().getY(), getBounds().getWidth(), getBounds().getHeight());
    }

    @Override
    public void onCollide(GameObject callback) {
        if(callback.getId() == ID.Player){
            callback.setVelX(0);
        }
    }
}
