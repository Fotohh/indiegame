package com.fotohh.indiegame.window;

import com.fotohh.indiegame.game.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler {

    LinkedList<GameObject> object = new LinkedList<>();

    public void tick(){
        for (GameObject gameObject : object) {
            gameObject.tick();
        }
    }

    public void render(Graphics g){
        for (GameObject gameObject : object) {
            gameObject.render(g);
        }
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public GameObject removeObject(GameObject object){
        this.object.remove(object);
        return object;
    }

}
