/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Testing Triangles
 * 
 * @author Nerya
 */
class TriangleTests {
	double DELTA = 0.00000000001;

	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Point point213 = new Point(2, 1, 3);
		Point point312 = new Point(3, 1, 2);
		Point point112 = new Point(1, 1, 2);
		Point[] vertices = new Point[] { point213, point312, point112 };
		Triangle triangle = new Triangle(vertices);

		// ============================ Equivalence Partitions Tests
		// ============================

		assertEquals(new Vector(0, -1, 0), triangle.getNormal(point213), "wrong normal of plane");
	}
	/**
	 * Test method for {@link geometries.Triangle#findIntersections(geometries.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		final Point p234 = new Point(2, 3, 4);
		final Point p325 = new Point(3, 2, 5);
		final Point p453 = new Point(4, 5, 3);
		
		final Triangle t = new Triangle(new Point[] {p234, p325, p453});
		
		// ============ Equivalence Partitions Tests ==============
		
		// TC01: The intersection with the triangle plane is inside the triangle.
		final var res1 = t.findIntersections(new Ray(new Point(3, 3, 7), new Vector(0, 0, -4)));
		assertNotNull(res1, "Can't be null");
		assertEquals(1, res1.size(), "Wrong number of points");
		assertEquals(List.of(new Point(3.0, 3.0, 4.25)), res1, "Ray crosses the triangle exatly in one point");
		
		// TC02: The intersection with the triangle plane is outside the triangle in front of one of the edges.
		assertNull(t.findIntersections(new Ray(new Point(3.9, 3.1, 6), new Vector(0, 0, -2.3))), "Ray intersect outside the triangle, in front of one of the edges");
		
		// TC03: The intersection with the triangle plane is outside the triangle in front of one of the vertices.
		assertNull(t.findIntersections(new Ray(new Point(4.5, 5.5, 6), new Vector(0, 0, -2.5))), "Ray intersect outside the triangle, in front of one of the vertices");
		
		// ============ Boundary Values Tests ==============
		
		// TC04: The intersection with the triangle plane is on one of the edges.
		final Vector vector001 = new Vector(0, 0, 1);
		assertNull(t.findIntersections(new Ray(new Point(2.5, 2.5, 0), vector001)), "Ray intersect is on one of the triangle edges");
		
		// TC05: The intersection with the triangle plane is on one of the vertices.
		assertNull(t.findIntersections(new Ray(new Point(2, 3, 0), vector001)), "Ray intersect is on one of the triangle vertices");
		
		// TC06: The intersection with the triangle plane is on one of the edges extension.
		assertNull(t.findIntersections(new Ray(new Point(4, 1, 0), vector001)), "Ray intersect is on one of the triangle edges extension (beyond edge)");
	}

}
