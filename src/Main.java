import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(410, 435);
        frame.setLocationRelativeTo(null);

        int[][] grid = new int[400][400];
        DrawImage image = new DrawImage(grid);

        double angle = 0.01;
        try{
            String filepath = "testobj/UVSphere.obj";
            Polygon polygon = OBJParser.parseOBJFile(filepath);
            polygon.setColor(255);
            image.redrawImage(grid);
            frame.add(image);

            frame.setVisible(true);

            while(true){
                image.clearGrid(grid);
                polygon.rotate(angle, 0, angle);
                polygon.project2Grid(90, 1000, grid, 10);
                image.redrawImage(grid);

                try {
                    Thread.sleep(10); // Adjust the sleep time as needed
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
