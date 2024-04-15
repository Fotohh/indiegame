package com.fotohh.indiegame.player;

import com.fotohh.indiegame.Main;
import com.fotohh.indiegame.game.GameObject;
import com.fotohh.indiegame.game.ID;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Player extends GameObject {

    public Player(int x, int y, Main main) {
        super(x, y, ID.Player, main);
        initializeControls();
    }

    @Override
    public void tick() {
        if(x >= 0 || x <= main.getWidth()) x+=velX; //TODO fix
    }

    private void initializeControls(){
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_D -> setVelX(5);
                    case KeyEvent.VK_A -> setVelX(-5);
                }
            }
        });
        main.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()){
                    case KeyEvent.VK_D, KeyEvent.VK_A -> setVelX(0);
                }
            }
        });
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x,y, 50,50);
    }
}
