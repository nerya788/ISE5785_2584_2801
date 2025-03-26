package primitives;

/**
 * Represents a point in a 3D space.
 */
public class Point {
    /**
     * The coordinates of the point stored as a Double3 object.
     */
    protected final Double3 xyz;
    
    /**
     * Constant representing the zero point (0,0,0).
     */
    public static final Point ZERO = new Point(new Double3(0, 0, 0));

    /**
     * Constructs a point with given coordinates.
     * @param xyz The coordinates of the point as a Double3 object.
     */
    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Constructs a point with given x, y, and z values.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     * Returns a string representation of the point.
     * @return A string in the format "Point: (x, y, z)".
     */
    @Override
    public String toString() {
        return "Point: " + xyz.toString();
    }

    /**
     * Adds a vector to this point, resulting in a new point.
     * @param v The vector to add.
     * @return A new Point resulting from the addition.
     */
    public Point add(Vector v) {
        return new Point(this.xyz.add(v.xyz));
    }

    /**
     * Subtracts another point from this point, resulting in a vector.
     * @param p The point to subtract.
     * @return A new Vector from the given point to this point.
     */
    public Vector subtract(Point p) {
        return new Vector(this.xyz.subtract(p.xyz));
    }

    /**
     * Computes the squared Euclidean distance between this point and another point.
     * @param point The other point.
     * @return The squared distance.
     */
    public double distanceSquared(Point point) {
        return (xyz.d1() - point.xyz.d1()) * (xyz.d1() - point.xyz.d1())
                + (xyz.d2() - point.xyz.d2()) * (xyz.d2() - point.xyz.d2())
                + (xyz.d3() - point.xyz.d3()) * (xyz.d3() - point.xyz.d3());
    }

    /**
     * Computes the Euclidean distance between this point and another point.
     * @param point The other point.
     * @return The distance.
     */
    public double distance(Point point) {
        return Math.sqrt(this.distanceSquared(point));
    }

    /**
     * Checks if this point is equal to another object.
     * @param poi The object to compare.
     * @return True if the object is a Point with the same coordinates, false otherwise.
     */
    @Override
    public boolean equals(Object poi) {
        if (this == poi)
            return true;
        return (poi instanceof Point other) && this.xyz.equals(other.xyz);
    }
}
