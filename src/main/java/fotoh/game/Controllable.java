package fotoh.game;

import fotoh.util.KeyboardEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Controllable {

  static class Control {
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
  private boolean enabled = false;

  private final GameObject gameObject;

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    if (enabled) initializeControls();
    if (!enabled) {
      gameObject.getEvent().clear(gameObject.getClassId());
    }
  }

  public Controllable(GameObject gameObject) {
    this.gameObject = gameObject;
    initializeControls();
  }

  public void initializeControls() {
    if (!gameObject.isEnabled() || !enabled) return;
    gameObject.getEvent().add(keyEvent -> {
      for (Control control : keyPressed) {
        if (keyEvent.getKeyCode() == control.key) {
          control.event.accept(true);
        }
      }
    }, KeyboardEvent.Type.PRESSED, gameObject.getClassId());

    gameObject.getEvent().add(keyEvent -> {
      for (Control control : keyReleased) {
        if (keyEvent.getKeyCode() == control.key) {
          control.event.accept(false);
        }
      }
    }, KeyboardEvent.Type.RELEASED, gameObject.getClassId());
  }

  public synchronized void keyPressed(int key, Consumer<Boolean> event) {
    keyPressed.add(new Control(key, event));
  }

  public synchronized void keyReleased(int key, Consumer<Boolean> event) {
    keyReleased.add(new Control(key, event));
  }

}
