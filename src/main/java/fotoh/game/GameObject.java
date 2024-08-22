package fotoh.game;

import fotoh.Main;
import fotoh.handler.Collider;
import fotoh.handler.ObjectBounds;
import fotoh.util.ImageLoader;
import fotoh.util.KeyboardEvent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.UUID;

@Getter
@Setter
public abstract class GameObject extends Collider {

    protected double velX, velY;

    private final KeyboardEvent event;

    private final UUID objectUUID;

    private boolean isVisible = true;

    protected boolean enabled = true;

    private Image entityImage;

    protected final ID id;

    protected final Main main;

    public GameObject(double x, double y, int w, int h, ID id, Main main) {
        super(new ObjectBounds(x,y,w,h));
        objectUUID = UUID.randomUUID();
        this.id = id;
        this.main = main;
        this.event = main.getEvent();
        main.getCollisionManager().register(this);
        main.getHandler().addObject(this, enabled);
        initializeControls();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        main.getHandler().replace(this, enabled);
    }

    public abstract void tick();

    public void initializeControls(){}

    public abstract void render(Graphics g);

    public void delete() {
        main.getHandler().removeObject(this);
    }

    public void resize(int width, int height) {
        getBounds().resize(width, height);
        setEntityImage(getEntityImage().getScaledInstance(getBounds().getWidth(), getBounds().getHeight(), Image.SCALE_DEFAULT));
    }

}
