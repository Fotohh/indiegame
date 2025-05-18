package fotoh.window;

import fotoh.Main;

import javax.swing.*;
import java.awt.event.*;

public class Window {
    private final JFrame jFrame;

    public Window(int width, int height, String title, Main main) {
        jFrame = new JFrame(title);
        jFrame.setSize(width, height);
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                main.onDisable();
            }
        });
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.add(main);

    }

    public JFrame getJFrame() {
        return jFrame;
    }
}
