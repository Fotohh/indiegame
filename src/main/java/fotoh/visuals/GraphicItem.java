package fotoh.visuals;

import lombok.Setter;

import java.awt.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class GraphicItem {

    protected int r,g,b = 0;
    protected int alpha = 255;

    protected ConcurrentLinkedQueue<Animation> animations = new ConcurrentLinkedQueue<>();

    public void addAnimation(Animation animation){
        animations.add(animation);
    }

    public Color getColor(){
        return new Color(r,g,b,alpha);
    }

    public GraphicItem(){

    }

    public abstract void draw(Graphics graphics, int x, int y);
}
