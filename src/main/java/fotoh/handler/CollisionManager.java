package fotoh.handler;

import lombok.Getter;

import java.util.LinkedList;

@Getter
public class CollisionManager {

    private final LinkedList<Collider> objects = new LinkedList<>();

    public void register(Collider collider) {
        objects.add(collider);
    }

    public void update(){
        //todo check collision

        //check if enabled
    }
}
