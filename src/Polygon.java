
import java.util.List;
import java.util.ArrayList;


public class Polygon {

    List<Triangle> tris;
    Color color;
    public Polygon() {
        tris = new ArrayList<Triangle>();
    }
    public Polygon(Color color) {
        this();
        this.color = color;
    }
    public void addTriangle(Triangle tri) {
        tris.add(tri);
    }
    public List<Triangle> getTriangles() {
        return tris;
    }
    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
    public void rotate(double alpha, double beta, double gamma) {
        for (Triangle tri : tris) {
            tri.rotate(alpha, beta, gamma);
        }
    }
    public void translate(float x, float y, float z) {
        for (Triangle tri : tris) {
            tri.translate(x,y,z);
        }
    }
    public void project2GridTransparent(float theta, float renderDistance, int[][] grid, float tempTranslate){
        //remove tempTranslate later on, when cropping implemented
        //also renderdistance cropping isn't enabled yet
        for (Triangle tri : tris) {
            float scalar = 1000;
            int[] v1 = {projectX(theta, renderDistance, grid, tri.getA().getX(), tri.getA().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getA().getY(), tri.getA().getZ(), scalar, tempTranslate)};
            int[] v2 = {projectX(theta, renderDistance, grid, tri.getB().getX(), tri.getB().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getB().getY(), tri.getB().getZ(), scalar, tempTranslate)};
            int[] v3 = {projectX(theta, renderDistance, grid, tri.getC().getX(), tri.getC().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getC().getY(), tri.getC().getZ(), scalar, tempTranslate)};
            drawLine(v1[0], v1[1], v2[0], v2[1],color.getRGB(), grid);
            drawLine(v2[0], v2[1], v3[0], v3[1],color.getRGB(), grid);
            drawLine(v3[0], v3[1], v1[0], v1[1],color.getRGB(), grid);
        }
    }
    public void project2Grid(float theta, float renderDistance, int[][] grid, Vertice cameraV, float tempTranslate){
        //remove tempTranslate later on, when cropping implemented
        //also renderdistance cropping isn't enabled yet
        for (Triangle tri : tris) {
            Utility utility = new Utility();
            if(utility.isTriangleAligned(tri, cameraV)){
                float scalar = 1000;
                int[] v1 = {projectX(theta, renderDistance, grid, tri.getA().getX(), tri.getA().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getA().getY(), tri.getA().getZ(), scalar, tempTranslate)};
                int[] v2 = {projectX(theta, renderDistance, grid, tri.getB().getX(), tri.getB().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getB().getY(), tri.getB().getZ(), scalar, tempTranslate)};
                int[] v3 = {projectX(theta, renderDistance, grid, tri.getC().getX(), tri.getC().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getC().getY(), tri.getC().getZ(), scalar, tempTranslate)};
                drawLine(v1[0], v1[1], v2[0], v2[1],color.getRGB(), grid);
                drawLine(v2[0], v2[1], v3[0], v3[1],color.getRGB(), grid);
                drawLine(v3[0], v3[1], v1[0], v1[1],color.getRGB(), grid);
            }

        }
    }
    public void project2GridSortedRasterized(float theta, float renderDistance, int[][] grid, Vertice cameraV, Vertice lightV, float tempTranslate){
        //remove tempTranslate later on, when cropping implemented
        //also renderdistance cropping isn't enabled yet
        Utility utility = new Utility();
        List<Triangle> trisSorted = new ArrayList<Triangle>();
        //is triangle dot product in visible range?
        for (Triangle tri : tris) {
            if (utility.isTriangleAligned(tri, cameraV)){
                trisSorted.add(tri);
            }
        }

        //sort algorithm for drawing triangles furthest in front
        trisSorted = utility.sortTriangles(trisSorted, cameraV);
        for (Triangle tri : trisSorted) {
                float scalar = 1000;
                int[] v1 = {projectX(theta, renderDistance, grid, tri.getA().getX(), tri.getA().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getA().getY(), tri.getA().getZ(), scalar, tempTranslate)};
                int[] v2 = {projectX(theta, renderDistance, grid, tri.getB().getX(), tri.getB().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getB().getY(), tri.getB().getZ(), scalar, tempTranslate)};
                int[] v3 = {projectX(theta, renderDistance, grid, tri.getC().getX(), tri.getC().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getC().getY(), tri.getC().getZ(), scalar, tempTranslate)};
                Vertice vertice1 = new Vertice(v1[0], v1[1], 0);
                Vertice vertice2 = new Vertice(v2[0], v2[1], 0);
                Vertice vertice3 = new Vertice(v3[0], v3[1], 0);
                float alignment = utility.howMuchIsTriangleAligned(tri, lightV);
                int shade = color.lighten((int)(-alignment * 20));

                utility.rasterizeTriangle(grid,vertice1, vertice2,vertice3, shade);
                /*
            drawLine(v1[0], v1[1], v2[0], v2[1],20, grid);
            drawLine(v2[0], v2[1], v3[0], v3[1],20, grid);
            drawLine(v3[0], v3[1], v1[0], v1[1],20, grid);

                 */

        }
    }
    public int projectX(float theta ,float renderDistance, int[][] grid, float x, float Z, float scalar, float tempTranslate){
        //REMOVE TEMPTRANSALTE LATER
        float f = (float)(1/Math.tan(Math.toRadians(theta/2)));
        float a = (float)grid.length/ (float)grid[0].length;
        //float a = 1;
        float X = ((x*a*f)* scalar)/(Z+tempTranslate) ;
        X += (float)grid.length/2;
        return (int)Math.round(X);
    }
    public int projectY(float theta, float renderDistance, int[][] grid, float y, float Z, float scalar, float tempTranslate){
        //REMOVE TEMPTRANSLATE
        float f = (float)(1/Math.tan(Math.toRadians(theta/2)));
        float a = (float)grid[0].length/ (float)grid.length;
        float Y = ((y*a*f)* scalar)/(Z+tempTranslate);
        Y += (float)grid[0].length/2;
        return (int)Math.round(Y);
    }
    public int projectZ(float renderDistance, float Z){
        float p = (float)renderDistance/((float)renderDistance - (float)renderDistance/10);
        return (int)(Z* p - p*(float)renderDistance/10);
    }
    public void drawLine (int x1, int y1, int x2, int y2, int color, int[][] grid){
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;
        while (true) {
            if (x1 >= 0 && x1 < grid.length && y1 >= 0 && y1 < grid[0].length) {
                grid[x1][y1] = color; // Set the value on the grid
            }
            if (x1 == x2 && y1 == y2) break;
            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }
    }
}

