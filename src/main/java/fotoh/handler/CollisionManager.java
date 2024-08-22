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

    public synchronized void update(){

        System.out.println(objects.size());

        for(GameObject obj : objects){
            if(!obj.isEnabled() || !obj.isCanCollide()) continue;
            for(GameObject other : objects){
                if(obj.getObjectUUID().equals(other.getObjectUUID())) continue;
                if(!other.isEnabled() || !other.isCanCollide()) continue;
                double f_minY = other.getBounds().getMinY();
                double f_maxY = other.getBounds().getMaxY();
                double f_minX = other.getBounds().getMinX();
                double f_maxX = other.getBounds().getMaxX();
                double s_minY = obj.getBounds().getMinY();
                double s_maxY = obj.getBounds().getMaxY();
                double s_minX = obj.getBounds().getMinX();
                double s_maxX = obj.getBounds().getMaxX();
                if((f_minX > s_minX || f_maxX  < s_maxX) && (f_minY > s_minY || f_maxY  < s_maxY)){
                    obj.onCollide(other);
                    other.onCollide(obj);
                }
            }
        }

    }

}
