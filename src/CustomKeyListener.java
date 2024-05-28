import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomKeyListener implements KeyListener {
    private static Set<Integer> pressedKeys;
    private final CameraLight cameraLight;
    private final double angle;

    public CustomKeyListener( CameraLight cameraLight, double angle) {
        this.pressedKeys = new HashSet<Integer>();
        this.cameraLight = cameraLight;
        this.angle = angle;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

    public void processKeyEvents() {
        if (pressedKeys.contains(KeyEvent.VK_W)) {
            cameraLight.translateCamera(0, 0, (float) angle);
            cameraLight.translateLight(0, 0, (float) angle);
        }
        if (pressedKeys.contains(KeyEvent.VK_S)) {
            cameraLight.translateCamera(0, 0, -(float) angle);
            cameraLight.translateLight(0, 0, -(float) angle);
        }
        if (pressedKeys.contains(KeyEvent.VK_A)) {
            cameraLight.translateCamera(-(float) angle, 0, 0);
            cameraLight.translateLight(-(float) angle, 0, 0);
        }
        if (pressedKeys.contains(KeyEvent.VK_D)) {
            cameraLight.translateCamera((float) angle, 0, 0);
            cameraLight.translateLight((float) angle, 0, 0);
        }
        if (pressedKeys.contains(KeyEvent.VK_SPACE)) {
            cameraLight.translateCamera(0, -(float) angle, 0);
            cameraLight.translateLight(0, -(float) angle, 0);
        }
        if (pressedKeys.contains(KeyEvent.VK_SHIFT)) {
            cameraLight.translateCamera(0, (float) angle, 0);
            cameraLight.translateLight(0, (float) angle, 0);
        }
    }
}