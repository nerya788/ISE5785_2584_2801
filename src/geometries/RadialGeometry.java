package geometries;

/**
 * Represents a geometric shape that has a radius, such as a sphere or a tube.
 * This is an abstract class that extends Geometry.
 */
public abstract class RadialGeometry extends Geometry {
    
    protected final double radius; // The radius of the geometric shape

    /**
     * Constructs a RadialGeometry object with a specified radius.
     *
     * @param num The radius of the shape
     */
    public RadialGeometry(double num) {
        radius = num;
    }
}
