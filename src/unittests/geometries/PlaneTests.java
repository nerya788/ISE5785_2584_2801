/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import primitives.*;
import geometries.*;
import org.junit.jupiter.api.Test;

/**
 * Testing Planes
 * 
 * @authors Nerya and Yehuda
 */
class PlaneTests {
	final double DELTA = 0.00000000001;
	final double Zero = 0.0;

	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Point point213 = new Point(2, 1, 3);
		Point point312 = new Point(3, 1, 2);
		Point point112 = new Point(1, 1, 2);
		Plane plane = new Plane(point213, point312, point112);

		// ============================ Equivalence Partitions Tests
		// ============================

		assertEquals(new Vector(0, -1, 0), plane.getNormal(point213), "wrong normal of plane");
	}

	/**
	 * Test method for
	 * {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
	 */
	@Test
	void testPlane() {
		Point point123 = new Point(1, 2, 3);
		Point point224 = new Point(2, 2, 4);
		Point point321 = new Point(3, 2, 1);
		Plane plane = new Plane(point123, point321, point224);

		// ============================ Equivalence Partitions Tests
		// ============================

		assertEquals(Zero, plane.getNormal(point321).dotProduct(point321.subtract(point123)), "wrong normal of plane");
		assertEquals(Zero, plane.getNormal(point321).dotProduct(point224.subtract(point123)), "wrong normal of plane");

		// ============================ Boundary Values Tests
		// ====================================

		assertThrows(IllegalArgumentException.class, () -> new Plane(point123, point123, point224),
				"point1 and point2 same, cannot creat zero normal vector");
		assertThrows(IllegalArgumentException.class, () -> new Plane(point123, point224, point123),
				"point1 and point3 same, cannot creat zero normal vector");
		assertThrows(IllegalArgumentException.class, () -> new Plane(point224, point123, point123),
				"point2 and point3 same, cannot creat zero normal vector");
		assertThrows(IllegalArgumentException.class, () -> new Plane(point123, point123, point123),
				"all the point same, normal vector cannot be zero vector");

		Point point246 = new Point(2, 4, 6);
		Point point369 = new Point(3, 6, 9);
		assertThrows(IllegalArgumentException.class, () -> new Plane(point123, point246, point369),
				"all the point on same direct, normal vector cannot be zero vector");
	}
	
	/**
	 * Test method for {@link geometries.Plane#findIntersections(geometries.Ray)}.
	 */
	@Test
	void testFindIntersections() {
		final Point point234 = new Point(2, 3, 4);
		final Vector vector012 = new Vector(0, 1, 2);
		final Vector vector1m21 = new Vector(1, -2, 1);
		final Vector vector101 = new Vector(1, 0, 1);
		final Plane p = new Plane(new Point(2, 3, 4), vector1m21);
		
		// ============ Equivalence Partitions Tests ==============
		
		// TC01: Ray's line that starts outside the plane and have only one intersection.
		final var res1 = p.findIntersections(new Ray(new Point(4, 2, 1), new Vector(-1, 0, 1)));
		assertNotNull(res1, "Can't be null");
		assertEquals(1, res1.size(), "Wrong number of points");
		assertEquals(List.of(new Point(3.5, 2.5, 1.5)), res1, "Ray crosses plane in a different point");
		
		// TC02: Ray's line that starts outside the plane and dosn't have any intersection.
		assertNull(p.findIntersections(new Ray(new Point(7, 6, 7), new Vector(1, -2, -2))), "Ray starts after the plane");
		
		// ============ Boundary Values Tests ==============
		
		// TC03: Ray is parallel to the plane and not included. 
		assertNull(p.findIntersections(new Ray(new Point(2, 4, 4), vector012)), "Ray is parallel to the plane & not included");
		
		// TC04: Ray is parallel to the plane and included in the plane.
		assertNull(p.findIntersections(new Ray(point234, vector012)), "Ray is parllel to the plane and include in the plane");
		
		// TC05: Ray is orthogonal to the plane and starts before the plane.
		final var res5 = p.findIntersections(new Ray(new Point(0, 7, 2), vector1m21));
		assertNotNull(res5, "Can't be null");
		assertEquals(1, res5.size(), "Wrong number of points");
		assertEquals(List.of(new Point(2, 3, 4)), res5, "Ray crosses plane in a different point");
		
		// TC06: Ray is orthogonal to the plane and starts in the plane.
		assertNull(p.findIntersections(new Ray(new Point(3, 4, 5), vector1m21)), "Ray is parllel to the plane & the head of ray is in the plane");
		
		// TC07: Ray is orthogonal to the plane and starts after the plane.
		assertNull(p.findIntersections(new Ray(new Point(5, -3, 7), vector1m21)), "Ray starts after the plane");
		
		// TC08: ray is neither parallel or orthogonal to the plane and begins in the plane.
		assertNull(p.findIntersections(new Ray(new Point(3, 4, 5), vector101)), "Ray is not parllel to the plane and the head of ray is in the plane");
		
		// TC09: ray is neither parallel or orthogonal to the plane and begins in the same reference point of the plane.
		assertNull(p.findIntersections(new Ray(new Point(2, 3, 4), vector101)), "Ray is not parllel to the plane and the head of ray is the same as the plane point");
	}

}
