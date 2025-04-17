package geometries;

import java.util.List;

import primitives.*;

/**
 * Represents an infinite cylindrical tube in 3D space, defined by a central
 * axis and a radius.
 */
public class Tube extends RadialGeometry {

	protected final Ray axis; // The central axis of the tube

	/**
	 * Constructs a tube with a given axis and radius.
	 *
	 * @param ray    The central axis of the tube
	 * @param radius The radius of the tube
	 */
	public Tube(Ray ray, double radius) {
		super(radius);
		axis = ray;
	}

	/**
	 * Returns the normal vector to the tube at a given point on its surface.
	 *
	 * @param point A point on the surface of the tube
	 * @return The normal vector at the given point
	 */
	public Vector getNormal(Point point) {
		if (point.equals(axis.getHead()) || point.subtract(axis.getHead()).dotProduct(axis.getDirection()) == 0)
			return axis.getDirection().scale(-1).normalize();
		else {
			Vector u = point.subtract(axis.getHead());
			double t = axis.getDirection().dotProduct(u);
			Point o = axis.getHead().add(axis.getDirection().scale(t));
			return point.subtract(o).normalize();
		}
	}

	@Override
	public List<Point> findIntersections(Ray ray) {
	    /**
	     * Finds the intersection points of a given ray with the infinite tube.
	     * 
	     * @param ray The ray to check for intersections with the tube.
	     * @return A list of intersection points (may be empty if there are no intersections).
	     * If there are two intersections, they will be returned in order of distance from the ray's head.
	     * If there is only one intersection, it will be returned as a single element list.
	     * If there are no intersections, null will be returned.
	     */

		// Compute deltaP = p0 - o (assume not equal to avoid zero vector)
		Vector deltaP = ray.getHead().equals(axis.getHead()) ? axis.getDirection() : ray.getHead().subtract(axis.getHead());

		// Compute the orthogonal component of the ray direction (vOrth) relative to the tube axis.
		double vDotVa = ray.getDirection().dotProduct(axis.getDirection());

		Vector vOrth;
		if (Util.isZero(vDotVa))
			// If the ray is parallel to the axis
			vOrth = ray.getDirection();
		else if (ray.getDirection().equals(axis.getDirection().scale(vDotVa)))
			// If the ray direction is aligned with the axis, return null since they don't intersect.
			return null;
		else
			// Otherwise, calculate the perpendicular component of the ray direction.
			vOrth = ray.getDirection().subtract(axis.getDirection().scale(vDotVa));

	    // Compute the orthogonal component of deltaP (dpOrth) relative to the tube axis.
		Vector dpOrth;
		double dpDotVa = deltaP.dotProduct(axis.getDirection());
		if (Util.isZero(deltaP.lengthSquared()) || deltaP.normalize().equals(axis.getDirection())
				|| deltaP.normalize().equals(axis.getDirection().scale(-1)))
			/** If deltaP is zero or parallel to the axis, no orthogonal component
			*Therefore, we will use an alternative calculation, in which we will check what t
			*for which the beam will reach the tube
			*/
			dpOrth = null;
		else if (Util.isZero(dpDotVa))
			// If deltaP is orthogonal to the axis, keep it as is
			dpOrth = deltaP;
		else
			// Otherwise, subtract the projection onto the axis
			dpOrth = deltaP.subtract(axis.getDirection().scale(dpDotVa));

		// a = squared length of v's orthogonal component
		double a = vOrth.lengthSquared();
		// b = 2 times dot product of vOrth and dpOrth (or 0 if dpOrth is null)
		double b = (dpOrth == null) ? 0 : 2 * vOrth.dotProduct(dpOrth);
		// c = squared distance from ray to axis minus radius^2 (with epsilon)
		double c = (dpOrth == null) ? -(radius * radius - 1e-12) : dpOrth.lengthSquared() - (radius * radius - 1e-12);

		double discriminant = b * b - 4 * a * c;

		if (discriminant < 0 || a == 0) {
			return null; // No intersection
		}

		double sqrtD = Math.sqrt(discriminant);
		double t1 = (-b + sqrtD) / (2 * a);
		double t2 = (-b - sqrtD) / (2 * a);

		// Get intersection points for positive t values
		Point p1 = null, p2 = null;
		if (t1 > 0)
			p1 = ray.getPoint(t1);
		if (t2 > 0)
			p2 = ray.getPoint(t2);

		// Return valid intersection points
		if ((p1 != null && p2 != null) && (!p2.equals(ray.getHead())))
			return List.of(p2, p1);
		if (p1 != null)
			return List.of(p1);
		if (p2 != null)
			return List.of(p2);
		return null;
	}

}
