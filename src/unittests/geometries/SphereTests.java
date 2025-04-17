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
 * Testing Spheres
 * 
 * @author Nerya
 */
class SphereTests {
	double DELTA = 0.00000000001;

	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Point point121 = new Point(1, 2, 1);
		Point point123 = new Point(1, 2, 3);
		double radius = 2;
		Sphere sphere = new Sphere(point123, radius);

		// ============================ Equivalence Partitions Tests
		// ============================

		assertEquals(new Vector(0, 0, -1), sphere.getNormal(point121), "wrong normal of plane");
	}

	/** A point used in some tests */
	private final Point p001 = new Point(0, 0, 1);
	/** A point used in some tests */
	private final Point p100 = new Point(1, 0, 0);
	/** A vector used in some tests */
	private final Vector v001 = new Vector(0, 0, 1);

	/**
	 * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
	 */
	@Test
	public void testFindIntersections() {
		Sphere sphere = new Sphere(p100, 1);
		final Point gp1 = new Point(0.0651530771650466, 0.355051025721682, 0);
		final Point gp2 = new Point(1.53484692283495, 0.844948974278318, 0);
		final var exp1 = List.of(gp1, gp2);
		final Vector v310 = new Vector(3, 1, 0);
		final Vector v110 = new Vector(1, 1, 0);
		final Point pm100 = new Point(-1, 0, 0);
		// ============ Equivalence Partitions Tests ==============
		// TC01: Ray's line is outside the sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(pm100, v110)), "Ray's line out of sphere");

		// TC02: Ray starts before and crosses the sphere (2 points)
		final var result1 = sphere.findIntersections(new Ray(pm100, v310));
		assertNotNull(result1, "Can't be empty list");
		assertEquals(2, result1.size(), "Wrong number of points");
		assertEquals(exp1, result1, "Ray crosses sphere");

		// TC03: Ray starts inside the sphere (1 point)
		final Point p05050 = new Point(0.5, 0.5, 0);
		final var result2 = sphere.findIntersections(new Ray(p05050, v310));
		final var exp2 = List.of(gp2);
		assertNotNull(result2, "Can't be empty list");
		assertEquals(1, result2.size(), "Wrong number of points");
		assertEquals(exp2, result2, "Ray that emanates from the in of the circle");

		// TC04: Ray starts after the sphere (0 points)
		final Point p210 = new Point(2, 1, 0);
		assertNull(sphere.findIntersections(new Ray(p210, v310)), "Ray that after the circle");

		// =============== Boundary Values Tests ==================
		final Vector v010 = new Vector(0, 1, 0);
		final Vector vm100 = new Vector(-1, 0, 0);
		final Vector v100 = new Vector(1, 0, 0);
		final Vector v0m10 = new Vector(0, -1, 0);
		final Point p200 = new Point(2, 0, 0);
		final Point p300 = new Point(3, 0, 0);
		final Point p110 = new Point(1, 1, 0);
		final Point p120 = new Point(1, 2, 0);
		final Point p1m10 = new Point(1, -1, 0);

		// **** Group 1: Ray's line crosses the sphere (but not the center)
		// TC11: Ray starts at sphere and goes inside (1 point)
		final var result11 = sphere.findIntersections(new Ray(p110, v0m10));
		final var exp11 = List.of(p1m10);
		assertNotNull(result11, "Can't be empty list");
		assertEquals(1, result11.size(), "Wrong number of points");
		assertEquals(exp11, result11, "Ray starts at sphere and goes inside");

		// TC12: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(p1m10, v0m10)), "Ray starts at sphere and goes outside");

		// **** Group 2: Ray's line goes through the center

		// TC21: Ray starts at sphere and goes outside (0 points)
		assertNull(sphere.findIntersections(new Ray(p200, v100)), "Ray from sphere away from center");

		// TC22: Ray starts at the center (1 point)
		final var result22 = sphere.findIntersections(new Ray(p100, v100));
		final var exp22 = List.of(p200);
		assertNotNull(result22, "Can't be empty list");
		assertEquals(1, result22.size(), "Wrong number of points");
		assertEquals(exp22, result22, "Ray from center");

		// TC23: Ray starts after sphere (0 points)
		assertNull(sphere.findIntersections(new Ray(p300, v100)), "Ray starts after sphere");

		// TC24: Ray starts at sphere and goes inside (1 point)
		final var result24 = sphere.findIntersections(new Ray(p110, v0m10));
		final var exp24 = List.of(p1m10);
		assertNotNull(result24, "Can't be empty list");
		assertEquals(1, result24.size(), "Wrong number of points");
		assertEquals(exp24, result24, "Ray from sphere into center");

		// TC25: Ray starts inside (1 point)
		final var result25 = sphere.findIntersections(new Ray(new Point(1.5, 0, 0), v100));
		assertNotNull(result25, "Can't be empty list");
		assertEquals(1, result25.size(), "Wrong number of points");
		assertEquals(exp22, result25, "Ray from inside through center");

		// TC26: Ray starts before the sphere (2 points)
		final var result26 = sphere.findIntersections(new Ray(p120, v0m10));
		final var exp26 = List.of(p110, p1m10);
		assertNotNull(result26, "Can't be empty list");
		assertEquals(2, result26.size(), "Wrong number of points");
		assertEquals(exp26, result26, "Ray through center before sphere");

		// **** Group 3: Ray's line is tangent to the sphere (all tests 0 points)
		Point tangent = new Point(0, 1, 0);
		Point pm110 = new Point(-1, 1, 0);
		// TC31: Ray starts before the tangent point
		assertNull(sphere.findIntersections(new Ray(pm110, v100)), "Ray tangent before point");

		// TC32: Ray starts at the tangent point
		assertNull(sphere.findIntersections(new Ray(tangent, v100)), "Ray tangent at point");

		// TC33: Ray starts after the tangent point
		assertNull(sphere.findIntersections(new Ray(new Point(2, 1, 0), v100)), "Ray tangent after point");

		// **** Group 4: Special cases
		// TC41: Ray's line is outside sphere, ray is orthogonal to start-to-center line (0 points)
		assertNull(sphere.findIntersections(new Ray(new Point(-1, 0, 0), v010)), "Orthogonal ray outside sphere");

		// TC42: Ray starts inside, orthogonal to center line (1 point)
		Point pm1050 = new Point(1, 0.5, 0);
		final var result42 = sphere.findIntersections(new Ray(pm1050, v010));
		final var exp42 = List.of(p110);
		assertNotNull(result42, "Can't be empty list");
		assertEquals(1, result42.size(), "Wrong number of points");
		assertEquals(exp42, result42, "Orthogonal ray inside sphere");
	}
}
