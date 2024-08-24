package fotoh.game;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Gravity {

    private final GameObject obj;

    private float JUMP_FORCE = -15.0f;
    private final float accelerationY = 0.5f;
    private final float MAX_SPEED_Y = 10.0f;

    public Gravity(GameObject obj) {
        this.obj = obj;
    }

    private float gravity = 1f;

    private boolean onGround = true;

    private boolean enabled = true;

    public void applyGravity() {
        if(!enabled) return;
        obj.setVelY(obj.getVelY() + gravity);
        if (getObj().getVelY() > MAX_SPEED_Y) getObj().setVelY(MAX_SPEED_Y);
        getObj().setY(getObj().getY() + getObj().getVelY());
    }

    public void fall(float dt){
        if(!enabled) return;
        if (getObj().getVelY() > 0) {
            getObj().setVelY(getObj().getVelY() - accelerationY * dt);
            if (getObj().getVelY() < 0) getObj().setVelY(0);
        } else if (getObj().getVelY() < 0) {
            getObj().setVelY(getObj().getVelY() + accelerationY * dt);
            if (getObj().getVelY() > 0) getObj().setVelY(0);
        }
    }

}