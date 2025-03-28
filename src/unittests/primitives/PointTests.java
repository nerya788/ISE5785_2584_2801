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
		assertEquals(poi.add(vec),new Point(3,5,-2),"wrong resulte for adding vector to point");
	
		// ============================ Boundary Values Tests ============================
		assertEquals(poi.add(vec2),Point.ZERO,"wrong resulte zero for adding vector to point");	
	}

	/**
	 * Test method for {@link primitives.Point#subtract(primitives.Point)}.
	 * No have Boundary Values Tests because no zero vector was exception. 
	 */
	@Test
	void testSubtract() {
		Point poi = new Point(2,3,-5);
		Point poi2 = new Point(1,2,3);
	
		// ============================ Equivalence Partitions Tests ============================
		assertEquals(poi.subtract(poi2),new Vector(1,1,-8),"wrong resulte vector for subtract point to point");

	}

	/**
	 * Test method for {@link primitives.Point#distanceSquared(primitives.Point)}.
	 */
	@Test
	void testDistanceSquared() {
		Point poi = new Point(2,3,-5);
		Point poi2 = new Point(1,2,3);
		assertEquals(poi.distanceSquared(poi2),66 ,"wrong resulte distance squared"); //8.12403840463596 Math.sqrt(66)
	}

	/**
	 * Test method for {@link primitives.Point#distance(primitives.Point)}.
	 */
	@Test
	void testDistance() {
		Point poi = new Point(2,3,-5);
		Point poi2 = new Point(1,2,3);
		assertEquals(poi.distance(poi2),Math.sqrt(66) ,"wrong resulte distance squared"); //8.12403840463596 
	}

}
