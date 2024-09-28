package fotoh.visuals.item;

import fotoh.Main;
import fotoh.listener.ClickType;
import fotoh.listener.Interactable;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class ButtonGraphic {

    private final int x,y;
    private final String text;
    protected Consumer<ButtonGraphic> consumer;
    private int x1,x2,y1,y2;
    private final Interactable interactable;

    //figure out where the x, and y, are drawn, so I can calculate the vertices of the box therefore calculating whether the event should be called in
    //click listener or not
    public ButtonGraphic(String text, int x, int y, Main main, int width, int height){
        this.text = text;
        this.x = x;
        this.y = y;
        interactable = new Interactable(ClickType.PRESSED, main.getClickListener());
        buttonClick();

        x1 = x;
        y1 = y;
    }

    private void buttonClick(){
        interactable.tick(event -> {

        });
    }

    public void onButtonClick(Consumer<ButtonGraphic> consumer){
        this.consumer = consumer;
    }

}
