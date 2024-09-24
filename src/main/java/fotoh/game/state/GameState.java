package fotoh.game.state;

import fotoh.Main;
import fotoh.visuals.FadeInOut;
import fotoh.visuals.item.TextGraphic;

import java.awt.*;
import java.util.TimerTask;

public abstract class GameState {

    protected final Main main;

    public GameState(Main main){
        this.main = main;
    }

    public static GameState DEFAULT(Main main){
        return new GameState(main) {
            @Override
            public GameState onEnable() {
                Main.LOGGER.info("GameState enabled!");
                main.getTimer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        main.setState(new Menu(main).onEnable());
                    }
                }, 1000 * 8);
                graphic.addAnimation(new FadeInOut(3*1000,3*1000,3*1000, graphic));
                return this;
            }

            private static final TextGraphic graphic = new TextGraphic("Presented By", new Font("Comic-Sans", Font.PLAIN, 75));

            @Override
            public void render(Graphics g) {
                g.setColor(Color.BLACK);
                graphic.draw(g.create(), 300, 250);
                g.drawString("Xaxis Studios", 500, 350);
                g.setFont(Main.DEFAULT_FONT);
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
