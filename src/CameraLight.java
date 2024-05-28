public class CameraLight {
    Vertice cameraV, lightV;
    float cameraAlpha, cameraBeta, cameraGamma, lightAlpha, lightBeta, lightGamma;
    public CameraLight() {
        cameraV = new Vertice(0,0,0);
        lightV = new Vertice(0,0,0);
        cameraAlpha = 0;
        cameraBeta = 0;
        cameraGamma = 0;
        lightAlpha = 0;
        lightBeta = 0;
        lightGamma = 0;
    }
    public void setCameraRot(float alpha, float beta, float gamma){
        cameraAlpha = alpha;
        cameraBeta = beta;
        cameraGamma = gamma;
    }
    public void setLightRot(float alpha, float beta, float gamma){
        lightAlpha = alpha;
        lightBeta = beta;
        lightGamma = gamma;
    }
    public void updateCameraRot(float alpha, float beta, float gamma){
        cameraAlpha += alpha;
        cameraBeta += beta;
        cameraGamma += gamma;
    }
    public void updateLightRot(float alpha, float beta, float gamma){
        lightAlpha += alpha;
        lightBeta += beta;
        lightGamma += gamma;
    }
    // Apply rotation to the movement vector
    private Vertice applyRotation(Vertice v, float alpha, float beta, float gamma) {
        double cosAlpha = Math.cos(alpha);
        double sinAlpha = Math.sin(alpha);
        double cosBeta = Math.cos(beta);
        double sinBeta = Math.sin(beta);
        double cosGamma = Math.cos(gamma);
        double sinGamma = Math.sin(gamma);

        // Rotation around X-axis (alpha)
        double y1 = cosAlpha * v.getY() - sinAlpha * v.getZ();
        double z1 = sinAlpha * v.getY() + cosAlpha * v.getZ();
        v.setY((float) y1);
        v.setZ((float) z1);

        // Rotation around Y-axis (beta)
        double x2 = cosBeta * v.getX() + sinBeta * v.getZ();
        double z2 = -sinBeta * v.getX() + cosBeta * v.getZ();
        v.setX((float) x2);
        v.setZ((float) z2);

        // Rotation around Z-axis (gamma)
        double x3 = cosGamma * v.getX() - sinGamma * v.getY();
        double y3 = sinGamma * v.getX() + cosGamma * v.getY();
        v.setX((float) x3);
        v.setY((float) y3);

        return v;
    }

    // Translate camera taking rotation into account
    public void translateCamera(float x, float y, float z) {
        Vertice movement = new Vertice(x, y, z);
        movement = applyRotation(movement, cameraAlpha, cameraBeta, cameraGamma);
        cameraV.translate(movement.getX(), movement.getY(), movement.getZ());
    }

    public void translateLight(float x, float y, float z) {
        Vertice movement = new Vertice(x, y, z);
        movement = applyRotation(movement, lightAlpha, lightBeta, lightGamma);
        lightV.translate(movement.getX(), movement.getY(), movement.getZ());
    }
    public Vertice getCameraV(){
        return cameraV;
    }
    public Vertice getLightV(){
        return lightV;
    }
    public float getCameraAlpha(){
        return cameraAlpha;
    }
    public float getCameraBeta(){
        return cameraBeta;
    }
    public float getCameraGamma(){
        return cameraGamma;
    }
    public float getLightAlpha(){
        return lightAlpha;
    }
    public float getLightBeta(){
        return lightBeta;
    }
    public float getLightGamma(){
        return lightGamma;
    }
}
