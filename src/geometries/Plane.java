package geometries;

import primitives.*;

/**
 * Represents a plane in 3D space, defined by either three points or a point and a normal vector.
 */
public class Plane {
    private final Point q; // A point on the plane
    private final Vector normal; // The normal vector to the plane

    /**
     * Constructs a plane from three points in 3D space.
     * 
     * @param point1 First point defining the plane
     * @param point2 Second point defining the plane
     * @param point3 Third point defining the plane
     */
    public Plane(Point point1, Point point2, Point point3) {
        q = point1;
        normal = null; // TODO: Compute the normal vector from the three points
    }

    /**
     * Constructs a plane from a point and a normal vector.
     * 
     * @param point A point on the plane
     * @param vec The normal vector to the plane
     */
    public Plane(Point point, Vector vec) {
        q = point;
        normal = null; // TODO: Assign vec after normalization //vec.normalize(); why error?
    }

    /**
     * Returns the normal vector of the plane.
     * 
     * @param point A point on the plane (not used in current implementation)
     * @return The normal vector of the plane
     */
    public Vector getNormal(Point point) {
        return normal;
    }
}


