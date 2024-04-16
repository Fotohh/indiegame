package fotoh.game;

import fotoh.Main;
import fotoh.util.KeyboardEvent;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class GameObject {

    protected double x,y,velX, velY;

    protected int width, height;

    protected final ID id;

    protected final Main main;

    public GameObject(double x, double y, int w, int h, ID id, Main main){
        this.x = x;
        this.y = y;
        this.id = id;
        this.main = main;
        this.width = w;
        this.height = h;
        main.getHandler().addObject(this);
        initializeControls(main.getWindow().getEvent());
    }

    protected abstract void initializeControls(KeyboardEvent event);

    protected abstract void tick();

    protected abstract void render(Graphics g);

    public void delete(){
        main.getHandler().removeObject(this);
    }

}
