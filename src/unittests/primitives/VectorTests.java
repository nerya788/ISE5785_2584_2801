/**
 * 
 */
package unittests.primitives;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;

import org.junit.jupiter.api.Test;

/**
 * Testing Vectors
 * 
 * @authors Neriya_and_Yehuda
 */
class VectorTests {
	double DELTA = 0.00000000001;

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		// ============================ Equivalence Partitions Tests ======================

		Vector vec123 = new Vector(1, 2, 3);
		Vector vecm23m4 = new Vector(-2, 3, -4);
		assertEquals(new Vector(-1, 5, -1), vec123.add(vecm23m4), "wrong resulte for add two vectors");

		// ============================ Boundary Values Tests =============================
		Vector vecMinus123 = new Vector(-1, -2, -3);
		assertThrows(IllegalArgumentException.class, () -> vec123.add(vecMinus123),
				"Vector onstractor of Double3 cannot be zero vector");

	}

	void testSubVector() {
		// ============================ Equivalence Partitions Tests ======================
		Vector vec123 = new Vector(1, 2, 3);
		Vector vecm23m4 = new Vector(-2, 3, -4);
		assertEquals(new Vector(3, -1, 7), vec123.subtract(vecm23m4), "wrong resulte for subtract two vectors");

		// ============================ Boundary Values Tests =============================
		assertThrows(IllegalArgumentException.class, () -> vec123.subtract(vec123),
				"Vector onstractor of Double3 cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		// ============================ Equivalence Partitions Tests ========================
		Vector vec135 = new Vector(1, 3, 5);
		Double num = 2.0;
		assertEquals(new Vector(2, 6, 10), vec135.scale(num), "wrong resulte for scaling vector");

		// ============================ Boundary Values Tests ================================
		Double ZERO = 0.0;
		assertThrows(IllegalArgumentException.class, () -> vec135.scale(ZERO),
				"Vector onstractor of Double3 cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
	 */
	@Test
	void testDotProduct() {
		// ============ Equivalence Partitions Tests ==============
		Vector vec123 = new Vector(1, 2, 3);
		Vector vec22m1 = new Vector(2, 2, -1);
		assertEquals( 3, vec123.dotProduct(vec22m1), "crossProduct() wrong result");

		// =============== Boundary Values Tests ==================
		assertThrows(IllegalArgumentException.class, () -> vec123.crossProduct(new Vector(2, 4, 6)),
				"Vector constractor of three Double cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
	 */
	@Test
	void testCrossProduct() {

		Vector vec123 = new Vector(1, 2, 3);
		Vector vec224 = new Vector(2, 2, 4);
		Vector vec22m2 = new Vector(2, 2, -2);
		Vector vec246 = new Vector(2, 4, 6);

		// ============================ Equivalence Partitions Tests ======================
		assertEquals(vec22m2, vec123.crossProduct(vec224), "cross product give wrong result");

		// ============================ Boundary Values Tests =============================
		assertThrows(IllegalArgumentException.class, () -> vec246.crossProduct(vec123),
				"Vector constractor of three Double cannot be zero vector");
		assertEquals( (Math.sqrt(42) * 2), vec123.length() * vec22m2.length(), DELTA,
				"wrong cross product of ortogonal vectors");
		assertEquals(0.0, vec22m2.dotProduct(vec123), "wrong cross product of ortogonal vectors");
		assertEquals(0.0, vec22m2.dotProduct(vec224), "wrong cross product of ortogonal vectors");
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	void testLengthSquared() {
		Vector vec224 = new Vector(2, 2, 4);
		// ============================ Equivalence Partitions Tests ======================
		assertEquals( 24 ,vec224.lengthSquared(), "wrong length squared calculates");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============================ Equivalence Partitions Tests ======================
		Vector vecm2m13 = new Vector(-2, -1, 3);
		// ============================ Equivalence Partitions Tests ======================
		assertEquals(Math.sqrt(14), vecm2m13.length(), "wrong length calculates");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============================ Equivalence Partitions Tests ======================
		Vector vec221 = new Vector(2, 2, 1);
		Vector norVec = new Vector((double) 2 / 3, (double) 2 / 3, (double) 1 / 3);
		assertEquals(norVec, vec221.normalize(), "wrong normalized vector");
	}

}
