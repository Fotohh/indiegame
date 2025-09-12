package fotoh.game.state;

import fotoh.Main;
import fotoh.game.ID;
import fotoh.objects.Block;
import fotoh.player.Player;
import lombok.Getter;

import java.awt.*;

public class Active extends GameState {

    @Getter
    private final Player player;
    private final Block block;

    public Active(Main main) {
        super(main);
        player = new Player(200, 300, 50, 50, main);
        block = new Block(200, 200, 50, 50, ID.Block, main);
    }

    @Override
    public GameState onEnable() {
        Main.LOGGER.info("Active state enabled");
        return this;
    }

    @Override
    public void render(Graphics g) {
        block.render(g);

        player.render(g); // goes last to be on top
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void tick(float dt) {
        block.tick(dt);

        player.tick(dt); // goes last to be on top
    }
}
