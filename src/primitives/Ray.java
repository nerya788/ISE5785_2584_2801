package primitives;

import primitives.Util;
import java.util.List;
import geometries.Intersectable.Intersection;
//import renderer.SimpleRayTracer;

/**
 * Represents a ray in 3D space, defined by a starting point and a direction
 * vector.
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
	 * @param vec   The direction vector of the ray.
	 * @throws IllegalArgumentException if the given vector is not normalized.
	 */
	public Ray(Point point, Vector vec) {
		head = point;
		direction = vec.normalize();
	}

	public Ray(Point point, Vector vec, Vector normal, double DELTA) {

		direction = vec.normalize();

		if (!Util.isZero(direction.dotProduct(normal)))
			head = point.add(normal.scale(direction.dotProduct(normal) > 0 ? DELTA : -DELTA));
		else
			head = point;
	}

	/**
	 * getter head
	 */
	public Point getHead() {
		return head;
	}

	/**
	 * getter direction
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * return the point that on distance t on the ray
	 */
	public Point getPoint(double t) {
		if (Util.isZero(t))
			return head;
		return head.add(direction.scale(t));
	}

	/**
	 * Finds and returns the point from a given list that is closest to the head of
	 * this ray.
	 * <p>
	 * If the list is empty or null, returns {@code null}.
	 *
	 * @param list A list of {@link Point} objects to search through.
	 * @return The point in the list that is closest to the head of the ray, or
	 *         {@code null} if the list is empty or null.
	 */
	public Point findClosestPoint(List<Point> points) {
		return points == null || points.isEmpty() ? null
				: findClosestIntersection(points.stream().map(p -> new Intersection(null, p)).toList()).point;
	}

	public Intersection findClosestIntersection(List<Intersection> list) {
		if (list == null || list.isEmpty())
			return null;
		if (list.size() == 1)
			return list.get(0);

		Intersection closest = list.get(0);
		double minDistance = head.distance(closest.point);

		for (int i = 1; i < list.size(); i++) {
			double distance = head.distance(list.get(i).point);
			if (distance < minDistance) {
				minDistance = distance;
				closest = list.get(i);
			}
		}

		return closest;
	}

	/**
	 * Checks whether this ray is equal to another object.
	 * 
	 * @param obj The object to compare.
	 * @return True if the object is a Ray with the same head and direction, false
	 *         otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return (obj instanceof Ray other) && this.head.equals(other.head) && this.direction.equals(other.direction);
	}

	/**
	 * Returns a string representation of the ray.
	 * 
	 * @return A string describing the ray in the format "Ray: head=(x, y, z),
	 *         direction=(dx, dy, dz)".
	 */
	@Override
	public String toString() {
		return "Ray: " + head + " " + direction;
	}

	/**
	 * Checks if this ray intersects a given Axis-Aligned Bounding Box (AABB).
	 * <p>
	 * This method uses the highly efficient Slab Test algorithm (an optimized
	 * version of Kay/Kajiya). It is designed for fast rejection (early exit) and
	 * does not calculate the actual intersection points.
	 *
	 * @param box The AABB to check for intersection with this ray.
	 * @return true if the ray intersects the box, false otherwise.
	 */
	public boolean isIntersecting(geometries.AABB box) {
		Point invDir = new Point(1.0 / this.getDirection().xyz.d1(), 1.0 / this.getDirection().xyz.d2(),
				1.0 / this.getDirection().xyz.d3());

		double t1 = (box.min.xyz.d1() - this.getHead().xyz.d1()) * invDir.xyz.d1();
		double t2 = (box.max.xyz.d1() - this.getHead().xyz.d1()) * invDir.xyz.d1();
		double t3 = (box.min.xyz.d2() - this.getHead().xyz.d2()) * invDir.xyz.d2();
		double t4 = (box.max.xyz.d2() - this.getHead().xyz.d2()) * invDir.xyz.d2();
		double t5 = (box.min.xyz.d3() - this.getHead().xyz.d3()) * invDir.xyz.d3();
		double t6 = (box.max.xyz.d3() - this.getHead().xyz.d3()) * invDir.xyz.d3();

		double tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
		double tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

		if (tmax < 0)
			return false;
		if (tmin > tmax)
			return false;

		return true;
	}
}
