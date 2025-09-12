package fotoh.game.state;

import fotoh.Main;
import fotoh.game.ID;
import fotoh.objects.Block;
import fotoh.player.Player;
import fotoh.util.KeyboardEvent;
import lombok.Getter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.UUID;

public class Active extends GameState {

    private final UUID CLASS_ID = UUID.randomUUID();

    @Getter
    private final Player player;
    private final Block block;
    private boolean paused;
    private final KeyboardEvent.Handler pauseKey;

    public Active(Main main) {
        super(main);
        player = new Player(200, 300, 50, 50, main);
        block = new Block(200, 200, 50, 50, ID.Block, main);
        pauseKey = main.getKeyboardEvent().add(keyEvent -> {
            if(keyEvent.getKeyCode() == KeyEvent.VK_ESCAPE) {
                paused = !paused;
            }
        }, KeyboardEvent.Type.RELEASED, CLASS_ID);
    }

    @Override
    public GameState onEnable() {
        Main.LOGGER.info("Active state enabled");
        return this;
    }

    @Override
    public void render(Graphics g) {

        if(paused) {
            g.setColor(Color.black);
            g.drawString("Paused", 100, 100);
        }
        block.render(g);

        player.render(g); // goes last to be on top
    }

    @Override
    public void onDisable() {
        main.getKeyboardEvent().clear(CLASS_ID);
    }

    @Override
    public void tick(float dt) {
        if(paused) return;
        block.tick(dt);

        player.tick(dt); // goes last to be on top
    }
}
