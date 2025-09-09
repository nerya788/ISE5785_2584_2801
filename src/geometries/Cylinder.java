package geometries;

import primitives.*;

import java.util.List;

import geometries.Intersectable.Intersection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.LinkedList;

/**
 * Represents a finite cylinder in 3D space, defined by a central axis, a
 * radius, and a height. It extends the Tube class by adding a height
 * constraint.
 */
public class Cylinder extends Tube {

	private final double height; // The height of the cylinder

	/**
	 * Constructs a cylinder with a specified height, axis, and radius.
	 *
	 * @param newHeight The height of the cylinder
	 * @param ray       The central axis of the cylinder
	 * @param radius    The radius of the cylinder
	 */
	public Cylinder(Ray ray, double radius, double newHeight) {
		super(ray, radius);
		height = newHeight;
	}

	/**
	 * Returns the normal vector to the cylinder at a given point on its surface.
	 *
	 * @param point A point on the surface of the cylinder
	 * @return The normal vector at the given point
	 */
	public Vector getNormal(Point point) {
		if (point.equals(axis.getHead()) || point.subtract(axis.getHead()).dotProduct(axis.getDirection()) == 0)
			return axis.getDirection().scale(-1).normalize();
		else if (point.equals(axis.getHead().add(axis.getDirection().scale(height))) || point
				.subtract(axis.getHead().add(axis.getDirection().scale(height))).dotProduct(axis.getDirection()) == 0)
			return axis.getDirection().normalize();
		else {
			Vector u = point.subtract(axis.getHead());
			double t = axis.getDirection().dotProduct(u);
			Point o = axis.getHead().add(axis.getDirection().scale(t));
			return point.subtract(o).normalize();
		}
	}

	@Override
	/**
	 * Finds and returns intersection points between the cylinder and a given ray.
	 * The method considers the intersections with the infinite tube, as well as the
	 * top and bottom bases of the cylinder.
	 *
	 * @param ray The ray to find intersections with.
	 * @return A list of intersection points, or {@code null} if there are no
	 *         intersections.
	 */
	public List<Intersection> calculateIntersectionsHelper(Ray ray) {
		List<Point> result = null;// new LinkedList<>()

		// Step 1: Find intersections with the infinite tube part
		List<Intersection> tubeIntersections = super.calculateIntersectionsHelper(ray);
		if (tubeIntersections != null) {
			for (Intersection pt : tubeIntersections) {
				// Check if point lies between the two bases of the finite cylinder
				double projection = axis.getDirection().dotProduct(pt.point.subtract(axis.getHead()));
				if (projection >= 0 && projection <= height) {
					if (result == null)
						result = new LinkedList<>();
					result.add(pt.point);

				}
			}
		}

		// Step 2: Check intersection with bottom base
		Point baseCenter = axis.getHead();
		Vector va = axis.getDirection();
		double denom = va.dotProduct(ray.getDirection());

		if (!Util.isZero(denom) && (!baseCenter.equals(ray.getHead()))) {
			double t = va.dotProduct(baseCenter.subtract(ray.getHead())) / denom;
			if (t > 0) {
				Point p = ray.getPoint(t);
				if (p.equals(baseCenter) || p.subtract(baseCenter).lengthSquared() <= radius * radius - 1e-12) {
					if (result == null)
						result = new LinkedList<>();
					result.add(p);

				}
			}
		}

		// Step 3: Check intersection with top base
		Point topCenter = axis.getHead().add(va.scale(height));
		denom = va.dotProduct(ray.getDirection());
		if (!Util.isZero(denom) && (!topCenter.equals(ray.getHead()))) {
			double t = va.dotProduct(topCenter.subtract(ray.getHead())) / denom;
			if (t > 0) {
				Point p = ray.getPoint(t);
				if (p.equals(topCenter) || p.subtract(topCenter).lengthSquared() <= radius * radius - 1e-12) {
					if (result == null)
						result = new LinkedList<>();
					result.add(p);
				}
			}
		}

		// return the list result
		if (result == null || result.isEmpty())
			return null;

		if (result.size() == 2)
			if (result.get(0).subtract(ray.getHead()).length() < result.get(1).subtract(ray.getHead()).length())
				return List.of(new Intersection(this, result.get(0)), new Intersection(this, result.get(1)));
			else
				return List.of(new Intersection(this, result.get(1)), new Intersection(this, result.get(0)));

		return List.of(new Intersection(this, result.get(0)));
	}
}
