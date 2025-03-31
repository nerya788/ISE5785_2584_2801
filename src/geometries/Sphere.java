package geometries;

import primitives.*;

/**
 * Represents a sphere in 3D space, defined by a center point and a radius.
 */
public class Sphere extends RadialGeometry {
    private final Point center; // The center of the sphere

    /**
     * Constructs a sphere with a given center point and radius.
     *
     * @param point The center of the sphere
     * @param radius The radius of the sphere
     */
    public Sphere(Point point, double radius) {
        super(radius);
        center = point;
    }

    /**
     * Returns the normal vector to the sphere at a given point on its surface.
     *
     * @param point A point on the surface of the sphere
     * @return The normal vector at the given point
     */
    public Vector getNormal(Point point) {
        return null; // TODO: Compute and return the actual normal vector
    }
}
