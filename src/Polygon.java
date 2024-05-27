import java.util.*;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;

public class Polygon {

    List<Triangle> tris;
    int color;
    public Polygon() {
        tris = new ArrayList<Triangle>();
    }
    public Polygon(int color) {
        this();
        this.color = color;
    }
    public void addTriangle(Triangle tri) {
        tris.add(tri);
    }
    public List<Triangle> getTriangles() {
        return tris;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
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
    public void project2Grid(float theta, float renderDistance, int[][] grid, float tempTranslate){
        //remove tempTranslate later on, when cropping implemented
        //also renderdistance cropping isn't enabled yet
        for (Triangle tri : tris) {
            float scalar = 1000;
            int[] v1 = {projectX(theta, renderDistance, grid, tri.getA().getX(), tri.getA().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getA().getY(), tri.getA().getZ(), scalar, tempTranslate)};
            int[] v2 = {projectX(theta, renderDistance, grid, tri.getB().getX(), tri.getB().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getB().getY(), tri.getB().getZ(), scalar, tempTranslate)};
            int[] v3 = {projectX(theta, renderDistance, grid, tri.getC().getX(), tri.getC().getZ(), scalar, tempTranslate), projectY(theta, renderDistance, grid, tri.getC().getY(), tri.getC().getZ(), scalar, tempTranslate)};
            drawLine(v1[0], v1[1], v2[0], v2[1],getColor(), grid);
            drawLine(v2[0], v2[1], v3[0], v3[1],getColor(), grid);
            drawLine(v3[0], v3[1], v1[0], v1[1],getColor(), grid);
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



/*

public class Triangle{

    Vertice a, b ,c;
    public Triangle(Vertice a, Vertice b, Vertice c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public Vertice getA(){
        return a;
    }
    public Vertice getB(){
        return b;
    }
    public Vertice getC(){
        return c;
    }
    public void setA(Vertice a){
        this.a = a;

    }
    public void setB(Vertice b){
        this.b = b;
    }
    public void setC(Vertice c){
        this.c = c;
    }
    public void rotate(double alpha, double beta, double gamma){

        getA().rotateX(alpha);
        getA().rotateY(beta);
        getA().rotateZ(gamma);
        getB().rotateX(alpha);
        getB().rotateY(beta);
        getB().rotateZ(gamma);
        getC().rotateX(alpha);
        getC().rotateY(beta);
        getC().rotateZ(gamma);
    }
    public void translate(float x, float y, float z){
        getA().translate(x,y,z);
        getB().translate(x,y,z);
        getC().translate(x,y,z);
    }
}

class Vertice{
    float x, y, z;
    public Vertice(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getZ() {
        return z;
    }
    public void setX(float x){
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }
    public void setZ(float z){
        this.z = z;
    }
    public void rotateX(double angle){
        float angleInRadians = (float)Math.toRadians(angle);
        float[] pos = new float[3];
        pos[0] = getX();
        pos[1] = getY() * (float)Math.cos(angleInRadians) + getZ() * (float)(-Math.sin(angleInRadians));
        pos[2] = getZ() * (float)Math.cos(angleInRadians) + getY() * (float)Math.sin(angleInRadians);
        setX(pos[0]);
        setY(pos[1]);
        setZ(pos[2]);
    }
    public void rotateY(double angle){
        float angleInRadians = (float)Math.toRadians(angle);
        float[] pos = new float[3];
        pos[0] = getX() * (float)Math.cos(angleInRadians) + getZ() * (float)(-Math.sin(angleInRadians));
        pos[1] = getY();
        pos[2] = getZ() * (float)Math.cos(angleInRadians) + getX() * (float)Math.sin(angleInRadians);
        setX(pos[0]);
        setY(pos[1]);
        setZ(pos[2]);
    }
    public void rotateZ(double angle){
        float angleInRadians = (float)Math.toRadians(angle);
        float[] pos = new float[3];
        pos[0] = getX() * (float)Math.cos(angleInRadians) + getY() * (float)(-Math.sin(angleInRadians));
        pos[1] = getY() * (float)Math.cos(angleInRadians) + getX() * (float)Math.sin(angleInRadians);
        pos[2] = getZ();
        setX(pos[0]);
        setY(pos[1]);
        setZ(pos[2]);
    }
    public void translate(float x, float y, float z){
        setX(getX()+x);
        setY(getY()+y);
        setZ(getZ()+z);
    }
}


 */


