package fotoh.game;

import fotoh.Main;
import fotoh.util.KeyboardEvent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class GameObject {

    protected double x, y, velX, velY;

    protected int width, height;

    private final KeyboardEvent event;

    protected final ID id;

    protected final Main main;

    public GameObject(double x, double y, int w, int h, ID id, Main main) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.main = main;
        this.width = w;
        this.height = h;
        this.event = main.getWindow().getEvent();
        initializeControls();
        main.getHandler().addObject(this);
    }

    protected abstract void initializeControls();

    public abstract void tick();

    public abstract void render(Graphics g);

    public void delete() {
        main.getHandler().removeObject(this);
    }

}
