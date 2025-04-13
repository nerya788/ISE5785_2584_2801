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
 * Testing Tubes
 * 
 * @author Nerya
 */
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Point point000 = new Point(0, 0, 0);
		Vector vec100 = new Vector(1, 0, 0);
		Ray ray = new Ray(point000, vec100);
		Tube tube = new Tube(ray, 2.0);

		Point point110 = new Point(1, 1, 0);
		Point point0050 = new Point(0, 0.5, 0);

		// ============================ Boundary Values Tests
		// ===================================
		assertEquals(new Vector(-1, 0, 0), tube.getNormal(point0050), "wrong normal of base tube");

		// ============================ Equivalence Partitions Tests
		// ============================
		assertEquals(new Vector(0, 1, 0), tube.getNormal(point110), "wrong normal of side tube");
	}

	@Test
	void testFindIntersections() {
		
		final Point p100 = new Point(1, 0, 0);
		final Point p300 = new Point(3, 0, 0);
	    final Point p2500 = new Point(2.5, 0, 0);
	    final Point p010 = new Point(0, 1, 0);
	    final Point p001 = new Point(0, 0, 1);
	    final Point p150 = new Point(1.5, 0, 0);
	    final Point p120 = new Point(1, 2, 0);
	    final Point p220 = new Point(2, 2, 0);
	    final Point p130 = new Point(1, 3, 0);
	    final Point p125 = new Point(1, 2.5, 0);
		final Point p110 = new Point(1, 1, 0);
		final Point p1m10 = new Point(1, -1, 0);

	    final Vector v001 = new Vector(0, 0, 1);
	    final Vector v010 = new Vector(0, 1, 0);
	    final Vector v100 = new Vector(1, 0, 0);
	    final Vector v110 = new Vector(1, 1, 0);
	    final Vector v1m10 = new Vector(1, -1, 0);
	    final Vector v1m20 = new Vector(1, -2, 0);
	    final Vector v0001 = new Vector(0, 0, 1);
		final Vector v0m10 = new Vector(0, -1, 0);
		Tube tube = new Tube(new Ray(p100, v001), 1);
		

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray crosses plant the tube (2 points)
        Ray ray1 = new Ray(p2500, new Vector(-2, 1, 0));
        List<Point> result1 = tube.findIntersections(ray1);
        final var exp1 = List.of(new Point (1.9633249581, 0.268337521, 0),new Point (0.6366750419,0.931662479,0));
        assertNotNull(result1, "Ray crosses the tube");
        assertEquals(2, result1.size(), "Expected two intersection points");
        assertEquals(exp1, result1, "Ray crosses plant the tube");

        // TC02: Ray starts inside the tube move plant (1 point)
        Ray ray2 = new Ray(new Point(1.5, 0.5, 0), v110);
        List<Point> result2 = tube.findIntersections(ray2);
        final var exp2 = List.of(new Point (1.7071067812,0.7071067812,0));
        assertNotNull(result2, "Ray starts inside the tube");
        assertEquals(1, result2.size(), "TC02: Expected one intersection point");

        // TC03: Ray is outside and misses the tube (0 points)
        Ray ray3 = new Ray(p220, v010);
        assertNull(tube.findIntersections(ray3), "Ray should miss the tube");
        
        // TC031: Ray is outside and misses the tube (0 points)
        Ray ray31 = new Ray(p300, v100);
        assertNull(tube.findIntersections(ray3), "Ray should miss the tube");

        // TC04: Ray parallel to the tube axis, inside the tube (0 points)
        Ray ray4 = new Ray(new Point(1, 0.5, 0), v001);
        assertNull(tube.findIntersections(ray4), "Ray is parallel and inside tube");

        // TC05: Ray parallel to axis, outside tube (0 points)
        Ray ray5 = new Ray(new Point(3, 0, 0), v001);
        assertNull(tube.findIntersections(ray5), "Ray parallel and outside tube");

        // TC06: Ray orthogonal to axis and intersects (2 points)
        final var result3 = tube.findIntersections(new Ray(p120, v0m10));
		final var exp3 = List.of(p110, p1m10);
		assertNotNull(result3, "Can't be empty list");
		assertEquals(2, result3.size(), "Wrong number of points");
		assertEquals(exp3, result3, "Ray through center before sphere");

        // TC07: Ray orthogonal and tangent (0 point)
        Ray ray7 = new Ray(new Point(2, 1, 0), new Vector(-1, 0, 0));
        List<Point> result7 = tube.findIntersections(ray7);
        assertNull(result7, "Tangent ray");

        // TC08: Ray starts on the tube surface and goes inside (1 point)
        Ray ray8 = new Ray(new Point(2, 0, 0), new Vector(-1, 1, 0));
        List<Point> result8 = tube.findIntersections(ray8);
        final var exp4 = List.of(p110);
        assertNotNull(result8, "Ray starts on surface and enters");
        assertEquals(1, result8.size(), "Expected one intersection point");

        // TC09: Ray starts on the surface and goes out (0 points)
        Ray ray9 = new Ray(new Point(2, 0, 0), new Vector(1, -1, 0));
        assertNull(tube.findIntersections(ray9), "Ray starts on surface and exits");

        // =============== Boundary Values Tests ==================

        // TC10: Ray goes through center line (should intersect twice)
        Ray ray10 = new Ray(p120, v1m10);
        List<Point> result10 = tube.findIntersections(ray10);
        assertNotNull(result10, "TC10: Ray through center");
        assertEquals(2, result10.size(), "TC10: Expected two intersection points");

        // TC11: Ray tangent to tube - starts before tangent point (0 points)
        Ray ray11 = new Ray(new Point(0, 1, 0), v100);
        assertNull(tube.findIntersections(ray11), "TC11: Tangent ray before contact");

        // TC12: Ray tangent to tube - starts at tangent point (0 points)
        Ray ray12 = new Ray(new Point(1, 1, 0), v100);
        assertNull(tube.findIntersections(ray12), "TC12: Tangent ray at contact");

        // TC13: Ray tangent to tube - starts after tangent point (0 points)
        Ray ray13 = new Ray(new Point(2, 1, 0), v100);
        assertNull(tube.findIntersections(ray13), "TC13: Tangent ray after contact");

        // TC14: Ray perpendicular to axis and originates inside tube (1 point)
        Ray ray14 = new Ray(new Point(1.5, 0.5, 0), new Vector(-1, 0, 0));
        List<Point> result14 = tube.findIntersections(ray14);
        assertNotNull(result14, "TC14: Inside tube, perpendicular");
        assertEquals(1, result14.size(), "TC14: Expected one intersection point");

	}

}
