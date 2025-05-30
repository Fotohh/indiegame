package fotoh.game;

import fotoh.Main;
import fotoh.handler.Collider;
import fotoh.util.KeyboardEvent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public abstract class GameObject {

    protected float velX, velY, x, y, width, height;
    protected final Gravity gravity = new Gravity(this);
    protected final Controllable controllable;
    private final KeyboardEvent event;
    private final Collider collider = new Collider();
    private final UUID objectUUID;
    private boolean isVisible = true;
    private boolean enabled = true;
    protected Image entityImage;
    protected final ID id;
    protected final Main main;
    protected boolean w_down = false;

    public GameObject(float x, float y, float w, float h, ID id, Main main) {
        controllable = new Controllable(this);
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.objectUUID = UUID.randomUUID();
        this.id = id;
        this.main = main;
        this.event = main.getEvent();
        if(collider.isEnabled()) main.getCollisionManager().register(this);
        main.getGameObjects().add(this);

        initializeControls();
    }

    public float[][] getAxes() {
        float[] vertices = getVertices();
        float[][] axes = new float[vertices.length / 2][2];
        for (int i = 0; i < 4; i++) {
            float x1 = vertices[i * 2];
            float y1 = vertices[i * 2 + 1];
            float x2 = vertices[(i * 2 + 2) % vertices.length];
            float y2 = vertices[(i * 2 + 3) % vertices.length];

            float dx = x2 - x1;
            float dy = y2 - y1;

            axes[i][0] = dy;
            axes[i][1] = -dx;

            float length = (float) Math.sqrt(axes[i][0] * axes[i][0] + axes[i][1] * axes[i][1]);
            axes[i][0] /= length;
            axes[i][1] /= length;
        }
        return axes;
    }

    public float[] getVertices() {
        float halfWidth = width / 2;
        float halfHeight = height / 2;

        return new float[]{
                x - halfWidth, y - halfHeight,
                x + halfWidth, y - halfHeight,
                x + halfWidth, y + halfHeight,
                x - halfWidth, y + halfHeight
        };
    }

    protected void handleMovement(float dt) {}

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public abstract void tick(float dt);

    public void initializeControls() {}

    protected abstract void handleCollision(GameObject other, Collider.CollisionDirection collisionDirection);

    public abstract void render(Graphics g);

    public void delete() {

    }

    public void resize(float width, float height) {
        setEntityImage(getEntityImage().getScaledInstance((int) width, (int) height, Image.SCALE_DEFAULT));
    }
}
