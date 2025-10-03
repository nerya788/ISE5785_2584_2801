package primitives;

import geometries.AABB;

/**
 * A factory class for creating Axis-Aligned Bounding Boxes (AABB) for
 * geometries. This class resides in the 'primitives' package to gain access to
 * the protected fields of other primitive classes like Point, which is
 * necessary for calculations.
 */
public class BoundingBoxFactory {

	/**
	 * Creates an AABB for a Sphere.
	 * 
	 * @param center The center point of the sphere.
	 * @param radius The radius of the sphere.
	 * @return A new AABB object that tightly encloses the sphere.
	 */
	public static AABB createForSphere(Point center, double radius) {
		Point min = new Point(center.xyz.d1() - radius, center.xyz.d2() - radius, center.xyz.d3() - radius);
		Point max = new Point(center.xyz.d1() + radius, center.xyz.d2() + radius, center.xyz.d3() + radius);
		return new AABB(min, max);
	}

	/**
	 * Creates an AABB for a polygon-based geometry (like Triangle or Polygon). It
	 * finds the minimum and maximum coordinates among all vertices.
	 * 
	 * @param vertices A list of the geometry's vertices.
	 * @return A new AABB object that tightly encloses the geometry.
	 */
	public static AABB createForPolygon(java.util.List<Point> vertices) {
		if (vertices == null || vertices.isEmpty()) {
			return null;
		}

		// Initialize min and max with the coordinates of the first vertex
		Point first = vertices.get(0);
		double minX = first.xyz.d1();
		double minY = first.xyz.d2();
		double minZ = first.xyz.d3();
		double maxX = first.xyz.d1();
		double maxY = first.xyz.d2();
		double maxZ = first.xyz.d3();

		// Iterate through the rest of the vertices to find the overall min and max
		for (int i = 1; i < vertices.size(); i++) {
			Point p = vertices.get(i);
			minX = Math.min(minX, p.xyz.d1());
			minY = Math.min(minY, p.xyz.d2());
			minZ = Math.min(minZ, p.xyz.d3());
			maxX = Math.max(maxX, p.xyz.d1());
			maxY = Math.max(maxY, p.xyz.d2());
			maxZ = Math.max(maxZ, p.xyz.d3());
		}

		return new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
	}

	/**
	 * Merges two AABBs into a single AABB that contains both.
	 * 
	 * @param b1 The first bounding box. Can be null.
	 * @param b2 The second bounding box. Can be null.
	 * @return A new AABB that is the union of the two boxes.
	 */
	public static AABB union(AABB b1, AABB b2) {
		// If one box is null, the union is the other box.
		if (b1 == null)
			return b2;
		if (b2 == null)
			return b1;

		// Find the overall minimum coordinates
		double minX = Math.min(b1.min.xyz.d1(), b2.min.xyz.d1());
		double minY = Math.min(b1.min.xyz.d2(), b2.min.xyz.d2());
		double minZ = Math.min(b1.min.xyz.d3(), b2.min.xyz.d3());

		// Find the overall maximum coordinates
		double maxX = Math.max(b1.max.xyz.d1(), b2.max.xyz.d1());
		double maxY = Math.max(b1.max.xyz.d2(), b2.max.xyz.d2());
		double maxZ = Math.max(b1.max.xyz.d3(), b2.max.xyz.d3());

		return new AABB(new Point(minX, minY, minZ), new Point(maxX, maxY, maxZ));
	}

	/**
	 * Creates a conservative AABB for a Cylinder.
	 * 
	 * @param axis   The central axis of the cylinder.
	 * @param radius The radius of the cylinder.
	 * @param height The height of the cylinder.
	 * @return A new AABB that encloses the cylinder.
	 */
	public static AABB createForCylinder(Ray axis, double radius, double height) {
		Point p1 = axis.getHead();
		Point p2 = axis.getPoint(height);

		// Bounding box of the two endpoints of the axis and expand the box by the
		// radius in all directions
		return new AABB(
				new Point(Math.min(p1.xyz.d1() - radius, p2.xyz.d1() - radius),
						Math.min(p1.xyz.d2() - radius, p2.xyz.d2() - radius),
						Math.min(p1.xyz.d3() - radius, p2.xyz.d3() - radius)),
				new Point(Math.max(p1.xyz.d1() + radius, p2.xyz.d1() + radius),
						Math.max(p1.xyz.d2() + radius, p2.xyz.d2() + radius),
						Math.max(p1.xyz.d3() + radius, p2.xyz.d3() + radius)));
	}

}