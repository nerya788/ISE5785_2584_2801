package geometries;

import primitives.*;

import primitives.Point;

/**
 * Represents a general geometric shape in 3D space. This is an abstract class
 * that serves as a base for specific geometric shapes.
 */
public abstract class Geometry extends Intersectable {

	protected Color emission = Color.BLACK;

    /**
     * Sets the emission color of the geometry.
     * Chaining-style setter as part of Builder design pattern.
     *
     * @param emission the emission color to set
     * @return this geometry instance (for method chaining)
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Color getEmission() {
        return this.emission;
    }

	/**
	 * Returns the normal vector to the geometry at a given point.
	 *
	 * @param point A point on the surface of the geometry
	 * @return The normal vector at the given point
	 */
	public abstract Vector getNormal(Point point);

}
