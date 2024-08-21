package fotoh.handler;

import fotoh.game.GameObject;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
public class CollisionManager {

    private final LinkedList<GameObject> objects = new LinkedList<>();

    public void register(GameObject collider) {
        objects.add(collider);
    }

    public void update(){

        ArrayList<GameObject> tmp = (ArrayList<GameObject>) objects.stream().toList();

        for(int i = 0; i < tmp.size() - 1; i++){
            GameObject obj = tmp.get(i);
            if(!obj.isEnabled() || !obj.getCollider().isCanCollide()) continue;
            for(int a = i; a < tmp.size() -1; a++){
                GameObject other = tmp.get(a);
                if(!other.isEnabled() || !other.getCollider().isCanCollide()) continue;
                if((other.getBounds().getMinX() > obj.getBounds().getMinX() || other.getBounds().getMaxX() < obj.getBounds().getMaxX())
                        && (other.getBounds().getMinY() > obj.getBounds().getMinY() || other.getBounds().getMaxY() < obj.getBounds().getMaxY())){
                    obj.getCollider().onCollide(other);
                    other.getCollider().onCollide(obj);
                }
            }
        }

    }

}
