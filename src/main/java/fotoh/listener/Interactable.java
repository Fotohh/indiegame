package fotoh.listener;

import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class Interactable {

    public Interactable(ClickType type, ClickListener clickListener){
        clickListener.register(this, type);
    }

    protected Consumer<MouseEvent> call;

    public void tick(Consumer<MouseEvent> consumer){
        this.call = consumer;
    }

}
