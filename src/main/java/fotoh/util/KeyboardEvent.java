package fotoh.util;

import fotoh.Main;
import fotoh.game.GameObject;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class KeyboardEvent {

    private final Map<Type, Consumer<KeyEvent>> list = new HashMap<>();

    public KeyboardEvent(Main main) {
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                list.forEach((key, value) -> {
                    if (key == Type.TYPED) value.accept(e);
                });
            }
        });
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                list.forEach((key, value) -> {
                    if (key == Type.PRESSED) value.accept(e);
                });
            }
        });
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                list.forEach((key, value) -> {
                    if (key == Type.RELEASED) value.accept(e);
                });
            }
        });
    }

    public KeyboardEvent add(Consumer<KeyEvent> consumer, Type type, GameObject object) {
        if(!object.isEnabled()) return this;
        list.put(type, consumer);
        return this;
    }

    public enum Type {
        PRESSED,
        RELEASED,
        TYPED
    }
}
