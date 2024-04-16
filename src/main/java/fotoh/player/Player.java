package fotoh.player;

import fotoh.Main;
import fotoh.game.GameObject;
import fotoh.game.ID;
import fotoh.util.ImageLoader;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    public Player(double x, double y, int width, int height, Main main) {
        super(x, y, width, height, ID.Player, main);
        initializeControls();
    }

    @Override
    protected void tick() {
        if(d_down && a_down) velX = 0;
        else if(x >= main.getWidth() - width && d_down) velX = 0;
        else if(x <= 0 && a_down) velX = 0;
        else if(d_down) setVelX(5);
        else if (a_down) setVelX(-5);

        velY += 1;
        y+=velY;

        if(y >= 920 - height){
            y = 920 - height;
            velY = 0;
            onGround = true;
        }

        x+=velX;
    }

    private boolean onGround = true;
    private boolean d_down = false;
    private boolean a_down = false;

    protected void initializeControls(){
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_D -> d_down = true;
                    case KeyEvent.VK_A -> a_down = true;
                    case KeyEvent.VK_W -> {
                        if(onGround){
                        velY = -18;
                        onGround = false;
                        }
                    }
                }
            }
        });
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_D -> {
                        setVelX(0);
                        d_down = false;
                    }
                    case KeyEvent.VK_A -> {
                        setVelX(0);
                        a_down = false;
                    }
                    case KeyEvent.VK_W -> {
                        if(velY < -6.0) velY = -6;
                    }

                }
            }
        });
    }

    @Override
    protected void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawImage(ImageLoader.loadImage("C:\\Users\\zinha\\Documents\\Coding\\Python\\indiegame\\src\\main\\java\\fotoh\\data\\person.png"), (int) x, (int) y, main.getWindow().getJFrame());
    }
}
