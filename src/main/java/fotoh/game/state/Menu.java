package fotoh.game.state;

import fotoh.Main;
import fotoh.visuals.item.ButtonGraphic;
import fotoh.window.PopupMenu;
import fotoh.window.PopupMenuChoice;

import java.awt.*;

public class Menu extends GameState {

    private final PopupMenu menu;

    public Menu(Main main) {
        super(main);
        menu = new PopupMenu(200, 200, "Are you sure?", main, Color.BLUE);
        new PopupMenuChoice(main, menu, Color.RED, "Yes", _ -> {
            this.onDisable();
            main.onDisable();
            System.out.println("disabled");
        });
        new PopupMenuChoice(main, menu, Color.GREEN, "No", _ -> {
            menu.setVisible(false);
            menu.disable();
            System.out.println("cancelled");
        });
        menu.getOptions().forEach(PopupMenuChoice::register);
        menu.disable();

        graphic = new ButtonGraphic(300 , 100, main, 200, 100, Color.BLACK)
                .withText("PLAY", "Arial", 20, Color.WHITE);
        graphic.onButtonClick(_ -> {
            System.out.println("Button Was Clicked. BIDIBIDI");
            main.setState(new Active(main));
        });
        settings = new ButtonGraphic(300 , 250, main, 200, 100, Color.BLACK)
                .withText("SETTINGS", "Arial", 20, Color.WHITE);
        settings.onButtonClick(_ -> System.out.println("Button Was Clicked. SETTINGS"));
        quit = new ButtonGraphic(300 , 400, main, 200, 100, Color.BLACK)
                .withText("QUIT", "Arial", 20, Color.WHITE);
        quit.onButtonClick(_ -> {
            menu.enable();
        });
    }

    private final ButtonGraphic graphic;
    private final ButtonGraphic settings;
    private final ButtonGraphic quit;

    @Override
    public GameState onEnable() {
        Main.LOGGER.info("Entered Menu State");
        return this;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(500, 500, 300, 300);
        g.setColor(Color.BLUE);
        g.fillRect(250, 250, 150, 150);
        g.setColor(Color.GREEN);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 55));
        g.drawString("RANDOM GAME", 105 , 385);
        graphic.render(g);
        settings.render(g);
        quit.render(g);
        if(menu != null && menu.isVisible()) menu.render(g);
        g.setFont(Main.DEFAULT_FONT);
    }

    @Override
    public void onDisable() {
        graphic.disable();
        settings.disable();
        quit.disable();
        menu.disable();
    }

    @Override
    public void tick(float dt) {
    }
}
