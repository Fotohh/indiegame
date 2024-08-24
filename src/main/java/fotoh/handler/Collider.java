package fotoh.handler;

import fotoh.Main;
import fotoh.game.GameObject;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;


@Setter
@Getter
public class Collider {

    public enum CollisionDirection {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private final ConcurrentHashMap<CollisionDirection, Boolean> collisionDirections = new ConcurrentHashMap<>(){{
        put(CollisionDirection.TOP, false);
        put(CollisionDirection.BOTTOM, false);
        put(CollisionDirection.LEFT, false);
        put(CollisionDirection.RIGHT, false);
    }};
    private final List<GameObject> colliders = new ArrayList<>();
    private boolean enabled = true;

    public void update(GameObject object, Main main){
        if(!enabled) return;
        checkBounds(object, main);
        object.handleCollision(colliders, collisionDirections);
        updateCollision(object);
    }


    private float[] projectPolygonOntoAxis(GameObject obj, float[] axis) {
        float[] vertices = obj.getVertices();
        float min = dotProduct(vertices[0], vertices[1], axis);
        float max = min;
        for (int i = 2; i < vertices.length; i += 2) {
            float projection = dotProduct(vertices[i], vertices[i + 1], axis);
            if (projection < min) {
                min = projection;
            } else if (projection > max) {
                max = projection;
            }
        }
        return new float[]{min, max};
    }

    public void checkBounds(GameObject o, Main main){
        if (o.getX() < 0) {
            o.setX(0);
            o.setVelX(0);
        } else if (o.getX() + o.getWidth() > main.getWidth()) {
            o.setX(main.getWidth() - o.getWidth());
            o.setVelX(0);
        }

        if (o.getY() < 0) {
            o.setY(0);
            o.setVelY(0);
        } else if (o.getY() + o.getHeight() > main.getHeight()) {
            o.setY(main.getHeight() - o.getHeight());
            o.setVelY(0);
            o.getGravity().setOnGround(true);
        }
    }

    private float dotProduct(float x, float y, float[] axis) {
        return x * axis[0] + y * axis[1];
    }

    private boolean isOverlapping(float[] a, float[] b) {
        return a[0] <= b[1] && a[1] >= b[0];
    }

    boolean checkSATCollision(GameObject A, GameObject B) {
        if(!enabled) return false;
        float[][] axesA = A.getAxes();
        float[][] axesB = B.getAxes();

        for (float[] axis : axesA) {
            if (!isOverlapping(projectPolygonOntoAxis(A, axis), projectPolygonOntoAxis(B, axis))) {
                return false;
            }
        }

        for (float[] axis : axesB) {
            if (!isOverlapping(projectPolygonOntoAxis(A, axis), projectPolygonOntoAxis(B, axis))) {
                return false;
            }
        }

        return true;
    }

    private CollisionDirection getCollisionDirection(GameObject self, GameObject other) {
        float dx = other.getX() - self.getX();
        float dy = other.getY() - self.getY();
        float absDx = Math.abs(dx);
        float absDy = Math.abs(dy);

        if (absDx > absDy) {
            return dx > 0 ? CollisionDirection.RIGHT : CollisionDirection.LEFT;
        } else {
            return dy > 0 ? CollisionDirection.BOTTOM : CollisionDirection.TOP;
        }
    }

    public void updateCollision(GameObject current){
        for(CollisionDirection direction : CollisionDirection.values()){
            if(collisionDirections.containsKey(direction)){
                collisionDirections.replace(direction, isCollidingInDirection(current, current.getMain().getGameObjects(), direction));
            }else collisionDirections.put(direction, isCollidingInDirection(current, current.getMain().getGameObjects(), direction));
        }
    }

    private boolean isCollidingInDirection(GameObject current, ConcurrentLinkedQueue<GameObject> objects, CollisionDirection direction) {
        for (GameObject other : objects) {
            if (current.getObjectUUID().equals(other.getObjectUUID())) continue;
            if (!other.isEnabled() || !other.getCollider().isEnabled()) continue;
            if (checkSATCollision(current, other)) {
                if (getCollisionDirection(current, other) == direction) {
                    if(!colliders.contains(other)) colliders.add(other);
                    return true;
                }
            }else{
                colliders.remove(other);
            }
        }
        return false;
    }

}
