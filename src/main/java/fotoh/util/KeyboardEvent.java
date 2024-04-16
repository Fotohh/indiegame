package fotoh.util;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class KeyboardEvent {

    protected final JFrame jFrame;

    Map<Type, Consumer<Void>> list = new HashMap<>();

    public KeyboardEvent(JFrame jFrame){
        this.jFrame = jFrame;

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                list.forEach((key, value) -> {
                    if (key == Type.TYPED) value.accept(null);
                });
            }

            @Override
            public void keyPressed(KeyEvent e) {
                list.forEach((key, value) -> {
                    if (key == Type.PRESSED) value.accept(null);
                });
            }

            @Override
            public void keyReleased(KeyEvent e) {
                list.forEach((key, value) -> {
                    if (key == Type.RELEASED) value.accept(null);
                });
            }
        });
    }

    public KeyboardEvent add(Consumer<Void> consumer, Type type){
        list.put(type, consumer);
        return this;
    }

    public enum Type{
        PRESSED,
        RELEASED,
        TYPED
    }
}
