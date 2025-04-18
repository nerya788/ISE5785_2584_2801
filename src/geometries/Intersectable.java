package geometries;

import java.util.List;

import primitives.*;

/**
 * Interface representing geometric objects that can be intersected by a {@link Ray}.
 * Classes implementing this interface provide methods for finding intersections.
 */
public interface Intersectable {

    /**
     * Finds the intersection points between the current object and the given {@link Ray}.
     *
     * @param ray The ray used to find intersections with the object.
     * @return A list of {@link Point} objects representing the intersection points,
     * or {@code null} if there are no intersections.
     */
    List<Point> findIntersections(Ray ray);
}
