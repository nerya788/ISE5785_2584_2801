package primitives;

/**
 * Represents a vector in 3D space, extending the Point class.
 */
public class Vector extends Point {

    /**
     * Constructs a vector with given x, y, and z values.
     * @param d1 The x-coordinate.
     * @param d2 The y-coordinate.
     * @param d3 The z-coordinate.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if (super.equals(ZERO))
            throw new IllegalArgumentException("Vector cannot be zero vector.");
    }

    /**
     * Constructs a vector from a Double3 object.
     * @param d The Double3 representing the vector coordinates.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public Vector(Double3 d) {
        super(d);
        if (super.equals(ZERO))
            throw new IllegalArgumentException("Vector cannot be zero vector.");
    }

    /**
     * Adds another vector to this vector.
     * @param vec The vector to add.
     * @return A new Vector representing the sum of both vectors.
     */
    public Vector add(Vector vec) {
        return new Vector(xyz.add(vec.xyz));
    }

    /**
     * Scales this vector by a scalar value.
     * @param num The scalar value.
     * @return A new Vector representing the scaled vector.
     */
    public Vector scale(double num) {
        return new Vector(xyz.scale(num));
    }

    /**
     * Computes the dot product of this vector with another vector.
     * @param vec The vector to compute the dot product with.
     * @return The dot product as a double value.
     */
    public double dotProduct(Vector vec) {
        return xyz.d1() * vec.xyz.d1() + xyz.d2() * vec.xyz.d2() + xyz.d3() * vec.xyz.d3();
    }

    /**
     * Computes the cross product of this vector with another vector.
     * @param vec The vector to compute the cross product with.
     * @return A new Vector representing the cross product.
     */
    public Vector crossProduct(Vector vec) {
        return new Vector(xyz.d2() * vec.xyz.d3() - xyz.d3() * vec.xyz.d2(),
                xyz.d3() * vec.xyz.d1() - xyz.d1() * vec.xyz.d3(),
                xyz.d1() * vec.xyz.d2() - xyz.d2() * vec.xyz.d1());
    }

    /**
     * Computes the squared length (magnitude) of this vector.
     * @return The squared length of the vector.
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * Computes the length (magnitude) of this vector.
     * @return The length of the vector.
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * Normalizes this vector, returning a new unit vector.
     * @return A new Vector representing the normalized version of this vector.
     */
    public Vector normalize() {
        return new Vector(xyz.scale((double) 1 / this.length()));
    }

    /**
     * Checks if this vector is equal to another object.
     * @param vec The object to compare.
     * @return True if the object is a Vector with the same coordinates, false otherwise.
     */
    @Override
    public boolean equals(Object vec) {
        if (this == vec)
            return true;
        return vec instanceof Vector other && this.xyz.equals(other.xyz);
    }

    /**
     * Returns a string representation of the vector.
     * @return A string in the format "Vector: (x, y, z)".
     */
    @Override
    public String toString() {
        return "Vector: " + xyz.toString();
    }
}
