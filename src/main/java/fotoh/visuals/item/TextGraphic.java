package fotoh.visuals.item;

import fotoh.visuals.Animation;
import fotoh.visuals.GraphicItem;
import lombok.Setter;

import java.awt.*;

public class TextGraphic extends GraphicItem {

    private final String textToDraw;
    private final Font font;
    @Setter
    private Color color;

    public TextGraphic(String textToDraw, Font font, Color color){
        this.textToDraw = textToDraw;
        this.font = font;
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void draw(Graphics graphics, int x, int y) {
        graphics.setColor(getColor());
        graphics.setFont(font);
        graphics.drawString(textToDraw, x, y);
        if(animations.peek() != null){
            Animation anim = animations.poll();
            anim.run();
        }
    }

    public String getText() {
        return textToDraw;
    }
}
