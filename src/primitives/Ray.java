package primitives;

/**
 * Represents a ray in 3D space, defined by a starting point and a direction vector.
 */
public class Ray {
    
    /** The starting point of the ray. */
    private final Point head;
    
    /** The normalized direction vector of the ray. */
    private final Vector direction;

    /**
     * Constructs a ray with a given starting point and direction vector.
     * 
     * @param point The starting point of the ray.
     * @param vec The direction vector of the ray.
     * @throws IllegalArgumentException if the given vector is not normalized.
     */
    public Ray(Point point, Vector vec) {
        head = point;
        direction = vec.normalize();
    }
    
    /**
     * Checks whether this ray is equal to another object.
     * 
     * @param obj The object to compare.
     * @return True if the object is a Ray with the same head and direction, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && this.head.equals(other.head)
                && this.direction.equals(other.direction);
    }
    
    /**
     * Returns a string representation of the ray.
     * 
     * @return A string describing the ray in the format "Ray: head=(x, y, z), direction=(dx, dy, dz)".
     */
    @Override
    public String toString() {
        return "Ray: head=" + head.toString() + ", direction=" + direction.toString();
    }
}
