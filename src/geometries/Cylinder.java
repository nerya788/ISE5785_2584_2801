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
    public Cylinder(double newHeight, Ray ray, double radius) {
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
        return null; // TODO: Compute and return the actual normal vector
    }
}
