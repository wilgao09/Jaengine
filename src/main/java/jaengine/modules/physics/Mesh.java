package jaengine.modules.physics;


/**
 * Describes how a shape is to look on screen, if at all
 */
public class Mesh extends GameAttribute {
    private double[] points;
    /**
     * Create a new Mesh
     */
    public Mesh() {
        super("Mesh",true);
    }
    /**
     * Required. Set the points in this Mesh. The mesh is built around the point (0,0)
     * @param type The shape of the mesh; currently only accepts box
     * @param data Arguments to aupplement the type
     */
    public void setMesh(String type, double[] data) {
        if (points == null) {
            if (type.equals("box")) {
                //expecting data.length = 2
                points = new double[8];
                double w = data[0]/2.0;
                double h = data[1]/2.0;
                points[0] = -1 * w;
                points[1] = -1 * h;
                points[2] = w;
                points[3] = -1 * h;
                points[4] = w;
                points[5] = h;
                points[6] = -1 * w;
                points[7] = h;
            }
        }
    }
    /**
     * Retreive the mesh points. The points are around (0,0)
     * @return the mesh points
     */
    public double[] getPoints() {
        return this.points;
    }
}
