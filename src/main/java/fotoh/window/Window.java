package fotoh.window;

import fotoh.Main;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class Window {

    private final JFrame jFrame;

    public Window(int w, int h, String t, Main main){
        jFrame = new JFrame(t);

        jFrame.setPreferredSize(new Dimension(w,h));
        jFrame.setMaximumSize(new Dimension(w,h));
        jFrame.setMinimumSize(new Dimension(w,h));
        jFrame.pack();
        jFrame.add(main);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

}
