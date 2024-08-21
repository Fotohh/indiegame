package fotoh.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ObjectBounds {

    @Setter
    private double x,y;
    private int width, height;
    private double minX, maxX, maxY, minY;

    public ObjectBounds(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        double halfW = (double) width /2;
        double halfH = (double) height /2;
        minX = x - halfW;
        maxX = x + halfW;
        minY = y - halfH;
        maxY = y + halfH;
    }

    public void resize(int width, int height){
        this.width = width;
        this.height = height;
        double halfW = (double) width /2;
        double halfH = (double) height /2;
        minX = x - halfW;
        maxX = x + halfW;
        minY = y - halfH;
        maxY = y + halfH;
    }

    public void getAndAddX(double x) {
        this.x += x;
    }

    public void getAndAddY(double y) {
        this.y += y;
    }

}
