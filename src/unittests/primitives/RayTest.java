/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import primitives.*;

/**
 * 
 */
class RayTest {

	/**
	 * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}.
	 */
	@Test
	void testFindClosestPoint() {
		Ray ray001 = new Ray(new Point(1, 0, 0), new Vector(0, 0, 1));

		Point p102 = new Point(1, 0, 2);
		Point p103 = new Point(1, 0, 3);
		Point p104 = new Point(1, 0, 4);

		final List<Point> list1 = List.of();
		final List<Point> list2 = List.of(p102, p103, p104);
		final List<Point> list3 = List.of(p104, p103, p102);
		final List<Point> list4 = List.of(p104, p102, p103);

		// ============================ Equivalence Partitions Tests
		// ============================
		// The first point is in the middle.
		Point point4 = ray001.findClosestPoint(list4);
		assertNotNull(point4, "Ray intersectable to point");
		assertEquals(p102, point4, "Ray crosses plant the tube");

		// ============================ Boundary Values Tests
		// ===================================
		// the list empty
		Point point1 = ray001.findClosestPoint(list1);
		assertNull(point1, "Ray no intersectable to point");

		// The first point is in the first list
		Point point2 = ray001.findClosestPoint(list2);
		assertNotNull(point2, "Ray intersectable to point");
		assertEquals(p102, point2, "Ray crosses plant the tube");

		// The first point is in the final list
		Point point3 = ray001.findClosestPoint(list3);
		assertNotNull(point3, "Ray intersectable to point");
		assertEquals(p102, point3, "Ray crosses plant the tube");
	}

}
