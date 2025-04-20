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
	
	public static final double Zero = 0.0;
	
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

	/**
     * Finds the intersection points between a given {@link Ray} and the plane.
     * 
     * If there are intersection points, they are calculated and returned as a list.
     * If there are no intersection points (the ray does not intersect the plane),
     * this method returns {@code null}.
     * if the ray head and the plane head is equals the result is an empty list.
     * if the ray is a subset of the plane (infinity points) then returns an empty list.
     *
     * @param ray The {@link Ray} to check for intersections with the plane
     * @return A list of intersection points as {@link Point} objects, or {@code null} if none exist
     */
	@Override
	public List<Point> findIntersections(Ray ray) {
		if (ray.getHead().equals(head))
			return List.of();
		Vector v = ray.getHead().subtract(head);
		if (Util.isZero(ray.getDirection().dotProduct(direction))) {
			if (Util.isZero(v.dotProduct(direction)))
				return List.of();
			return null;
		}
		if (Util.isZero(v.dotProduct(direction)))
			return List.of();
		Vector v1 = head.subtract(ray.getHead());
		double numerator = v1.dotProduct(direction);
		double denominator = ray.getDirection().dotProduct(direction);
		double t = Util.alignZero(numerator / denominator);
		if (t <= Zero)
			return null;
		Point intersection = ray.getHead().add(ray.getDirection().scale(t));
		return List.of(intersection);
	}	
}
