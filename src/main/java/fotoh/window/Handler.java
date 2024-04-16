package fotoh.window;

import fotoh.game.GameObject;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Handler {

    LinkedHashMap<GameObject, Boolean> object = new LinkedHashMap<>();

    public void tick() {
        object.forEach((key, value) -> {
            if (value) key.tick();
        });
    }

    public synchronized void render(Graphics g) {
        object.forEach((key, value) -> {
            if (value) key.render(g);
        });
    }

    public void addObject(GameObject object, boolean enabled) {
        this.object.put(object, enabled);
    }

    public void replace(GameObject object, boolean val) {
        this.object.replace(object, val);
        if (val) object.initializeControls();
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

}
