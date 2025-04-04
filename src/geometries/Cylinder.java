package geometries;

import primitives.*;

/**
 * Represents a finite cylinder in 3D space, defined by a central axis, a radius, and a height.
 * It extends the Tube class by adding a height constraint.
 */
public class Cylinder extends Tube {
    
    private final double height; // The height of the cylinder

    /**
     * Constructs a cylinder with a specified height, axis, and radius.
     *
     * @param newHeight The height of the cylinder
     * @param ray The central axis of the cylinder
     * @param radius The radius of the cylinder
     */
    public Cylinder(Ray ray, double radius, double newHeight) {
        super(ray, radius);
        height = newHeight;
    }

    /**
     * Returns the normal vector to the cylinder at a given point on its surface.
     *
     * @param point A point on the surface of the cylinder
     * @return The normal vector at the given point
     */
    public Vector getNormal(Point point) {
    	if ( point.equals(axis.getHead()) || point.subtract(axis.getHead()).dotProduct(axis.getDirection()) == 0)
    		return axis.getDirection().scale(-1).normalize();
    	else if (	point.equals(axis.getHead().add(axis.getDirection().scale(height)))
            	||  point.subtract(axis.getHead().add(axis.getDirection().scale(height))).dotProduct(axis.getDirection()) == 0)
    		return axis.getDirection().normalize();
    	else {
    	Vector u = point.subtract(axis.getHead());
    	double t = axis.getDirection().dotProduct(u);
    	Point o = axis.getHead().add(axis.getDirection().scale(t));
    	return point.subtract(o).normalize();
    	}
    }
}
