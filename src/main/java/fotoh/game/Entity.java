package fotoh.game;

import fotoh.Main;
import lombok.Getter;

@Getter
public abstract class Entity extends GameObject {

    private final Gravity gravity = new Gravity(this);

    public Entity(float x, float y, float w, float h, ID id, Main main) {
        super(x, y, w, h, id, main);
    }

}
