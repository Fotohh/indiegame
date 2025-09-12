package fotoh.util;

import fotoh.Main;
import lombok.Getter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.function.Consumer;

@Getter
public class KeyboardEvent {

    public record Handler(Type type, Consumer<KeyEvent> eventConsumer, UUID uuid) { }

    private final List<Handler> list = new ArrayList<>();

    public KeyboardEvent(Main main) {
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                handleKeyEvent(Type.TYPED, e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyEvent(Type.PRESSED, e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                handleKeyEvent(Type.RELEASED, e);
            }
        });
    }

    private void handleKeyEvent(Type type, KeyEvent e) {
        list.forEach((value) -> {
            if (value.type == type) value.eventConsumer.accept(e);
        });
    }

    public Handler add(Consumer<KeyEvent> consumer, Type type, UUID uuid) {
        Handler handler = new Handler(type, consumer, uuid);
        list.removeIf(entry -> entry.eventConsumer.equals(consumer));
        list.add(handler);
        return handler;
    }

    public void remove(Handler handler) {
        list.remove(handler);
    }

    public void clear(UUID classID) {
        list.removeIf(entry -> entry.uuid.equals(classID));
    }

    public enum Type {
        PRESSED,
        RELEASED,
        TYPED
    }
}
