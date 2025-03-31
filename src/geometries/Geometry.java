package geometries;
import primitives.Vector;
import primitives.Point;

/**
 * Represents a general geometric shape in 3D space.
 * This is an abstract class that serves as a base for specific geometric shapes.
 */
public abstract class Geometry {
    
    /**
     * Returns the normal vector to the geometry at a given point.
     *
     * @param point A point on the surface of the geometry
     * @return The normal vector at the given point
     */
    protected abstract Vector getNormal(Point point);
}
