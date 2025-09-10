package fotoh.listener;

import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ClickListener {

    private final ConcurrentLinkedQueue<Interactable> pressed = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Interactable> released = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Interactable> hover = new ConcurrentLinkedQueue<>();

    public void register(Interactable interactable, ClickType type){
        switch (type){
            case HOVER -> hover.add(interactable);
            case PRESSED -> pressed.add(interactable);
            case RELEASED -> released.add(interactable);
        }
    }

    private synchronized void iterate(ConcurrentLinkedQueue<Interactable> list, MouseEvent event){
        for(Interactable interactable : list) {
            if(interactable.call != null) interactable.call.accept(event);
        }
    }

    public synchronized void tick(MouseEvent event, ClickType clickType){
        switch (clickType) {
            case PRESSED -> iterate(pressed, event);
            case RELEASED -> iterate(released, event);
            case HOVER -> iterate(hover, event);
        }
    }

    public void remove(Interactable interactable, ClickType clickType) {
        switch (clickType){
            case HOVER -> hover.removeIf(interactable::equals);
            case PRESSED -> pressed.removeIf(interactable::equals);
            case RELEASED -> released.removeIf(interactable::equals);
        }
    }
}

