/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

import org.junit.jupiter.api.Test;

/**
 * 
 */
class VectorTests {

	/**
	 * Test method for {@link primitives.Vector#Vector(double, double, double)}.
	 */
	@Test
	void testVector3Double() {
		// ============================ Equivalence Partitions Tests ============================
		Vector vec123 = new Vector (1,2,3);
		assertEquals(new Vector (1,2,3),vec123 ,"wrong three Double constractor allocation");
		
		// ============================ Boundary Values Tests ============================
		assertThrowsExactly(IllegalArgumentException.class, () -> new Vector (0,0,0), "Vector constractor of three Double cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#Vector(primitives.Double3)}.
	 */
	@Test
	void testVectorDouble3() {
		// ============================ Equivalence Partitions Tests ============================
				Double3 dou123 = new Double3 (1,2,3);
				Double3 dou000 = new Double3 (0,0,0);
				Vector vec123 = new Vector (1,2,3);
				assertEquals(new Vector (dou123),vec123 ,"wrong Double3 constractor allocation");
				
		// ============================ Boundary Values Tests ============================
				assertThrowsExactly(IllegalArgumentException.class, () -> new Vector (dou000), "Vector onstractor of Double3 cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		// ============================ Equivalence Partitions Tests ============================
		Vector vec123 = new Vector(1,2,3);
		Vector vec234 = new Vector(-2,3,-4);
		assertEquals(new Vector (-1,5,-1),vec123.add(vec234) ,"wrong resulte for connecting two vectors");
		
		// ============================ Boundary Values Tests ============================
		Vector vecMinus123 = new Vector(-1,-2,-3);
		assertThrowsExactly(IllegalArgumentException.class, () -> vec123.add(vecMinus123) ,"Vector onstractor of Double3 cannot be zero vector nji");
		
	}

	/**mm
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		// ============================ Equivalence Partitions Tests ============================
		Vector vec135 = new Vector(1,3,5);
		Double num = 2.0;
		assertEquals(new Vector (2,6,10),vec135.scale(num) ,"wrong resulte for scaling vector");
		
		// ============================ Boundary Values Tests ============================
		Double ZERO = 0.0; 
		assertThrowsExactly(IllegalArgumentException.class, () -> vec135.scale(ZERO) ,"Vector onstractor of Double3 cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		// ============ Equivalence Partitions Tests ==============
		 Vector vec123 = new Vector(1,2,3);
		 Vector vec224 = new Vector(2,2,4);
		 assertEquals(vec123.crossProduct(vec224),new Vector(2,2,-2),  "crossProduct() wrong result");
		 
		 // =============== Boundary Values Tests ==================
		 assertThrows(IllegalArgumentException.class, () -> vec123.crossProduct(new Vector(2,4,6)), "Vector constractor of three Double cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {
		// ============================ Equivalence Partitions Tests ============================
		
		// ============================ Boundary Values Tests ============================
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		// ============================ Equivalence Partitions Tests ============================
		
		// ============================ Boundary Values Tests ============================
	//	fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============================ Equivalence Partitions Tests ============================
		
		// ============================ Boundary Values Tests ============================
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============================ Equivalence Partitions Tests ============================
		
		// ============================ Boundary Values Tests ============================
		//fail("Not yet implemented");
	}

}
