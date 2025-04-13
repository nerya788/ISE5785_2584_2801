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
	 * Test method for {@link primitives.Vector#Vector(double, double, double)}.
	 */
	@Test
	void testVector3Double() {
		// ============================ Equivalence Partitions Tests
		// ============================
		Vector vec123 = new Vector(1, 2, 3);
		assertEquals(new Vector(1, 2, 3), vec123, "wrong three Double constractor allocation");

		// ============================ Boundary Values Tests
		// ============================
		assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0),
				"Vector constractor of three Double cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#Vector(primitives.Double3)}.
	 */
	@Test
	void testVectorDouble3() {
		// ============================ Equivalence Partitions Tests
		// ============================
		Double3 dou123 = new Double3(1, 2, 3);
		Double3 dou000 = new Double3(0, 0, 0);
		Vector vec123 = new Vector(1, 2, 3);
		assertEquals(new Vector(dou123), vec123, "wrong Double3 constractor allocation");

		// ============================ Boundary Values Tests
		// ============================
		assertThrows(IllegalArgumentException.class, () -> new Vector(dou000),
				"Vector onstractor of Double3 cannot be zero vector");
	}

	/**
	 * Test method for {@link primitives.Vector#add(primitives.Vector)}.
	 */
	@Test
	void testAddVector() {
		// ============================ Equivalence Partitions Tests
		// ============================
		Vector vec123 = new Vector(1, 2, 3);
		Vector vecm23m4 = new Vector(-2, 3, -4);
		assertEquals(new Vector(-1, 5, -1), vec123.add(vecm23m4), "wrong resulte for add two vectors");

		// ============================ Boundary Values Tests
		// ============================
		Vector vecMinus123 = new Vector(-1, -2, -3);
		assertThrows(IllegalArgumentException.class, () -> vec123.add(vecMinus123),
				"Vector onstractor of Double3 cannot be zero vector");

	}

	void testSubVector() {
		// ============================ Equivalence Partitions Tests
		// ============================
		Vector vec123 = new Vector(1, 2, 3);
		Vector vecm23m4 = new Vector(-2, 3, -4);
		assertEquals(new Vector(3, -1, 7), vec123.subtract(vecm23m4), "wrong resulte for subtract two vectors");

		// ============================ Boundary Values Tests
		// ============================
		assertThrows(IllegalArgumentException.class, () -> vec123.subtract(vec123),
				"Vector onstractor of Double3 cannot be zero vector");

	}

	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	void testScale() {
		// ============================ Equivalence Partitions Tests
		// ============================
		Vector vec135 = new Vector(1, 3, 5);
		Double num = 2.0;
		assertEquals(new Vector(2, 6, 10), vec135.scale(num), "wrong resulte for scaling vector");

		// ============================ Boundary Values Tests
		// ============================
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
		assertEquals(vec123.dotProduct(vec22m1), 3, "crossProduct() wrong result");

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

		// ============================ Equivalence Partitions Tests
		// ============================

		assertEquals(vec123.crossProduct(vec224), vec22m2, "cross product give wrong result");

		// ============================ Boundary Values Tests
		// ============================

		assertThrows(IllegalArgumentException.class, () -> vec246.crossProduct(vec123),
				"Vector constractor of three Double cannot be zero vector");
		assertEquals(vec123.length() * vec22m2.length(), (Math.sqrt(42) * 2), DELTA,
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
		// ============================ Equivalence Partitions Tests
		// ============================
		assertEquals(vec224.lengthSquared(), 24, "wrong length squared calculates");
	}

	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	@Test
	void testLength() {
		// ============================ Equivalence Partitions Tests
		// ============================
		Vector vecm2m13 = new Vector(-2, -1, 3);
		// ============================ Equivalence Partitions Tests
		// ============================
		assertEquals(vecm2m13.length(), Math.sqrt(14), "wrong length calculates");
	}

	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	@Test
	void testNormalize() {
		// ============================ Equivalence Partitions Tests
		// ============================
		Vector vec221 = new Vector(2, 2, 1);
		Vector norVec = new Vector((double) 2 / 3, (double) 2 / 3, (double) 1 / 3);
		assertEquals(vec221.normalize(), norVec, "wrong normalized vector");
	}

}
