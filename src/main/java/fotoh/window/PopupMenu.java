package fotoh.window;

import fotoh.Main;
import fotoh.visuals.item.ButtonGraphic;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopupMenu {

    @Getter
    private final int width, height;
    @Setter
    @Getter
    private int x, y;
    @Setter
    @Getter
    private String title;
    @Getter
    @Setter
    private boolean visible = true;
    private Color background;
    @Getter
    private boolean enabled = true;

    private final Main main;
    @Getter
    private final List<PopupMenuChoice> options = new ArrayList<>();

    public PopupMenu(int width, int height, String title, Main main, Color color) {
        this.width = width;
        this.height = height;
        this.title = title;
        this.main = main;
        this.x = (Main.WIDTH / 2);
        this.y = (Main.HEIGHT / 2);
        this.background = color;
    }

    public void addOption(PopupMenuChoice option) {
        options.add(option);
    }

    public void render(Graphics g) {
        if(!enabled || !visible) return;
        g.setColor(background);
        g.fillRect(x, y, width, height);
        for(PopupMenuChoice choice : options) {
            ButtonGraphic option = choice.getButton();
            if(option == null) continue;
            if(option.isEnabled()) option.render(g);
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        this.visible = enabled;
        options.forEach(option -> {
            if (option.getButton() != null) {
                option.getButton().setEnabled(enabled);
            }
        });
    }
}
