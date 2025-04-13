/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * Testing Points
 * 
 * @authors Neriya_and_Yehuda
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		Point point23m5 = new Point(2, 3, -5);
		Vector vec123 = new Vector(1, 2, 3);
		Vector vecm2m35 = new Vector(-2, -3, 5);

		// ============================ Equivalence Partitions Tests
		// ============================
		assertEquals(point23m5.add(vec123), new Point(3, 5, -2), "wrong resulte for adding vector to point");

		// ============================ Boundary Values Tests
		// ============================
		assertEquals(point23m5.add(vecm2m35), Point.ZERO, "wrong resulte zero for adding vector to point");
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}. No have
	 * Boundary Values Tests because no zero vector was exception.
	 */
	@Test
	void testSubtract() {
		Point point23m5 = new Point(2, 3, -5);
		Point point123 = new Point(1, 2, 3);

		// ============================ Equivalence Partitions Tests
		// ============================
		assertEquals(point23m5.subtract(point123), new Vector(1, 1, -8),
				"wrong resulte vector for subtract point to point");

	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		Point point23m5 = new Point(2, 3, -5);
		Point point123 = new Point(1, 2, 3);
		assertEquals(point23m5.distanceSquared(point123), 66, "wrong resulte distance squared");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		Point point23m5 = new Point(2, 3, -5);
		Point point123 = new Point(1, 2, 3);
		assertEquals(point23m5.distance(point123), Math.sqrt(66), "wrong resulte distance squared");
	}

}
