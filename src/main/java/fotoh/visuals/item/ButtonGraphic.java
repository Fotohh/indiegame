package fotoh.visuals.item;

import fotoh.Main;
import fotoh.listener.ClickType;
import fotoh.listener.Interactable;
import lombok.Getter;

import java.awt.*;
import java.util.function.Consumer;

@Getter
public class ButtonGraphic {

    private final int x,y;
    private final String text;
    protected Consumer<ButtonGraphic> consumer;
    private int x1,x2,y1,y2;
    private final Interactable interactable;
    private int width, height;
    private final int size;

    public ButtonGraphic(String text, int x, int y, Main main, int width, int height, int size){
        this.text = text;
        this.x = x;
        this.y = y;
        interactable = new Interactable(ClickType.PRESSED, main.getClickListener());
        buttonClick();

        x1 = x;
        y1 = y;
        x2 = x + width;
        y2 = y + height;
        this.size = size;
        this.width = width;
        this.height = height;
    }



    private void buttonClick(){
        interactable.tick(event -> {
            if(event.getX() >= x1 && event.getX() <= x2 && event.getY() >= y1 && event.getY() <= y2){
                if(consumer != null){
                    consumer.accept(this);
                }
            }
        });
    }

    public void onButtonClick(Consumer<ButtonGraphic> consumer){
        this.consumer = consumer;
    }

    public void render(Graphics graphics) {
        TextGraphic textGraphic = new TextGraphic(text, new Font("Arial", Font.PLAIN, size), Color.WHITE);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x1, y1, width, height);
        textGraphic.draw(graphics, (x + width / 2)-((int)(0.3*size) * textGraphic.getText().length()), (y + height / 2)-size);
    }
}
