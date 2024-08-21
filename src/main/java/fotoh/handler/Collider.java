package fotoh.handler;

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
    protected Consumer<Void> callback;

    public void onCollide(Consumer<Void> callback){
        this.callback = callback;
    }

    protected Consumer<Void> getConsumer(){
        return callback;
    }

}
