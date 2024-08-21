package fotoh.handler;

import fotoh.game.GameObject;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class Collider {

    public Collider(ObjectBounds bounds) {
        this.bounds = bounds;
    }

    private final ObjectBounds bounds;

    @Setter
    @Getter
    private boolean canCollide;
    @Getter
    @Setter
    private boolean colliding;
    protected Consumer<GameObject> callback;

    public void onCollide(Consumer<GameObject> callback){
        this.callback = callback;
    }

    protected Consumer<GameObject> getConsumer(){
        return callback;
    }

}
