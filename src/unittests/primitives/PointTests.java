/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;

/**
 * 
 */
class PointTests {

	/**
	 * Test method for {@link primitives.Point#add(primitives.Vector)}.
	 */
	@Test
	void testAdd() {
		Point poi = new Point(2,3,-5);
		Vector vec = new Vector(1,2,3);
		Vector vec2 = new Vector(-2,-3,5);
		
		
		// ============================ Equivalence Partitions Tests ============================
		assertEquals(poi.add(vec),new Point(3,5,2),"wrong resulte for adding vector to point");
	
		// ============================ Boundary Values Tests ============================
		assertEquals(poi.add(vec2),Point.ZERO,"wrong resulte for adding vector to point");
		
		
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 */
	@Test
	void testSubtract() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		fail("Not yet implemented");
	}

}
