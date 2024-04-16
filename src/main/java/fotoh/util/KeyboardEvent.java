package fotoh.util;

import java.awt.event.KeyEvent;

public class KeyboardEvent {

    private KeyEvent event;

    private Type type;

    enum Type{
        PRESSED,
        RELEASED,
        TYPED
    }
}
