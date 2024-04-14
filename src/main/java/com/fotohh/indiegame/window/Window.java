package com.fotohh.indiegame.window;

import com.fotohh.indiegame.Main;

import javax.swing.*;
import java.awt.*;

public class Window {

    public Window(int w, int h, String t, Main main){
        GraphicHandler graphicHandler = new GraphicHandler();
        JFrame jFrame = new JFrame(t);

        jFrame.setPreferredSize(new Dimension(w,h));
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(graphicHandler);
        jFrame.add(main);
        main.start();
    }

}
