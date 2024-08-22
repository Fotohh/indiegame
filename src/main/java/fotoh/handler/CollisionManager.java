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

    private boolean checkAABBCollision(GameObject A, GameObject B) {
      var AisToTheRightOfB = A.getCollider().getMinX(A.getX(), A.getWidth()) > B.getCollider().getMaxX(B.getX(), B.getWidth());
      var AisToTheLeftOfB = A.getCollider().getMaxX(A.getX(), A.getWidth()) < B.getCollider().getMinX(B.getX(), B.getWidth());
      var AisAboveB = A.getCollider().getMinY(A.getY(), A.getHeight()) > B.getCollider().getMaxY(B.getY(), B.getHeight());
      var AisBelowB = A.getCollider().getMaxY(A.getY(), A.getHeight()) < B.getCollider().getMinY(B.getY(), B.getHeight());
      return !(AisToTheRightOfB
        || AisToTheLeftOfB
        || AisAboveB
        || AisBelowB);
    }

    public synchronized void update(){

        for(GameObject obj : objects){
            if(!obj.isEnabled() || !obj.getCollider().isCanCollide()) continue;
            for(GameObject other : objects){
                if(obj.getObjectUUID().equals(other.getObjectUUID())) continue;
                if(!other.isEnabled() || !other.getCollider().isCanCollide()) continue;
                if (checkAABBCollision(obj, other)) obj.getCollider().getOther().accept(other);
            }
        }

    }

}
