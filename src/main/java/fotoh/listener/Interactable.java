package fotoh.listener;

import java.awt.event.MouseEvent;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;

public class Interactable {

    private final UUID uuid = UUID.randomUUID();

    public Interactable(ClickType type, ClickListener clickListener){
        clickListener.register(this, type);
    }

    protected Consumer<MouseEvent> call;

    public void tick(Consumer<MouseEvent> consumer){
        this.call = consumer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Interactable that = (Interactable) o;
        return that.uuid.equals(this.uuid);
    }
}
