package fotoh.game;

import fotoh.Main;
import fotoh.handler.Collider;
import fotoh.handler.ObjectBounds;
import fotoh.util.KeyboardEvent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class GameObject extends Collider {

    protected double velX, velY;

    private final KeyboardEvent event;

    private boolean isActive;

    private boolean isVisible;

    protected boolean enabled = false;

    private Image entityImage;

    protected final ID id;

    protected final Main main;

    private Collider collider;

    public GameObject(double x, double y, int w, int h, ID id, Main main) {
        super(new ObjectBounds(x,y,w,h));
        this.id = id;
        this.main = main;
        this.event = main.getEvent();
        initializeControls();
        main.getHandler().addObject(this, enabled);
        main.getCollisionManager().register(this);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        main.getHandler().replace(this, enabled);
    }

    public void initializeControls(){}

    public abstract void tick();

    public abstract void render(Graphics g);

    public void delete() {
        main.getHandler().removeObject(this);
    }

    public abstract void resize(int width, int height);

}
