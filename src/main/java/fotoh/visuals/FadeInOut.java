package fotoh.visuals;

import org.jetbrains.annotations.NotNull;

import java.util.Timer;
import java.util.TimerTask;

public class FadeInOut extends Animation {

    private long fadeIn, stay, fadeOut;

    private final GraphicItem item;

    /**
     * Fade In Out animation.
     * @param fadeIn in millis
     * @param stay in millis
     * @param fadeOut in millis
     * @param item graphic item
     */
    public FadeInOut(long fadeIn, long stay, long fadeOut, @NotNull GraphicItem item){
        this.item = item;
        this.fadeIn= fadeIn;
        this.fadeOut = fadeOut;
        this.stay = stay;
        item.animations.add(this);
        item.alpha = 0;
    }

    @Override
    public void run(){
        timer.scheduleAtFixedRate(new TimerTask() {
            final int fis = (int) (fadeIn / 100);
            int counter = fis;
            @Override
            public void run() {
                if(item.alpha < 100) {
                    if (counter > 0) counter--;
                    else {
                        counter = fis;
                        item.alpha+=1;
                    }
                } else {
                    timer.cancel();
                    stay();
                }
            }

        }, 0, 1);
    }

    private void stay(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                item.alpha = 100;
                stay--;
                if(stay <= 0) {
                    timer.cancel();
                    fadeOut();
                }
            }
        }, 0, 1);
    }
    private void fadeOut(){
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            final int fos = (int) (fadeOut / 100);
            int c = fos;
            @Override
            public void run() {
                if(item.alpha > 0){
                    if(c > 0) c--; else{
                        c = fos;
                        item.alpha-=1;
                    }
                }else{
                    timer.purge();
                }
            }
        }, 0 ,1);
    }
}
