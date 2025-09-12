package fotoh.visuals.item;

import fotoh.Main;
import fotoh.listener.ClickType;
import fotoh.listener.Interactable;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

@Getter
public class ButtonGraphic {

    private final Main main;
    private final int x1, x2, y1, y2;
    private final Interactable interactable;
    protected Consumer<MouseEvent> consumer;
    @Setter
    private int x, y;
    private String text;
    private TextGraphic textGraphic;
    @Setter
    private int width, height;
    @Getter
    private boolean enabled = true;
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
        this.main = main;
        this.width = width;
        this.height = height;
        setEnabled(true);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        interactable.setEnabled(enabled);
    }

    public ButtonGraphic withText(String text, String font, int fontSize, Color color) {
        this.text = text;
        this.fontSize = fontSize;
        textGraphic = new TextGraphic(text, new Font(font, Font.PLAIN, fontSize), color);
        return this;
    }

    public ButtonGraphic withImage(Image image) {

        return this;
    }

    private void buttonClick() {
        interactable.tick(event -> {
            if (event.getX() >= x1 && event.getX() <= x2 && event.getY() >= y1 && event.getY() <= y2) {
                if (consumer != null) {
                    consumer.accept(event);
                }
            }
        });
    }

    public void onButtonClick(Consumer<MouseEvent> consumer) {
        this.consumer = consumer;
    }

    public void render(Graphics graphics) {
        if (!enabled) return;
        graphics.setColor(Color.BLACK);
        graphics.fillRect(x1, y1, width, height);
        if (textGraphic != null)
            textGraphic.draw(graphics, (x + width / 2) - ((int) (0.3 * fontSize) * textGraphic.getText().length()), (y + height / 2) + (int) (0.5 * fontSize));
    }

}
