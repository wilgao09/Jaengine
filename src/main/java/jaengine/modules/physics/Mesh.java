package jaengine.modules.physics;

public class Mesh extends GameAttribute{
    private double[] points;
    public Mesh() {
        super("Mesh",true);
    }
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
    public double[] getPoints() {
        return this.points;
    }
}
