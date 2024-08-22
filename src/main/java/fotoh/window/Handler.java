package fotoh.window;

import fotoh.game.GameObject;

import java.awt.*;
import java.util.concurrent.ConcurrentHashMap;

public class Handler {

    private final ConcurrentHashMap<GameObject, Boolean> object = new ConcurrentHashMap<>();

    public synchronized void tick() {
        object.forEach((key, value) -> {
            if (value) key.tick();
        });
    }

    public synchronized void render(Graphics g) {
        object.forEach((key, value) -> {
            if (value && key.isVisible()) key.render(g);
        });
    }

    public void addObject(GameObject object, boolean enabled) {
        this.object.put(object, enabled);
    }

    public void replace(GameObject object, boolean val) {
        this.object.replace(object, val);
        if (val) {
            object.initializeControls();
        }
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

}
