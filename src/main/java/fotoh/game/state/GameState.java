package fotoh.game.state;

import fotoh.Main;
import fotoh.visuals.FadeInOut;
import fotoh.visuals.item.TextGraphic;

import java.awt.*;
import java.util.TimerTask;

public abstract class GameState {

    protected final Main main;

    public GameState(Main main) {
        this.main = main;
    }

    public static GameState DEFAULT(Main main) {
        return new GameState(main) {

            private static final TextGraphic graphic = new TextGraphic("Presented By", new Font("Comic-Sans", Font.PLAIN, 12), Color.BLACK);
            private static final TextGraphic graphic2 = new TextGraphic("Xaxis Studios", new Font("Comic-Sans", Font.PLAIN, 75), Color.BLACK);

            @Override
            public GameState onEnable() {
                graphic.addAnimation(new FadeInOut(3 * 1000, 3 * 1000, 3 * 1000, graphic));
                graphic2.addAnimation(new FadeInOut(3 * 1000, 3 * 1000, 3 * 1000, graphic2));
                Main.LOGGER.info("GameState enabled!");
                main.getTimer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        main.setState(new Menu(main));
                    }
                }, 1000 * 10);

                return this;
            }

            @Override
            public void render(Graphics g) {
                graphic.draw(g.create(), 500, 350);
                graphic2.draw(g.create(), 300, 250);
            }

            @Override
            public void onDisable() {
                Main.LOGGER.info("GameState shutting down!");
            }

            @Override
            public void tick(float dt) {
            }

        };
    }

    public abstract GameState onEnable();

    public abstract void render(Graphics g);

    public abstract void onDisable();

    public abstract void tick(float dt);

}
