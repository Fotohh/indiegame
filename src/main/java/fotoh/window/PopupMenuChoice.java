package fotoh.window;

import fotoh.Main;
import fotoh.visuals.item.ButtonGraphic;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

@Getter
public class PopupMenuChoice {

    private final Consumer<MouseEvent> callback;
    private final int slot;
    private final PopupMenu menu;
    private final Main main;
    private final Color color;
    private final String text;
    private ButtonGraphic button;

    public PopupMenuChoice(Main main, PopupMenu menu, Color color, String text, Consumer<MouseEvent> call) {
        this.callback = call;
        this.menu = menu;
        this.main = main;
        this.color = color;
        this.text = text;
        menu.addOption(this);
        slot = menu.getOptions().size();
    }

    public PopupMenuChoice register() {
        int optionsLen = menu.getOptions().size();
        int optionHeight = menu.getHeight() / optionsLen;
        int optionWidth = menu.getWidth() / optionsLen;
        int x = (menu.getX() + ((menu.getWidth() / optionsLen) * slot));
        int y = (menu.getY() + (menu.getHeight() / 4));
        button = new ButtonGraphic(x, y, main, optionWidth, optionHeight, Color.WHITE);
        button.onButtonClick(callback);
        button.withText(text, Main.DEFAULT_FONT.getFontName(), 20, color);
        return this;
    }

}
