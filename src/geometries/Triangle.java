package geometries;

import primitives.*;

import java.util.List;

/**
 * Represents a triangle in 3D space. This class extends Polygon, assuming a
 * triangle is a specific case of a polygon with three sides.
 */
public class Triangle extends Polygon {

	public Triangle(Point[] vertices) {
		super(vertices);
	}

	@Override
	public Vector getNormal(Point point) {
		return super.getNormal(point);
	}
	
	/**
     * Finds the intersection points between a given {@link Ray} and the triangle.
     * <p>
     * If there are intersection points, they are calculated and returned as a list.
     * If there are no intersection points (the ray does not intersect the triangle),
     * this method returns {@code null}.
     *
     * @param ray The {@link Ray} to check for intersections with the triangle
     * @return A list of intersection points as {@link Point} objects, or {@code null} if none exist
     */
	@Override
	public List<Point> findIntersections(Ray ray) {
		final List<Point> planeIntersections = plane.findIntersections(ray);
		if (planeIntersections == null)
			return null;
		
		final Point planeIntersection = planeIntersections.getFirst();
		
		final Point b = this.vertices.get(0);
		final Point a = this.vertices.get(1);
		final Point c = this.vertices.get(2);
		
		final Vector v0 = c.subtract(a);
		final Vector v1 = b.subtract(a);
		final Vector v2 = planeIntersection.subtract(a);
		
		final double d00 = v0.dotProduct(v0);
		final double d01 = v0.dotProduct(v1);
		final double d11 = v1.dotProduct(v1);
		final double d20 = v2.dotProduct(v0);
		final double d21 = v2.dotProduct(v1);
		
		final double denom = d00 * d11 - d01 * d01;
		
		final double v = (d11 * d20 - d01 * d21) / denom;
		final double w = (d00 * d21 - d01 * d20) / denom;
		final double u = 1.0 - v - w;
		
		if (u > 0 && v > 0 && w > 0)
			return planeIntersections;
		
		return null;
	}
}
