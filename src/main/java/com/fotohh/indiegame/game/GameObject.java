package com.fotohh.indiegame.game;

import com.fotohh.indiegame.Main;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class GameObject {

    protected int x,y,velX, velY;
    protected final ID id;
    protected final Main main;

    public GameObject(int x, int y, ID id, Main main){
        this.x = x;
        this.y = y;
        this.id = id;
        this.main = main;
        main.getHandler().addObject(this);
    }

    public abstract void tick();
    public abstract void render(Graphics g);

    public void delete(){
        main.getHandler().removeObject(this);
    }

}
