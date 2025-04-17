package geometries;

import java.util.List;

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

	@Override
	public List<Point> findIntersections(Ray ray) {
		if (ray.getHead() == center)
			return List.of(ray.getPoint(radius));
		Vector u = center.subtract(ray.getHead());
		double tm = u.dotProduct(ray.getDirection());
		double d = Math.sqrt(u.length() * u.length() - tm * tm);
		if (d >= radius)
			return null;
		else {
			double th = Math.sqrt(radius * radius - d * d);
			if ((tm - th) > 0 && (tm + th) > 0)
				return List.of((ray.getPoint(tm - th)), ray.getPoint(tm + th));
			else if ((tm - th) > 0 && (tm + th) <= 0)
				return List.of((ray.getPoint(tm - th)));
			else if ((tm - th) <= 0 && (tm + th) > 0)
				return List.of(ray.getPoint(tm + th));

			return null;
		}
	}
}

/*
 * @Override public List<Point> findIntersections(Ray ray){ List<Point>
 * intersections = new ArrayList<Point>(); Vector u =
 * center.subtract(ray.getHead()); double tm = u.dotProduct(ray.getDirection());
 * double d = Math.sqrt(u.length()*u.length() - tm*tm); if (d>=radius) { return
 * null; } else { double th = Math.sqrt(radius*radius - d*d); if ((tm-th)>0)
 * intersections.add(ray.getPoint(tm-th)); if ((tm+th)>0)
 * intersections.add(ray.getPoint(tm+th)); return intersections; } } }
 */