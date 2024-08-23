package fotoh.util;

import fotoh.Main;
import fotoh.game.GameObject;
import lombok.Getter;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class KeyboardEvent {

    @Getter
    static class Handler {
        private final Type type;
        private final Consumer<KeyEvent> eventConsumer;
        private final GameObject object;
        private final UUID uuid;

        public Handler(Type type, Consumer<KeyEvent> eventConsumer, GameObject object) {
            this.type = type;
            this.eventConsumer = eventConsumer;
            this.uuid = UUID.randomUUID();
            this.object = object;
        }
    }

    private final Map<UUID, Handler> list = new HashMap<>();

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
        list.forEach((_, value) -> {
            if (value.type == type) value.eventConsumer.accept(e);
        });
    }

    public KeyboardEvent add(Consumer<KeyEvent> consumer, Type type, GameObject object) {
        if(!object.isEnabled() || !object.getControllable().isEnabled()) return this;
        Handler handler = new Handler(type, consumer, object);

        for (Map.Entry<UUID, Handler> entry : list.entrySet()) {
            if (entry.getValue().object.equals(object) && entry.getValue().type == type) {
                list.replace(entry.getKey(), handler);
                return this;
            }
        }

        list.putIfAbsent(UUID.randomUUID(), handler);
        return this;
    }

    public void remove(GameObject object) {
        list.forEach((uuid, handler) -> {
            if(handler.object.equals(object)) {
                list.remove(uuid);
            }
        });
    }

    public enum Type {
        PRESSED,
        RELEASED,
        TYPED
    }
}
