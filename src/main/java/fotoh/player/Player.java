package fotoh.player;

import fotoh.Main;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.util.ImageLoader;
import fotoh.util.KeyboardEvent;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    public Player(double x, double y, int width, int height, Main main) {
        super(x, y, width, height, ID.Player, main);
        setEnabled(true);
        setEntityImage(ImageLoader.loadImage(getClass().getResource("person.png").getFile()).getScaledInstance(getBounds().getWidth(), getBounds().getHeight(), Image.SCALE_DEFAULT));
    }

    @Override
    public void tick() {
        if (d_down && a_down) velX = 0;
        else if (getBounds().getX() >= main.getWidth() - getBounds().getWidth() && d_down) velX = 0;
        else if (getBounds().getX() <= 0 && a_down) velX = 0;
        else if (d_down) setVelX(5);
        else if (a_down) setVelX(-5);

        velY += 1;
        getBounds().getAndAddY(velY);

        if (getBounds().getY() >= 920 - getBounds().getHeight()) velY = 0; {
            getBounds().setY(920 - getBounds().getHeight());
            velY = 0;
            onGround = true;
        }

        getBounds().getAndAddX(velX);
    }

    private boolean onGround = true;
    private boolean d_down = false;
    private boolean a_down = false;


    public void initializeControls() {
        getEvent().add(event -> {
                    switch (event.getKeyCode()) {
                        case KeyEvent.VK_D -> d_down = true;
                        case KeyEvent.VK_A -> a_down = true;
                        case KeyEvent.VK_W -> {
                            if (onGround) {
                                velY = -18;
                                onGround = false;
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
                        case KeyEvent.VK_W -> {
                            if (velY < -6.0) velY = -6;
                        }
                    }
                }, KeyboardEvent.Type.RELEASED, this);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(getEntityImage(), (int) getBounds().getX(), (int) getBounds().getY(), main.getWindow().getJFrame());
    }

    @Override
    public void resize(int width, int height) {
        getBounds().resize(width, height);
        setEntityImage(ImageLoader.loadImage(getClass().getResource("person.png").getFile()).getScaledInstance(getBounds().getWidth(), getBounds().getHeight(), Image.SCALE_DEFAULT));
    }

    @Override
    public void onCollide(GameObject callback) {
        System.out.println("Type: " + callback.getId() + " COLLIDED with " + getId());
    }
}
