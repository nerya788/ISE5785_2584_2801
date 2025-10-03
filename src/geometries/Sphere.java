package geometries;

import java.util.List;

import geometries.Intersectable.Intersection;
import primitives.*;

/**
 * Represents a sphere in 3D space, defined by a center point and a radius.
 */
public class Sphere extends RadialGeometry {
	private final Point center; // The center of the sphere

	/**
	 * Constructs a sphere with a given center point and radius.
	 *
	 * @param point  The center of the sphere
	 * @param radius The radius of the sphere
	 */
	public Sphere(Point point, double radius) {
		super(radius);
		center = point;
	}

	/**
	 * Returns the normal vector to the sphere at a given point on its surface.
	 *
	 * @param point A point on the surface of the sphere
	 * @return The normal vector at the given point
	 */
	public Vector getNormal(Point point) {

		return (point.subtract(center)).normalize();
	}

	/**
	 * Finds the intersection points between a given {@link Ray} and the sphere.
	 * <p>
	 * If there are intersection points, they are calculated and returned as a list.
	 * If there are no intersection points (the ray does not intersect the sphere),
	 * this method returns {@code null}.
	 *
	 * @param ray The {@link Ray} to check for intersections with the sphere
	 * @return A list of intersection points as {@link Point} objects, or
	 *         {@code null} if none exist
	 */

	@Override
	public List<Intersection> calculateIntersectionsHelper(Ray ray) {
		if (ray.getHead() == center)
			return List.of(new Intersection(this, ray.getPoint(radius)));
		Vector u = center.subtract(ray.getHead());
		double tm = u.dotProduct(ray.getDirection());
		double d = Math.sqrt(u.length() * u.length() - tm * tm);
		if (d >= radius)
			return null;
		else {
			double th = Math.sqrt(radius * radius - d * d);
			if (Util.alignZero(tm - th) > 0 && Util.alignZero(tm + th) > 0)
				return List.of(new Intersection(this, (ray.getPoint(tm - th))),
						new Intersection(this, ray.getPoint(tm + th)));
			else if (Util.alignZero(tm - th) > 0 && Util.alignZero(tm + th) <= 0)
				return List.of(new Intersection(this, (ray.getPoint(tm - th))));
			else if (Util.alignZero(tm - th) <= 0 && Util.alignZero(tm + th) > 0)
				return List.of(new Intersection(this, (ray.getPoint(tm + th))));

			return null;
		}
	}

	@Override
	protected AABB createBoundingBox() {
		return primitives.BoundingBoxFactory.createForSphere(this.center, this.radius);
	}
}
