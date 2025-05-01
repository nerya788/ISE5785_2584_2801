package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Geometries class represents a collection of geometric objects that
 * can be intersected with a ray. It allows aggregation of multiple
 * geometries and provides a unified interface for intersection calculations.
 */
public class Geometries implements Intersectable {

    private final List<Intersectable> allgeometries = new LinkedList<>();

    /**
     * Default constructor for the Geometries class.
     * Creates an empty collection of geometries.
     */
    public Geometries() {
    }

    /**
     * Constructor for the Geometries class.
     * Allows initialization with a collection of intersectable geometries.
     *
     * @param geometries A variable number of geometries to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds one or more intersectable geometries to the collection.
     *
     * @param geometries A variable number of geometries to be added.
     */
    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
        	allgeometries.add(geo);
        }
    }

    /**
     * Finds all intersection points between the given ray and the geometries
     * in the collection.
     *
     * @param ray The ray for which to find intersection points.
     * @return A list of intersection points, or {@code null} if no intersections are found.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;

        for (Intersectable geo : allgeometries) {
            List<Point> points = geo.findIntersections(ray);
            if (points != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(points);
            }
        }

        return intersections;
    }
}
