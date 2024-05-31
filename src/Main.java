import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setLocationRelativeTo(null);

        int[][] grid = new int[1000][1000];
        DrawImage image = new DrawImage(grid);

        double angle = 0.1;
        CameraLight cameraLight = new CameraLight();
        CustomKeyListener keyListener = new CustomKeyListener(cameraLight, angle );
        CustomMouseListener mouseListener = new CustomMouseListener(cameraLight);
        frame.addKeyListener(keyListener);
        frame.addMouseListener(mouseListener);
        frame.addMouseMotionListener(mouseListener);
        cameraLight.translateCamera(0,0,-10);
        cameraLight.translateLight(0,0,-10);
        Color color = new Color();
        color.setBlue(10);
        color.setGreen(10);
        color.setRed(10);
        color.setRGB();


        try{
            String filepath = "testobj/UVSphere.obj";
            Polygon polygon = OBJParser.parseOBJFile(filepath);
            polygon.setColor(color);
            image.redrawImage(grid);
            frame.add(image);

            frame.setVisible(true);

            while(true){
                image.clearGrid(grid);
                polygon.rotate(angle, -angle, angle);
                keyListener.processKeyEvents();
                polygon.transformSortProject(90, 20, grid, cameraLight, 0);
                image.redrawImage(grid);

                try {
                    Thread.sleep(20); // Adjust the sleep time as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
