package fotoh.game;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

public class HealthBar {

    private LivingEntity entity;
    private float width, height;
    private float xOffset, yOffset;
    private int borderThickness = 2;
    private Color healthBarFull = Color.GREEN;
    private Color borderColor = Color.BLACK;
    private Color backgroundColor = Color.GRAY;
    private int x = 0, y = 0;
    private int r = 0, g = 255, b = 0;
    @Setter
    @Getter
    private boolean enabled = true;
    private float tempX = -1, tempY = -1;

    public HealthBar(LivingEntity entity, float width, float height, float xOffset, float yOffset) {
        this.entity = entity;
        this.width = width;
        this.height = height;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void render(Graphics g) {
        if (!enabled) return;
        if (entity == null) return;

        float healthPercentage = entity.getHealth() / entity.getMaxHealth();
        int barWidth = (int) (width * healthPercentage);

        g.setColor(backgroundColor);
        g.fillRect(x - borderThickness, y - borderThickness, (int) width + 2 * borderThickness, (int) height + 2 * borderThickness);

        g.setColor(healthBarFull);
        g.fillRect(x, y, barWidth, (int) height);

        g.setColor(borderColor);
        g.drawRect(x - borderThickness, y - borderThickness, (int) width + 2 * borderThickness - 1, (int) height + 2 * borderThickness - 1);
    }

    public void tick(float dt) {
        if (!enabled) return;
        if (entity == null) return;
        x = (int) (entity.getX() + xOffset);
        y = (int) (entity.getY() + yOffset);
        if (tempX != x || tempY != y) {
            tempX = x;
            tempY = y;
        }
        int increment = (int) (255 / entity.getMaxHealth());
        r = Math.min(255, (int) ((entity.getMaxHealth() - entity.getHealth()) * increment));
        g = Math.max(0, 255 - (int) ((entity.getMaxHealth() - entity.getHealth()) * increment));
        healthBarFull = new Color(r, g, b);
    }
}
