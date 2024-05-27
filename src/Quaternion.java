public class Quaternion {
    public double w, x, y, z;

    public Quaternion(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Quaternion multiply(Quaternion q) {
        double nw = w * q.w - x * q.x - y * q.y - z * q.z;
        double nx = w * q.x + x * q.w + y * q.z - z * q.y;
        double ny = w * q.y - x * q.z + y * q.w + z * q.x;
        double nz = w * q.z + x * q.y - y * q.x + z * q.w;
        return new Quaternion(nw, nx, ny, nz);
    }

    public Quaternion conjugate() {
        return new Quaternion(w, -x, -y, -z);
    }

    public static Quaternion fromAxisAngle(Vertice axis, double angle) {
        axis = axis.normalize();
        double halfAngle = angle / 2;
        double sinHalfAngle = Math.sin(halfAngle);
        return new Quaternion(Math.cos(halfAngle),
                axis.getX() * sinHalfAngle,
                axis.getY() * sinHalfAngle,
                axis.getZ() * sinHalfAngle);
    }
    public void normalize() {
        double magnitude = Math.sqrt(w * w + x * x + y * y + z * z);
        w /= magnitude;
        x /= magnitude;
        y /= magnitude;
        z /= magnitude;
    }

}