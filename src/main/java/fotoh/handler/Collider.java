package fotoh.handler;

import fotoh.game.GameObject;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;


@Getter
public class Collider {

    @Setter
    private boolean canCollide = true;
    @Setter
    private boolean colliding = false;

    private Consumer<GameObject> other;

    public void onCollide(Consumer<GameObject> other){
        this.other = other;
    }

    public float getMinX(float x, float w){
        return x - w/2;
    }
    public float getMaxX(float x, float w){
        return x + w/2;
    }
    public float getMinY(float y, float h){
        return y - h/2;
    }
    public float getMaxY(float y, float h){
        return y + h/2;
    }

}
