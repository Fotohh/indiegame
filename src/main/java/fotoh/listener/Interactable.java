package fotoh.listener;

import lombok.Getter;
import lombok.Setter;

import java.awt.event.MouseEvent;
import java.util.UUID;
import java.util.function.Consumer;

public class Interactable {

    private final UUID uuid = UUID.randomUUID();

    @Getter
    @Setter
    private boolean enabled = true;

    public Interactable(ClickType type, ClickListener clickListener) {
        clickListener.register(this, type);
    }

    protected Consumer<MouseEvent> callback;

    public void tick(Consumer<MouseEvent> consumer){
        this.callback = consumer;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Interactable that = (Interactable) o;
        return that.uuid.equals(this.uuid);
    }
}
