package fotoh.window;

import fotoh.Main;
import fotoh.listener.ClickListener;
import fotoh.listener.ClickType;
import fotoh.util.KeyboardEvent;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
        jFrame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                main.getClickListener().tick(e, ClickType.PRESSED);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                main.getClickListener().tick(e, ClickType.RELEASED);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                main.getClickListener().tick(e, ClickType.HOVER);
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
