import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class CustomMouseListener extends MouseAdapter implements MouseMotionListener {
    private boolean mousePressed = false;
    private int lastMouseX = 0;
    private int lastMouseY = 0;
    private final CameraLight cameraLight;

    public CustomMouseListener(CameraLight cameraLight) {
        this.cameraLight = cameraLight;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mousePressed = true;
            lastMouseX = e.getX();
            lastMouseY = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            mousePressed = false;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mousePressed) {
            int currentMouseX = e.getX();
            int currentMouseY = e.getY();

            int deltaX = currentMouseX - lastMouseX;
            int deltaY = currentMouseY - lastMouseY;

            double rotationSpeed = 0.005; // Adjust the speed of rotation as needed

            double beta = deltaX * rotationSpeed;
            double alpha = deltaY * rotationSpeed;

            cameraLight.updateCameraRot(-(float)alpha, (float)beta, 0);
            cameraLight.updateLightRot(-(float)alpha, (float)beta, 0);

            lastMouseX = currentMouseX;
            lastMouseY = currentMouseY;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Not used
    }
}