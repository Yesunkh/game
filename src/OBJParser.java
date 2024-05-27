import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
public static void main(String[] args) {
        try {
            String filepath = "../objfiles/your_obj_file.obj";
            Polygon polygon = parseOBJFile(filepath);
            // Use the polygon object as needed
            System.out.println("Polygon loaded with " + polygon.getTriangles().size() + " triangles.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 */

//TODO SELECT ONLY TRIANGULATED MESH AND VERTEX GROUPS FOR WAVEFRONT OBJ

public class OBJParser {

    public static Polygon parseOBJFile(String filepath) throws IOException {
        Polygon polygon = new Polygon();
        List<Vertice> vertices = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ")) {
                // Parse vertex
                String[] parts = line.split("\\s+");
                float x = Float.parseFloat(parts[1]);
                float y = Float.parseFloat(parts[2]);
                float z = Float.parseFloat(parts[3]);
                Vertice vertice = new Vertice(x, y, z);
                vertices.add(vertice);
            } else if (line.startsWith("f ")) {
                // Parse face
                String[] parts = line.split("\\s+");
                int idx1 = Integer.parseInt(parts[1]) - 1;
                int idx2 = Integer.parseInt(parts[2]) - 1;
                int idx3 = Integer.parseInt(parts[3]) - 1;
                Vertice v1 = new Vertice(vertices.get(idx1).getX(), vertices.get(idx1).getY(), vertices.get(idx1).getZ());
                Vertice v2 = new Vertice(vertices.get(idx2).getX(), vertices.get(idx2).getY(), vertices.get(idx2).getZ());
                Vertice v3 = new Vertice(vertices.get(idx3).getX(), vertices.get(idx3).getY(), vertices.get(idx3).getZ());

                Triangle triangle = new Triangle(v1, v2, v3);
                polygon.addTriangle(triangle);
            }
        }

        reader.close();
        return polygon;
    }
}

