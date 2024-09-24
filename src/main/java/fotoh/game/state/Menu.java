package fotoh.game.state;

import fotoh.Main;

import java.awt.*;

public class Menu extends GameState{

    public Menu(Main main) {
        super(main);
    }

    @Override
    public void onEnable() {
        Main.LOGGER.info("Entered Menu State");
    }

    int startAngle = 200;
    int endAngle = 700;
    int alpha  = 100;

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(500, 500, 300, 300);
        g.setColor(Color.BLUE);
        g.fillRect(250, 250, 150, 150);
        g.setColor(Color.GREEN);
        g.fillArc(250,250, 400, 400, startAngle, endAngle);
        g.setColor(new Color(0,0,0,alpha));
        g.setFont(new Font("Arial", Font.BOLD, 55));
        g.drawString("RANDOM GAME", 105 , 385);
        g.setFont(Main.DEFAULT_FONT);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void tick(float dt) {
        if (alpha > 1) alpha--;
        startAngle -= (int) (startAngle / (dt * .5));
        endAngle += (int) (dt - (float) startAngle / endAngle*2);
    }
}
