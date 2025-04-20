package geometries;

import primitives.*;

import java.util.List;

import geometries.*;

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
	
	@Override
	public List<Point> findIntersections(Ray ray) {
		return null;
	}
}
