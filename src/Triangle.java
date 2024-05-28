

public class Triangle{

    Vertice a, b ,c;
    int color;
    public Triangle(Vertice a, Vertice b, Vertice c){
        this.a = a;
        this.b = b;
        this.c = c;
    }
    public Triangle(Triangle other){
        setColor(other.getColor());
        setA(new Vertice(other.getA()));
        setB(new Vertice(other.getB()));
        setC(new Vertice(other.getC()));
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
        getA().rotate(alpha, beta, gamma);
        getB().rotate(alpha, beta, gamma);
        getC().rotate(alpha, beta, gamma);
    }
    public void translate(float x, float y, float z){
        getA().translate(x,y,z);
        getB().translate(x,y,z);
        getC().translate(x,y,z);
    }
    public void setColor(int color){
        this.color = color;
    }
    public int getColor(){
        return this.color;
    }
}