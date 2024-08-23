package fotoh.game;

import fotoh.Main;
import lombok.Getter;

@Getter
public abstract class LivingEntity extends GameObject {

    private final float health = 100.0f;
    private final float MAX_HEALTH = 100.0f;

    public LivingEntity(float x, float y, float w, float h, ID id, Main main) {
        super(x, y, w, h, id, main);
    }

}
