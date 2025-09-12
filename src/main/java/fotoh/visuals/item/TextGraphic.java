package fotoh.visuals.item;

import fotoh.visuals.Animation;
import fotoh.visuals.GraphicItem;

import java.awt.*;

public class TextGraphic extends GraphicItem {

    private final String textToDraw;
    private final Font font;

    public TextGraphic(String textToDraw, Font font, Color color) {
        this.textToDraw = textToDraw;
        this.font = font;
        r = color.getRed();
        g = color.getGreen();
        b = color.getBlue();
        alpha = color.getAlpha();
    }

    @Override
    public void draw(Graphics graphics, int x, int y) {
        graphics.setColor(getColor());
        graphics.setFont(font);
        graphics.drawString(textToDraw, x, y);
        if (animations.peek() != null) {
            System.out.println("Animation peek");
            Animation anim = animations.poll();
            anim.run();
        }
    }

    public String getText() {
        return textToDraw;
    }
}
