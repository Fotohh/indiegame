package fotoh.handler;

import fotoh.Main;
import fotoh.game.GameObject;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;


@Getter
public class Collider {

    public enum CollisionDirection {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    @Setter
    private boolean enabled = true;

    private Consumer<GameObject> other;

    public void onCollide(Consumer<GameObject> other) {
        this.other = other;
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

    public CollisionDirection getCollisionDirection(GameObject self, GameObject other) {
        float dx = other.getX() - self.getX();
        float dy = other.getY() - self.getY();
        float absDx = Math.abs(dx);
        float absDy = Math.abs(dy);

        if (absDx > absDy) {
            return dx > 0 ? CollisionDirection.LEFT : CollisionDirection.RIGHT;
        } else {
            return dy > 0 ? CollisionDirection.TOP : CollisionDirection.BOTTOM;
        }
    }

}
