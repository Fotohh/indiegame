package fotoh.handler;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ObjectBounds {

    @Setter
    private double x,y;
    private final int width, height;

    public ObjectBounds(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void getAndAddX(double x) {
        this.x += x;
    }

    public void getAndAddY(double y) {
        this.y += y;
    }

}
