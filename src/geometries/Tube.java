package geometries;

import primitives.*;

/**
 * Represents an infinite cylindrical tube in 3D space, defined by a central axis and a radius.
 */
public class Tube extends RadialGeometry {

    protected final Ray axis; // The central axis of the tube

    /**
     * Constructs a tube with a given axis and radius.
     *
     * @param ray The central axis of the tube
     * @param radius The radius of the tube
     */
    public Tube(Ray ray, double radius) {
        super(radius);
        axis = ray;
    }
    

    /**
     * Returns the normal vector to the tube at a given point on its surface.
     *
     * @param point A point on the surface of the tube
     * @return The normal vector at the given point
     */
    public Vector getNormal(Point point) {
    	if (point.subtract(axis.getHead()).dotProduct(axis.getDirection())==0 || point.equals(axis.getHead()))
    		return axis.getDirection().scale(-1).normalize();
    	else {
    	Vector u = point.subtract(axis.getHead());
    	double t = axis.getDirection().dotProduct(u);
    	Point o = axis.getHead().add(axis.getDirection().scale(t));
    	return point.subtract(o).normalize();
    	}
    }
}
