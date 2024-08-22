package fotoh.handler;

import fotoh.game.GameObject;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Collider {

    public Collider(ObjectBounds bounds) {
        this.bounds = bounds;
    }

    private final ObjectBounds bounds;
    private boolean canCollide = true;
    private boolean colliding = false;

    public abstract void onCollide(GameObject callback);

}
