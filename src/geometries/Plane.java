package geometries;

import java.util.List;

import primitives.*;

/**
 * Represents a plane in 3D space, defined by either three points or a point and
 * a normal vector.
 */
public class Plane extends Geometry {
	private final Point head; // A point on the plane
	private final Vector direction; // The normal vector to the plane

	/**
	 * Constructs a plane from three points in 3D space.
	 * 
	 * @param point1 First point defining the plane
	 * @param point2 Second point defining the plane
	 * @param point3 Third point defining the plane
	 */
	public Plane(Point point1, Point point2, Point point3) {
		head = point1;
		direction = (point3.subtract(point1).crossProduct(point2.subtract(point1))).normalize();
	}

	/**
	 * Constructs a plane from a point and a normal vector.
	 * 
	 * @param point  A point on the plane
	 * @param vector The normal vector to the plane
	 */
	public Plane(Point point, Vector vector) {
		head = point;
		direction = vector.normalize();
	}

	/**
	 * Returns the normal vector of the plane.
	 * 
	 * @param point A point on the plane (not used in current implementation)
	 * @return The normal vector of the plane
	 */
	@Override
	public Vector getNormal(Point point) {
		return direction;
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}
