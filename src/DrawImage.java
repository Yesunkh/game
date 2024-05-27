import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class DrawImage extends JPanel {

    private BufferedImage image;

    //takes in gridvalues and turns into BufferedImage to put on a JPanel
    public DrawImage(int[][] grid) {
        image = new BufferedImage(grid.length, grid[0].length, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                image.setRGB(x, y, grid[x][y]);
            }
        }
    }

    public void redrawImage(int [][] grid) {
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                image.setRGB(x, y, grid[x][y]);
            }
        }

        repaint();
    }

    public void clearGrid(int [][] grid){
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[0].length; y++) {
                grid[x][y] = 0;
            }
        }
    }





    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

}
