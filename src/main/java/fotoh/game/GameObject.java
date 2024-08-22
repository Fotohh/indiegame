package fotoh.game;

import fotoh.Main;
import fotoh.handler.Collider;
import fotoh.util.KeyboardEvent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.UUID;
import java.util.Vector;

@Getter
@Setter
public abstract class GameObject {

    protected float velX, velY, x, y, width, height;

    private final KeyboardEvent event;

    private final Collider collider = new Collider();

    private final UUID objectUUID;

    private boolean isVisible = true;

    protected boolean enabled = true;

    private Image entityImage;

    protected final ID id;

    protected final Main main;

    public GameObject(float x, float y, float w, float h, ID id, Main main) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
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

    public void resize(float width, float height) {
        setEntityImage(getEntityImage().getScaledInstance((int) width, (int) height, Image.SCALE_DEFAULT));
    }

}
