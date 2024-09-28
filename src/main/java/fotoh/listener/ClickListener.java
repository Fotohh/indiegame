package fotoh.listener;

import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class ClickListener {

    private final LinkedList<Interactable> pressed = new LinkedList<>();
    private final LinkedList<Interactable> released = new LinkedList<>();
    private final LinkedList<Interactable> hover = new LinkedList<>();

    public ClickListener(){

    }

    public void register(Interactable interactable, ClickType type){
        switch (type){
            case HOVER -> hover.add(interactable);
            case PRESSED -> pressed.add(interactable);
            case RELEASED -> released.add(interactable);
        }
    }

    public void tick(MouseEvent event, ClickType clickType){
        switch (clickType) {
            case PRESSED -> {
                for (Interactable interactable : pressed)
                    if (interactable.call != null) interactable.call.accept(event);
            }
            case RELEASED -> {
                for (Interactable interactable : released)
                    if (interactable.call != null) interactable.call.accept(event);
            }
            case HOVER -> {
                for (Interactable interactable : hover)
                    if (interactable.call != null) interactable.call.accept(event);
            }
        }
    }

}
