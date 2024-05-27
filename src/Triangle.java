

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
        /*
        getA().rotateX(alpha);
        getA().rotateY(beta);
        getA().rotateZ(gamma);
        getB().rotateX(alpha);
        getB().rotateY(beta);
        getB().rotateZ(gamma);
        getC().rotateX(alpha);


        getC().rotateY(beta);


        getC().rotateZ(gamma);

         */
        getA().rotate(alpha, beta, gamma);
        getB().rotate(alpha, beta, gamma);
        getC().rotate(alpha, beta, gamma);
    }
    public void translate(float x, float y, float z){
        getA().translate(x,y,z);
        getB().translate(x,y,z);
        getC().translate(x,y,z);
    }
}