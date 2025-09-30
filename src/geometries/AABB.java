package geometries;

import primitives.Point;

/**
 * Represents an Axis-Aligned Bounding Box (AABB) used for acceleration structures.
 * <p>
 * This class is a simple data holder defined by two points: a minimum and a maximum corner.
 * The logic for intersection testing is handled by the {@link primitives.Ray} class
 * to maintain proper encapsulation of the primitive classes.
 */
public class AABB {

    /** The corner of the box with the smallest x, y, and z coordinates. */
    public final Point min;

    /** The corner of the box with the largest x, y, and z coordinates. */
    public final Point max;

    /**
     * Constructs an AABB with the given minimum and maximum points.
     * @param min The minimum corner point.
     * @param max The maximum corner point.
     */
    public AABB(Point min, Point max) {
        this.min = min;
        this.max = max;
    }
}