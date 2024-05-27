import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Utility {

    public Vertice addVertice(Vertice v1, Vertice v2){
        return new Vertice(v1.getX() + v2.getX(), v1.getY() + v2.getY(), v1.getZ() + v2.getZ());
    }
    public Vertice subVertice(Vertice v1, Vertice v2){
        return new Vertice(v1.getX() - v2.getX(), v1.getY() - v2.getY(), v1.getZ() - v2.getZ());
    }
    public void multVertice(Vertice v1, double scalar){
        v1.setX(v1.getX()*(float)scalar);
        v1.setY(v1.getY()*(float)scalar);
        v1.setZ(v1.getZ()*(float)scalar);
    }
    public void divVertice(Vertice v1, double scalar){
        v1.setX(v1.getX()/(float)scalar);
        v1.setY(v1.getY()/(float)scalar);
        v1.setZ(v1.getZ()/(float)scalar);
    }
    public float dotProduct(Vertice v1, Vertice v2){
        return v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
    }
    public float lengthVertice(Vertice v){
        return (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ() * v.getZ());
    }
    //normalVertice also in Vertice.java, duplicate use here may delete later
    public Vertice normalVertice(Vertice v1){
        float l = lengthVertice(v1);
        return new Vertice(v1.getX()/l, v1.getY()/l, v1.getZ()/l);
    }
    public Vertice crossProduct(Vertice v1, Vertice v2){
        return new Vertice(v1.getY()*v2.getZ() - v1.getZ() * v2.getY(), v1.getZ() * v2.getX() - v1.getX() * v2.getZ(), v1.getX() * v2.getY() - v1.getY() * v2.getX());
    }
    public List<Triangle> sortTriangles(List<Triangle> triangles, Vertice P){

        List<TriangleDistance> triangleDistances = new ArrayList<>();
        for (Triangle triangle : triangles) {
            Vertice midpoint = getMidPoint(triangle);
            double distance = calculateDistance(P, midpoint);
            triangleDistances.add(new TriangleDistance(triangle, distance));
        }
        Collections.sort(triangleDistances, Comparator.comparingDouble(td -> -td.distance));
        // Extract the sorted triangles
        List<Triangle> sortedList = new ArrayList<Triangle>();
        for (TriangleDistance td : triangleDistances) {
            sortedList.add(td.triangle);
        }
        return sortedList;
    }
    public boolean isTriangleAligned(Triangle triangle, Vertice cameraV){
        //errechne Normalvektor auf Dreieck
        Vertice AB = subVertice(triangle.getB(), triangle.getA());
        Vertice BC = subVertice(triangle.getC(), triangle.getB());
        Vertice normalT = crossProduct(AB, BC);
        normalT = normalVertice(normalT);

        Vertice cam2Tri = subVertice(triangle.getA(), cameraV);

        return dotProduct(normalT, cam2Tri) < 0;
    }
    public float howMuchIsTriangleAligned(Triangle triangle, Vertice lightV){
        //errechne Normalvektor auf Dreieck
        Vertice AB = subVertice(triangle.getB(), triangle.getA());
        Vertice BC = subVertice(triangle.getC(), triangle.getB());
        Vertice normalT = crossProduct(AB, BC);
        normalT = normalVertice(normalT);

        Vertice light2Tri = subVertice(triangle.getA(), lightV);

        return dotProduct(normalT, light2Tri);
    }
    public Vertice getMidPoint(Triangle tri){
        float mx = (tri.getA().getX() + tri.getB().getX() + tri.getC().getX())/3;
        float my = (tri.getA().getY() + tri.getB().getY() + tri.getC().getY())/3;
        float mz = (tri.getA().getZ() + tri.getB().getZ() + tri.getC().getZ())/3;
        return new Vertice(mx, my, mz);
    }
    public double calculateDistance(Vertice P, Vertice M) {
        return Math.sqrt(Math.pow(M.getX() - P.getX(), 2) + Math.pow(M.getY() - P.getY(), 2) + Math.pow(M.getZ() - P.getZ(), 2));
    }

    public void rasterizeTriangle(int[][] grid, Vertice v0, Vertice v1, Vertice v2, int color) {
        int minX = (int)Math.min(Math.min(v0.getX(), v1.getX()), v2.getX());
        int minY = (int)Math.min(Math.min(v0.getY(), v1.getY()), v2.getY());
        int maxX = (int)Math.max(Math.max(v0.getX(), v1.getX()), v2.getX());
        int maxY = (int)Math.max(Math.max(v0.getY(), v1.getY()), v2.getY());

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (pointInTriangle(x, y, v0, v1, v2)) {
                    grid[x][y] = color;
                }
            }
        }
    }

    public boolean pointInTriangle(int px, int py, Vertice v0, Vertice v1, Vertice v2) {
        float dX = px - v2.getX();
        float dY = py - v2.getY();
        float dX21 = v2.getX() - v1.getX();
        float dY12 = v1.getY() - v2.getY();
        float D = dY12 * (v0.getX() - v2.getX()) + dX21 * (v0.getY() - v2.getY());
        float s = dY12 * dX + dX21 * dY;
        float t = (v2.getY() - v0.getY()) * dX + (v0.getX() - v2.getX()) * dY;

        if (D < 0) {
            return s <= 0 && t <= 0 && s + t >= D;
        }
        return s >= 0 && t >= 0 && s + t <= D;
    }


}
class TriangleDistance {
    Triangle triangle;
    double distance;

    TriangleDistance(Triangle triangle, double distance) {
        this.triangle = triangle;
        this.distance = distance;
    }
}
