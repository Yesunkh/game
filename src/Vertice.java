

public class Vertice{
    float x, y, z;
    public Vertice(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vertice(Vertice other){
        setX(other.getX());
        setY(other.getY());
        setZ(other.getZ());
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
    public void rotate(double alpha, double beta, double gamma){
        double alphaInRadians = Math.toRadians(alpha);
        double betaInRadians = Math.toRadians(beta);
        double gammaInRadians = Math.toRadians(gamma);
        Quaternion rotationX = new Quaternion(Math.cos(alpha / 2), Math.sin(alpha / 2), 0, 0);
        Quaternion rotationY = new Quaternion(Math.cos(beta / 2), 0, Math.sin(beta / 2), 0);
        Quaternion rotationZ = new Quaternion(Math.cos(gamma / 2), 0, 0, Math.sin(gamma / 2));
        Quaternion combinedRotation = rotationX.multiply(rotationY).multiply(rotationZ);
        combinedRotation.normalize();
        Quaternion p = new Quaternion(0, getX(), getY(), getZ());
        Quaternion result = combinedRotation.multiply(p).multiply(combinedRotation.conjugate());
        setX((float)result.x);
        setY((float)result.y);
        setZ((float)result.z);
    }
    public void translate(float x, float y, float z){
        setX(getX()+x);
        setY(getY()+y);
        setZ(getZ()+z);
    }
    public Vertice normalize(){
        //returns new Vertice normal Vector, original Vertice unchanged
        float length = (float)Math.sqrt(getX()*getX() + getY() * getY() + getZ() * getZ());
        return new Vertice(getX()/length, getY()/length, getZ()/length);
    }
}

