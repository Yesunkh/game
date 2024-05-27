import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        int[][] grid = new int[500][500];
        DrawImage image = new DrawImage(grid);

        double angle = 0.01;
        Vertice cameraV = new Vertice(0,0,-10);
        Vertice lightV = new Vertice(0,0,-10);
        Color color = new Color();
        color.setBlue(10);
        color.setGreen(10);
        color.setRed(10);
        color.setRGB();


        try{
            String filepath = "testobj/chair.obj";
            Polygon polygon = OBJParser.parseOBJFile(filepath);
            polygon.setColor(color);
            //polygon.translate(0,0,10);
            image.redrawImage(grid);
            frame.add(image);

            frame.setVisible(true);

            while(true){
                image.clearGrid(grid);
                polygon.rotate(angle, -angle, angle);
                polygon.project2GridSortedRasterized(90, 1000, grid, cameraV, lightV,20);
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
