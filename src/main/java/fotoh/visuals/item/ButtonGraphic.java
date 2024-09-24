package fotoh.visuals.item;

import lombok.Getter;

import javax.swing.*;
import java.util.function.Consumer;

@Getter
public class ButtonGraphic {

    private final int x,y;
    private final String text;
    protected Consumer<ButtonGraphic> consumer;
    private final JButton button;
    private final ButtonGraphic instance;

    /*

    Might just make my own button since the JFrame instance is a little weird, I can eventually switch raylib ( idk if it exists)
    Will fix this eventually at some point.
    todo Doesn't work...
     */

    public ButtonGraphic(String text, int x, int y, JFrame frame){
        this.text = text;
        this.instance = this;
        this.x = x;
        this.y = y;
        button = new JButton(text);
        button.setLocation(x,y);
        buttonClick();
        frame.add(button);
        button.setVisible(true);
    }

    private void buttonClick(){
        button.addActionListener(_ -> consumer.accept(instance));
    }

    public void onButtonClick(Consumer<ButtonGraphic> consumer){
        this.consumer = consumer;
    }

}
