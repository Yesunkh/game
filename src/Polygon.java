
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
    public void transformSortProject(float theta, float renderDistance, int[][] grid, CameraLight cameraLight, float tempTranslate){
        //remove tempTranslate later on, when cropping implemented
        //also renderdistance cropping isn't enabled yet
        Utility utility = new Utility();
        List<Triangle> trisCamSorted = new ArrayList<Triangle>();

        for (Triangle tri : tris) {
            //copy triangle, transform inverse of camera to compare relative to origin
            Triangle transformedTri = new Triangle(tri);
            transformedTri.translate(-cameraLight.getCameraV().getX(), -cameraLight.getCameraV().getY(), -cameraLight.getCameraV().getZ());

            transformedTri.rotate(-cameraLight.getCameraAlpha(), -cameraLight.getCameraBeta(), -cameraLight.getCameraGamma());
            //is triangle dot product in visible range? is triangle in front of camera?
            if (utility.isTriangleAligned(transformedTri, new Vertice(0,0,0)) && utility.getMidPoint(transformedTri).getZ()>0 && utility.calculateDistance(utility.getMidPoint(transformedTri), new Vertice(0,0,0))<renderDistance){
                trisCamSorted.add(transformedTri);
            }
        }


        //sort algorithm for drawing triangles furthest in front
        trisCamSorted = utility.sortTriangles(trisCamSorted, new Vertice(0,0,0));

        project(theta, renderDistance, grid, utility, trisCamSorted, cameraLight);




    }

    public int projectX(float theta , int[][] grid, float x, float Z, float scalar){
        float f = (float)(1/Math.tan(Math.toRadians(theta/2)));
        float a = (float)grid.length/ (float)grid[0].length;
        //float a = 1;
        float X = ((x*a*f)* scalar)/(Z) ;
        X += (float)grid.length/2;
        return (int)Math.round(X);
    }
    public int projectY(float theta, int[][] grid, float y, float Z, float scalar){
        float f = (float)(1/Math.tan(Math.toRadians(theta/2)));
        float a = (float)grid[0].length/ (float)grid.length;
        float Y = ((y*a*f)* scalar)/(Z);
        Y += (float)grid[0].length/2;
        return (int)Math.round(Y);
    }
    public int projectZ(float renderDistance, float Z){
        float p = (float)renderDistance/((float)renderDistance - (float)renderDistance/10);
        return (int)(Z* p - p*(float)renderDistance/10);
    }
    public void project(float theta, float renderDistance, int[][] grid, Utility utility, List<Triangle> trisSorted, CameraLight cameraLight){

        for (Triangle tri : trisSorted) {
            float scalar = 1000;
            int[] v1 = {projectX(theta, grid, tri.getA().getX(), tri.getA().getZ(), scalar), projectY(theta, grid, tri.getA().getY(), tri.getA().getZ(), scalar)};
            int[] v2 = {projectX(theta, grid, tri.getB().getX(), tri.getB().getZ(), scalar), projectY(theta, grid, tri.getB().getY(), tri.getB().getZ(), scalar)};
            int[] v3 = {projectX(theta, grid, tri.getC().getX(), tri.getC().getZ(), scalar), projectY(theta, grid, tri.getC().getY(), tri.getC().getZ(), scalar)};
            Vertice vertice1 = new Vertice(v1[0], v1[1], 0);
            Vertice vertice2 = new Vertice(v2[0], v2[1], 0);
            Vertice vertice3 = new Vertice(v3[0], v3[1], 0);

            //calculate distance between camera and ball (later light and ball) so ball wont
            // infinetely get lighter as farther away and angles of proj get steeper
            Vertice inverseLight = new Vertice(cameraLight.getLightV());
            inverseLight.translate(-cameraLight.getCameraV().getX(), -cameraLight.getCameraV().getY(), -cameraLight.getCameraV().getZ());
            inverseLight.rotate(-cameraLight.getCameraAlpha(), -cameraLight.getCameraBeta(), -cameraLight.getCameraGamma());
            float alignment = utility.howMuchIsTriangleAligned(tri, inverseLight);
            float distance = utility.lengthVertice(utility.subVertice(utility.getMidPoint(tri), inverseLight));
            int shade = color.lighten((int)(-(alignment/distance) * 80));

            utility.rasterizeTriangle(grid,vertice1, vertice2,vertice3, shade);
            /*
            drawLine(v1[0], v1[1], v2[0], v2[1],255, grid);
            drawLine(v2[0], v2[1], v3[0], v3[1],255, grid);
            drawLine(v3[0], v3[1], v1[0], v1[1],255, grid);

             */


        }
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

