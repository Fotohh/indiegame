package fotoh.handler;

import fotoh.game.GameObject;
import lombok.Getter;

import java.util.LinkedList;

@Getter
public class CollisionManager {

    private final LinkedList<GameObject> objects = new LinkedList<>();

    public void register(GameObject collider) {
        objects.add(collider);
    }

    public synchronized void update() {
        for (GameObject obj : objects) {
            if (!obj.isEnabled() || !obj.getCollider().isEnabled()) continue;
            for (GameObject other : objects) {
                if (obj.getObjectUUID().equals(other.getObjectUUID())) continue;
                if (!other.isEnabled() || !other.getCollider().isEnabled()) continue;
                if (checkCollision(obj, other)) {
                    handleCollision(obj, other);
                    handleCollision(other, obj);
                }
            }
        }
    }

    private boolean checkCollision(GameObject obj, GameObject other) {
        return obj.getCollider().checkSATCollision(obj, other);
    }

    private void handleCollision(GameObject obj, GameObject other) {
        if (obj.getCollider().getOther() != null) {
            obj.getCollider().getOther().accept(other);
        }
    }
}
