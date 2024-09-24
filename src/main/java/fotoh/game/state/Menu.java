package fotoh.game.state;

import fotoh.Main;
import fotoh.visuals.item.ButtonGraphic;

import java.awt.*;

public class Menu extends GameState{

    public Menu(Main main) {
        super(main);
        graphic = new ButtonGraphic("Random Button", 300 , 200, main.getWindow().getJFrame());
    }

    private final ButtonGraphic graphic;

    @Override
    public GameState onEnable() {
        Main.LOGGER.info("Entered Menu State");
        graphic.onButtonClick(_ -> System.out.println("Button Was Clicked. BIDIBIDI"));
        return this;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(500, 500, 300, 300);
        g.setColor(Color.BLUE);
        g.fillRect(250, 250, 150, 150);
        g.setColor(Color.GREEN);
        g.fillArc(250,250, 400, 400, 0, 360);
        g.setColor(new Color(0,0,0,255));
        g.setFont(new Font("Arial", Font.BOLD, 55));
        g.drawString("RANDOM GAME", 105 , 385);

        g.setFont(Main.DEFAULT_FONT);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void tick(float dt) {
    }
}
