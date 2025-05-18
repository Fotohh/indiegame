package fotoh.game.state;

import fotoh.Main;
import fotoh.game.ID;
import fotoh.objects.Block;
import fotoh.player.Player;
import fotoh.visuals.item.ButtonGraphic;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.logging.Level;

public class Menu extends GameState{

    public Menu(Main main) {
        super(main);
        graphic = new ButtonGraphic(300 , 100, main, 200, 100, Color.BLACK)
                .withText("PLAY", "Arial", 20, Color.WHITE);
        graphic.onButtonClick(_ -> System.out.println("Button Was Clicked. BIDIBIDI"));
        settings = new ButtonGraphic(300 , 250, main, 200, 100, Color.BLACK)
                .withText("SETTINGS", "Arial", 20, Color.WHITE);
        settings.onButtonClick(_ -> System.out.println("Button Was Clicked. SETTINGS"));
        quit = new ButtonGraphic(300 , 400, main, 200, 100, Color.BLACK)
                .withText("QUIT", "Arial", 20, Color.WHITE);
        quit.onButtonClick(_ -> main.onDisable());
    }

    private final ButtonGraphic graphic;
    private final ButtonGraphic settings;
    private final ButtonGraphic quit;

    @Override
    public GameState onEnable() {
        Main.LOGGER.info("Entered Menu State");

        new Player(200, 300, 50, 50, main);
        new Block(200, 200, 50, 50, ID.Block, main);
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
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 55));
        g.drawString("RANDOM GAME", 105 , 385);
        graphic.render(g);
        settings.render(g);
        quit.render(g);

        g.setFont(Main.DEFAULT_FONT);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void tick(float dt) {
    }
}
