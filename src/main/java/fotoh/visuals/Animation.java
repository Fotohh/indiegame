package fotoh.visuals;

import java.util.Timer;

public abstract class Animation {

    protected Timer timer = new Timer();

    public abstract void run();
}
