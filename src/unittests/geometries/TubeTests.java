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
		final Point p200 = new Point(2, 0, 0);
		final Point p2500 = new Point(2.5, 0, 0);
		final Point p120 = new Point(1, 2, 0);
		final Point p110 = new Point(1, 1, 0);
		final Point p1m10 = new Point(1, -1, 0);
		final Point pm100 = new Point(-1, 0, 0);
		final Point p210 = new Point(2, 1, 0);

		final Vector v001 = new Vector(0, 0, 1);
		final Vector v00m1 = new Vector(0, 0, -1);
		final Vector v010 = new Vector(0, 1, 0);
		final Vector v100 = new Vector(1, 0, 0);
		final Vector v110 = new Vector(1, 1, 0);
		final Vector v011 = new Vector(0, 1, 1);
		final Vector v111 = new Vector(1, 1, 1);
		final Vector vm110 = new Vector(-1, 1, 0);
		final Vector v1m10 = new Vector(1, -1, 0);
		final Vector vm302 = new Vector(-3, 0, 2);
		final Vector vm102 = new Vector(-1, 0, 2);
		final Vector v0m10 = new Vector(0, -1, 0);
		final Vector v310 = new Vector(3, 1, 0);// vm212
		final Vector vm212 = new Vector(-2, 1, 2);

		Tube tube = new Tube(new Ray(p100, v001), 1);

		// ============ Equivalence Partitions Tests ==============
		// **** Group 1: The base circle of the tube and flat vector
		// TC01: Ray crosses plant the tube (2 points)
		List<Point> result1 = tube.findIntersections(new Ray(p2500, new Vector(-2, 1, 0)));
		final var exp1 = List.of(new Point(1.96332495807108, 0.26833752096446, 0),
				new Point(0.63667504192892, 0.93166247903554, 0));
		assertNotNull(result1, "Ray crosses the tube");
		assertEquals(2, result1.size(), "Expected two intersection points");
		assertEquals(exp1, result1, "Ray crosses flat the tube");

		// TC02: Ray starts inside the tube move flat (1 point)
		List<Point> result2 = tube.findIntersections(new Ray(new Point(1.5, 0.5, 0), v110));
		final var exp2 = List.of(new Point(1.707106781186548, 0.707106781186548, 0));
		assertNotNull(result2, "Ray starts inside the tube");
		assertEquals(1, result2.size(), "TC02: Expected one intersection point");
		assertEquals(exp2, result2, "Ray crosses flat the tube");

		// TC03: Ray's line is off-side the tube (0 points)
		assertNull(tube.findIntersections(new Ray(pm100, v110)), "Ray's line out of tube");

		// TC04: Ray starts after the circle when the next side in the tube (0 points)
		assertNull(tube.findIntersections(new Ray(p210, v310)), "Ray that after the circle");

		// **** Group 2: parallel and orthogonal line
		// TC05: Ray parallel and orthogonal to the tube axis, inside the tube, length
		// the tube (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 0.5, 0), v001)),
				"Ray is parallel and inside tube, down head point");

		// TC06: Ray parallel and orthogonal to the tube axis, inside the tube, length
		// the tube, up base (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 0.5, 0), v00m1)),
				"Ray is parallel and inside tube, up head point");

		// TC07: Ray parallel and orthogonal to axis, outside tube ,length the tube (0
		// points)
		assertNull(tube.findIntersections(new Ray(new Point(3, 0, 0), v011)), "Ray parallel and outside tube");

		// TC08: Ray parallel and orthogonal to axis, outside tube ,length the tube (0
		// points)
		assertNull(tube.findIntersections(new Ray(new Point(3, 0, 0), v001)), "Ray parallel and outside tube");

		// TC09: Ray orthogonal to the tube axis, inside the tube, length the tube (2
		// points)
		List<Point> result31 = tube.findIntersections(new Ray(new Point(-1.5, 0.5, 1), v100));
		final var exp31 = List.of(new Point(0.133974596215561, 0.5, 1), new Point(1.866025403784438, 0.5, 1));
		assertNotNull(result31, "Ray starts inside the tube");
		assertEquals(2, result31.size(), "TC02: Expected one intersection point");
		assertEquals(exp31, result31, "Ray crosses plant the tube");

		// TC10: Ray orthogonal to the tube axis, inside the tube, length the tube, on
		// the center(2 points)
		List<Point> result32 = tube.findIntersections(new Ray(new Point(-1.5, 0, 1), v100));
		final var exp32 = List.of(new Point(0, 0, 1), new Point(2, 0, 1));
		assertNotNull(result32, "Ray starts inside the tube");
		assertEquals(2, result32.size(), "TC02: Expected one intersection point");
		assertEquals(exp32, result32, "Ray crosses plant the tube");

		// **** Group 3: the line passes inside the tube
		// TC11: Ray start before the tube (2 points)
		final var result4 = tube.findIntersections(new Ray(new Point(3, 0.5, 0), vm302));
		final var exp4 = List.of(new Point(1.866025403784439, 0.5, 0.755983064143708),
				new Point(0.133974596215561, 0.5, 1.910683602522959));
		assertNotNull(result4, "Can't be empty list");
		assertEquals(2, result4.size(), "Wrong number of points");
		assertEquals(exp4, result4, "Ray start before the tube");

		// TC12: Ray start in the tube(1 points)
		final var result5 = tube.findIntersections(new Ray(new Point(1.5, 0.5, 1), vm302));
		final var exp5 = List.of(new Point(0.133974596215561, 0.5, 1.910683602522959));
		assertNotNull(result5, "Can't be empty list");
		assertEquals(1, result5.size(), "Wrong number of points");
		assertEquals(exp5, result5, "Ray start in the tube");

		// TC13: Ray start after the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 0.5, 2), vm302)), "Ray start after the tube");

		// TC14: Ray start began the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0.1055122253, 0.5, 1.9296585165), vm302)),
				"Ray start began the tube");

		// TC15: Ray start at the end of the tube(1 points)
		final var result6 = tube
				.findIntersections(new Ray(new Point(1.866025403784439, 0.5, 0.7559830641437081), vm302));
		final var exp6 = List.of(new Point(0.133974596215561, 0.5, 1.910683602522959));
		assertNotNull(result6, "Can't be empty list");
		assertEquals(1, result6.size(), "Wrong number of points");
		assertEquals(exp6, result6, "Ray start at the end of the tube");

		// **** Group 4: line passes inside the tube, arrive the center
		// TC16: Ray start before the tube (2 points)
		final var result13 = tube.findIntersections(new Ray(new Point(0, -1, 0), v111));
		final var exp13 = List.of(new Point(0.292893218813453, -0.707106781186548, 0.292893218813453),
				new Point(1.707106781186548, 0.707106781186548, 1.707106781186548));
		assertNotNull(result13, "Can't be empty list");
		assertEquals(2, result13.size(), "Wrong number of points");
		assertEquals(exp13, result13, " Ray start before the tube");

		// TC17: Ray start in the tube(1 points)
		final var result14 = tube.findIntersections(new Ray(new Point(0.5, -0.5, 0.5), v111));
		final var exp14 = List.of(new Point(1.707106781186548, 0.707106781186548, 1.707106781186548));
		assertNotNull(result14, "Can't be empty list");
		assertEquals(1, result14.size(), "Wrong number of points");
		assertEquals(exp14, result14, "Ray start in the tube");

		// TC18: Ray start after the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(2, 1, 2), v111)), "Ray start after the tube");

		// TC19: Ray start at the end of the tube(0 points)
		assertNull(
				tube.findIntersections(
						new Ray(new Point(1.707106781186548, 0.707106781186548, 1.707106781186548), v111)),
				"Ray start at the end of the tube");

		// TC20: Ray start began the tube(1 points)
		final var result15 = tube
				.findIntersections(new Ray(new Point(0.292893218813453, -0.707106781186548, 0.292893218813453), v111));
		final var exp15 = List.of(new Point(1.707106781186548, 0.707106781186548, 1.707106781186548));
		assertNotNull(result15, "Can't be empty list");
		assertEquals(1, result15.size(), "Wrong number of points");
		assertEquals(exp15, result15, "Ray start began the tube");

		// TC21: Ray start at the center of tube(1 points)
		final var result16 = tube.findIntersections(new Ray(new Point(1, 0, 1), v111));
		final var exp16 = List.of(new Point(1.707106781186548, 0.707106781186548, 1.707106781186548));
		assertNotNull(result16, "Can't be empty list");
		assertEquals(1, result16.size(), "Wrong number of points");
		assertEquals(exp16, result16, "Ray start at the center of tube");

		// **** Group 5: line passes inside the tube, start with length of tube on the
		// center axis
		// TC22: Ray start before the tube (2 points)
		final var result17 = tube.findIntersections(new Ray(new Point(3, 0, 0), vm302));
		final var exp17 = List.of(new Point(2, 0, 0.666666666666667), new Point(0, 0, 2));
		assertNotNull(result17, "Can't be empty list");
		assertEquals(2, result17.size(), "Wrong number of points");
		assertEquals(exp17, result17, " Ray start before the tube");

		// TC23: Ray start in the tube(1 points)
		final var result18 = tube.findIntersections(new Ray(new Point(1.5, 0, 1), vm302));
		final var exp18 = List.of(new Point(0, 0, 2));
		assertNotNull(result18, "Can't be empty list");
		assertEquals(1, result18.size(), "Wrong number of points");
		assertEquals(exp18, result18, "Ray start in the tube");

		// TC24: Ray start after the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(-1.5, 0, 3), vm302)), "Ray start after the tube");

		// TC25: Ray start at the end of the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 0, 2), vm302)), "Ray start at the end of the tube");

		// TC26: Ray start began the tube(1 points)
		final var result19 = tube.findIntersections(new Ray(new Point(2, 0, 0.666666666666667), vm302));
		final var exp19 = List.of(new Point(0, 0, 2));
		assertNotNull(result19, "Can't be empty list");
		assertEquals(1, result19.size(), "Wrong number of points");
		assertEquals(exp19, result19, "Ray start began the tube");

		// TC27: Ray start at the center of tube(1 points)
		final var result20 = tube.findIntersections(new Ray(new Point(1, 0, 1.3333333333333333), vm302));
		final var exp20 = List.of(new Point(0, 0, 2));
		assertNotNull(result20, "Can't be empty list");
		assertEquals(1, result20.size(), "Wrong number of points");
		assertEquals(exp20, result20, "Ray start at the center of tube");

		// =============== Boundary Values Tests ==================

		// **** Group 9: Ray's line crosses the base center and orthogonal
		// TC28: Ray start before the tube (2 points)
		final var result21 = tube.findIntersections(new Ray(p120, v0m10));
		final var exp21 = List.of(p110, p1m10);
		assertNotNull(result21, "Can't be empty list");
		assertEquals(2, result21.size(), "Wrong number of points");
		assertEquals(exp21, result21, " Ray start before the tube");

		// TC29:Ray start began the tube (1 points)
		final var result23 = tube.findIntersections(new Ray(p110, v0m10));
		final var exp23 = List.of(p1m10);
		assertNotNull(result23, "Can't be empty list");
		assertEquals(1, result23.size(), "Wrong number of points");
		assertEquals(exp23, result23, "Ray start began the tube");

		// TC30:Ray start at the center of tube(1 point)
		final var result24 = tube.findIntersections(new Ray(new Point(1, 0, 0), v100));
		final var exp24 = List.of(p200);
		assertNotNull(result24, "Can't be empty list");
		assertEquals(1, result24.size(), "Wrong number of points");
		assertEquals(exp24, result24, "Ray start at the center of tube");

		// TC31: Ray start in the tube(1 point)
		final var result25 = tube.findIntersections(new Ray(new Point(1.5, 0, 0), v100));
		final var exp25 = List.of(p200);
		assertNotNull(result25, "Can't be empty list");
		assertEquals(1, result25.size(), "Wrong number of points");
		assertEquals(exp25, result25, "Ray start in the tube");

		// TC32:Ray start at the end of the tube(0 point)
		assertNull(tube.findIntersections(new Ray(new Point(2, 0, 0), v100)), "Ray start at the end of the tube");

		// TC33: Ray start after the tube(0 point)
		assertNull(tube.findIntersections(new Ray(new Point(2.5, 0, 0), v100)), "Ray start after the tube");

		// **** Group 10: Ray's line is flat tangent to the tube (all tests 0 points)
		// TC34: Ray tangent to tube - starts before tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 1, 0), v100)), "Tangent ray before contact");

		// TC35: Ray tangent to tube - starts at tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 1, 0), v100)), "Tangent ray at contact");

		// TC36: Ray tangent to tube - starts after tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(2, 1, 0), v100)), "Tangent ray after contact");

		// **** Group 11: Ray's line is deep tangent to the circle (all tests 0 points)
		// TC37: Ray tangent to tube - starts before tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 1, 0), vm102)), "Tangent ray before contact");

		// TC38: Ray tangent to tube - starts at tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 1, 0), vm102)), "Tangent ray at contact");

		// TC39: Ray tangent to tube - starts after tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(2, 1, 0), vm102)), "Tangent ray after contact");

		// **** Group 12: Ray's start on the base
		// TC40: Ray starts at circle and goes inside (1 point)
		final var result26 = tube.findIntersections(new Ray(p200, vm110));
		final var exp26 = List.of(p110);
		assertNotNull(result26, "Can't be empty list");
		assertEquals(1, result26.size(), "Wrong number of points");
		assertEquals(exp26, result26, "Ray starts at circle and goes inside");

		// TC41: Ray starts at circle and goes outside (0 points)
		assertNull(tube.findIntersections(new Ray(p200, v1m10)), "Ray starts at circle and goes outside");

		// TC42: Ray starts at circle and goes outside (0 points)
		assertNull(tube.findIntersections(new Ray(p200, v010)), "Ray starts at circle and goes outside");

		// **** Group 13: Special cases
		// TC43: Ray deep and outside the tube (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(5, 0, 0), vm212)), "Ray deep and outside the tube");

		// TC44: Ray's line is outside circle, ray is orthogonal to start-to-center line
		assertNull(tube.findIntersections(new Ray(new Point(-1, 0, 0), v010)), "Orthogonal ray outside tube");

		// TC45: Ray is orthogonal and outside vis versa center of tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1.5, 0, 0), v001)),
				"Ray is orthogonal and outside vers versa center of tube");

		// TC46: Ray is orthogonal and outside vis versa center of tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1.5, 0, 0), v00m1)),
				"Ray is orthogonal and outside vers versa center of tube");

		// TC47: Ray is orthogonal and inside center of tube, inside the tube center(0
		// points)
		assertNull(tube.findIntersections(new Ray(p100, v001)), "Ray is orthogonal and inside center of tube");

		// TC48: Ray is orthogonal and inside center of tube, inside the tube center(0
		// points)
		assertNull(tube.findIntersections(new Ray(p100, v00m1)), "Ray is orthogonal and inside center of tube");

		// TC49: Ray is contained in tube length
		assertNull(tube.findIntersections(new Ray(new Point(2, 0, 0), v001)), "Ray is contained in tube length");

	}
}
