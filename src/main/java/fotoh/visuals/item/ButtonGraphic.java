package fotoh.visuals.item;

import fotoh.Main;
import fotoh.listener.ClickType;
import fotoh.listener.Interactable;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

@Getter
public class ButtonGraphic {

    private final int x,y;
    private String text;
    protected Consumer<MouseEvent> consumer;
    private int x1,x2,y1,y2;
    private final Interactable interactable;
    private TextGraphic textGraphic;
    private int width, height;
    private int fontSize;

    public ButtonGraphic(int x, int y, Main main, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        interactable = new Interactable(ClickType.PRESSED, main.getClickListener());
        buttonClick();
        x1 = x;
        y1 = y;
        x2 = x + width;
        y2 = y + height;
        this.width = width;
        this.height = height;
    }

    public ButtonGraphic withText(String text, String font, int fontSize, Color color) {
        this.text = text;
        this.fontSize = fontSize;
        textGraphic = new TextGraphic(text, new Font(font, Font.PLAIN, fontSize), color);
        return this;
    }

    public ButtonGraphic withImage(Image image){

        return this;
    }

    private void buttonClick(){
        interactable.tick(event -> {
            if(event.getX() >= x1 && event.getX() <= x2 && event.getY() >= y1 && event.getY() <= y2){
                if(consumer != null){
                    consumer.accept(event);
                }
            }
        });
    }

    public void onButtonClick(Consumer<MouseEvent> consumer){
        this.consumer = consumer;
    }

    public void render(Graphics graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x1, y1, width, height);
        if(textGraphic != null)
            textGraphic.draw(graphics, (x + width / 2)-((int)(0.3*fontSize) * textGraphic.getText().length()), (y + height / 2)+(int)(0.5*fontSize));
    }
}
