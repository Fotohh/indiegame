package fotoh.game;

import fotoh.util.KeyboardEvent;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Controllable {

  class Control {
    private final int key;
    private final Consumer<Boolean> event;

    public Control(int key, Consumer<Boolean> event) {
      this.key = key;
      this.event = event;
    }
  }

  private final List<Control> keyPressed = new ArrayList<>();
  private final List<Control> keyReleased = new ArrayList<>();

  @Getter
  @Setter
  private boolean enabled = false;

  private final GameObject gameObject;

  public Controllable(GameObject gameObject) {

    this.gameObject = gameObject;

    System.out.println("Controllable created");

    gameObject.getEvent().add(keyEvent -> {
      if (!enabled) {
        return;
      }
      System.out.println("Key pressed: " + keyEvent.getKeyCode());
      for (Control control : keyPressed) {

        if (keyEvent.getKeyCode() == control.key) {
          control.event.accept(true);
        }
      }
    }, KeyboardEvent.Type.PRESSED, gameObject);

    gameObject.getEvent().add(keyEvent -> {
      if (!enabled) {
        return;
      }
      for (Control control : keyReleased) {
        if (keyEvent.getKeyCode() == control.key) {
          control.event.accept(false);
        }
      }
    }, KeyboardEvent.Type.RELEASED, gameObject);

  }

  public synchronized void keyPressed(int key, Consumer<Boolean> event) {
    keyPressed.add(new Control(key, event));
  }

  public synchronized void keyReleased(int key, Consumer<Boolean> event) {
    keyReleased.add(new Control(key, event));
  }



}
