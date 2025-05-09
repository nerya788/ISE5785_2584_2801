/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;

/**
 * Testing Cylinders
 * 
 * @author Nerya
 */
class CylinderTests {
	final double Radius = 2.0;

	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		double height = 2.0;
		Point point100 = new Point(1, 0, 0);
		Vector vec200 = new Vector(2, 0, 0);
		Ray ray = new Ray(point100, vec200);
		Cylinder cylinder = new Cylinder(ray, Radius, height);

		Point point1050 = new Point(1, 0.5, 0);
		Point point210 = new Point(2, 1, 0);
		Point point3050 = new Point(3, 0.5, 0);

		Point point110 = new Point(1, 1, 0);
		Point point310 = new Point(3, 1, 0);
		Point point300 = new Point(3, 0, 0);

		// ============================ Equivalence Partitions Tests ============================
		assertEquals(new Vector(0, 1, 0), cylinder.getNormal(point210), "wrong normal of side cylinder");
		assertEquals(new Vector(-1, 0, 0), cylinder.getNormal(point1050), "wrong normal of base1 cylinder");
		assertEquals(new Vector(1, 0, 0), cylinder.getNormal(point3050), "wrong normal of base2 cylinder");

		// ============================ Boundary Values Tests  ===================================
		assertEquals(new Vector(-1, 0, 0), cylinder.getNormal(point100), "wrong normal of center base1 cylinder");
		assertEquals(new Vector(1, 0, 0), cylinder.getNormal(point300), "wrong normal of center base2 cylinder");
		assertEquals(new Vector(-1, 0, 0), cylinder.getNormal(point110), "wrong normal of edge center base1 cylinder");
		assertEquals(new Vector(1, 0, 0), cylinder.getNormal(point310), "wrong normal of edge center base2 cylinder");

	}

	@Test
	void testCalculateIntersectionsHelper() {

		final Point p100 = new Point(1, 0, 0);
		final Point p103 = new Point(1, 0, 3);
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
		final Vector vm110 = new Vector(-1, 1, 0);
		final Vector v1m10 = new Vector(1, -1, 0);
		final Vector vm302 = new Vector(-3, 0, 2);
		final Vector vm102 = new Vector(-1, 0, 2);
		final Vector vm101 = new Vector(-1, 0, 1);
		final Vector v0m10 = new Vector(0, -1, 0);
		final Vector v310 = new Vector(3, 1, 0);
		final Vector vm1015 = new Vector(-1, 0, 1.5);

		Tube tube = new Cylinder(new Ray(p100, v001), 1, 3);

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

		// TC04: Ray starts after the sphere when the next side in the tube (0 points)
		assertNull(tube.findIntersections(new Ray(p210, v310)), "Ray that after the circle");

		// **** Group 2: parallel and orthogonal line
		// TC05: Ray parallel and orthogonal to the tube axis, inside the tube, length
		// the tube (0 points)
		List<Point> result3 = tube.findIntersections(new Ray(new Point(1, 0.5, 0), v001));
		final var exp3 = List.of(new Point(1, 0.5, 3));
		assertNotNull(result3, "Ray starts inside the tube");
		assertEquals(1, result2.size(), "TC02: Expected one intersection point");
		assertEquals(exp3, result3, "Ray crosses plant the tube");

		// TC06: Ray parallel and orthogonal to the tube axis, inside the tube, length
		// the tube, down base (1 points)
		List<Point> result33 = tube.findIntersections(new Ray(new Point(1, 0.5, -1), v001));
		final var exp33 = List.of(new Point(1, 0.5, 0), new Point(1, 0.5, 3));
		assertNotNull(result33, "Ray starts inside the tube");
		assertEquals(2, result33.size(), "TC02: Expected one intersection point");
		assertEquals(exp33, result33, "Ray crosses plant the tube");

		// TC07: Ray parallel and orthogonal to the tube axis, inside the tube, length
		// the tube, up base (0 points)
		List<Point> result34 = tube.findIntersections(new Ray(new Point(1, 0.5, 1), v001));
		final var exp34 = List.of(new Point(1, 0.5, 3));
		assertNotNull(result34, "Ray starts inside the tube");
		assertEquals(1, result34.size(), "TC02: Expected one intersection point");
		assertEquals(exp34, result34, "Ray crosses plant the tube");

		// TC08: Ray parallel and orthogonal to the tube axis, inside the tube, length
		// the tube, up base (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 0.5, -1), v00m1)),
				"Ray is parallel and inside tube, up base");

		// TC09: Ray parallel and orthogonal to axis, outside tube ,length the tube (0
		// points)
		assertNull(tube.findIntersections(new Ray(new Point(3, 0, 0), v001)), "Ray parallel and outside tube");

		// TC10: Ray parallel and orthogonal to the tube axis, inside the tube, width
		// the tube, down base (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 0.5, -1), v100)),
				"Ray is parallel and outside tube, down base, width the tube");

		// TC10A: Ray parallel and orthogonal to the tube axis, inside the tube, length
		// the tube, up base (2 points)
		List<Point> result31 = tube.findIntersections(new Ray(new Point(-1.5, 0.5, 1), v100));
		final var exp31 = List.of(new Point(0.133974596215561, 0.5, 1), new Point(1.866025403784438, 0.5, 1));
		assertNotNull(result31, "Ray starts inside the tube");
		assertEquals(2, result31.size(), "TC02: Expected one intersection point");
		assertEquals(exp31, result31, "Ray crosses plant the tube");

		// TC10B: Ray parallel and orthogonal to the tube axis, inside the tube, length
		// the tube, up base on the center(2 points)
		List<Point> result32 = tube.findIntersections(new Ray(new Point(-1.5, 0, 1), v100));
		final var exp32 = List.of(new Point(0, 0, 1), new Point(2, 0, 1));
		assertNotNull(result32, "Ray starts inside the tube");
		assertEquals(2, result32.size(), "TC02: Expected one intersection point");
		assertEquals(exp32, result32, "Ray crosses plant the tube");

		// **** Group 3: the line passes inside the tube, start with length of tube
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
				.findIntersections(new Ray(new Point(1.866025403784439, 0.5, 0.755983064143708), vm302));
		final var exp6 = List.of(new Point(0.133974596215561, 0.5, 1.910683602522959));
		assertNotNull(result6, "Can't be empty list");
		assertEquals(1, result6.size(), "Wrong number of points");
		assertEquals(exp6, result6, "Ray start at the end of the tube");

		// **** Group 4: the line passes inside the tube, start down base of tube
		// TC16: Ray start before the tube (2 points)
		final var result7 = tube.findIntersections(new Ray(new Point(2, 0.5, -2.5), vm102));
		final var exp7 = List.of(new Point(0.75, 0.5, 0), new Point(0.13397459621613872, 0.5, 1.2320508075677226));
		assertNotNull(result7, "Can't be empty list");
		assertEquals(2, result7.size(), "Wrong number of points");
		assertEquals(exp7, result7, "Ray start before the tub");

		// TC17: Ray start in the tube(1 points)
		final var result8 = tube.findIntersections(new Ray(new Point(1, 0.5, -0.5), vm102));
		final var exp8 = List.of(new Point(0.75, 0.5, 0), new Point(0.13397459621613872, 0.5, 1.2320508075677226));
		assertNotNull(result8, "Can't be empty list");
		assertEquals(2, result8.size(), "Wrong number of points");
		assertEquals(exp8, result8, "Ray start in the tube");

		// TC18: Ray start after the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 0.5, 1.5), vm102)), "Ray start after the tube");

		// TC19: Ray start began the tube(1 points)
		final var result9 = tube.findIntersections(new Ray(new Point(0.75, 0.5, 0), vm102));
		final var exp9 = List.of(new Point(0.13397459621613872, 0.5, 1.2320508075677226));
		assertNotNull(result9, "Can't be empty list");
		assertEquals(1, result9.size(), "Wrong number of points");
		assertEquals(exp9, result9, "Ray start began the tube");

		// TC20: Ray start at the end of the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0.13397459621613872, 0.5, 1.2320508075677226), vm102)),
				"Ray start at the end of the tube");

		// **** Group 5: line passes inside the tube, start between the base along the
		// tube
		// TC21: Ray start before the tube (2 points)
		final var result10 = tube.findIntersections(new Ray(new Point(4, 0, -3), vm1015));
		final var exp10 = List.of(new Point(2, 0, 0), new Point(0, 0, 3));
		assertNotNull(result10, "Can't be empty list");
		assertEquals(2, result10.size(), "Wrong number of points");
		assertEquals(exp10, result10, "Ray start before the tube");

		// TC22: Ray start in the tube(1 points)
		final var result11 = tube.findIntersections(new Ray(new Point(1.5, 0, 0.75), vm1015));
		final var exp11 = List.of(new Point(0, 0, 3));
		assertNotNull(result11, "Can't be empty list");
		assertEquals(1, result11.size(), "Wrong number of points");
		assertEquals(exp11, result11, "Ray start in the tube");

		// TC23: Ray start after the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(-2, 0, 6), vm1015)), "Ray start after the tube");

		// TC24: Ray start at the end of the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 0, 3), vm1015)), "Ray start at the end of the tube");

		// TC25: Ray start began the tube(1 points)
		final var result12 = tube.findIntersections(new Ray(new Point(2, 0, 0), vm1015));
		final var exp12 = List.of(new Point(0, 0, 3));
		assertNotNull(result12, "Can't be empty list");
		assertEquals(1, result12.size(), "Wrong number of points");
		assertEquals(exp12, result12, "Ray start began the tube");

		// **** Group 6: line passes inside the tube, start with length of tube on the
		// center
		// TC26: Ray start before the tube (2 points)
		final var result13 = tube.findIntersections(new Ray(new Point(3, 0, 0), vm302));
		final var exp13 = List.of(new Point(2, 0, 0.666666666666667), new Point(0, 0, 2));
		assertNotNull(result13, "Can't be empty list");
		assertEquals(2, result13.size(), "Wrong number of points");
		assertEquals(exp13, result13, " Ray start before the tube");

		// TC27: Ray start in the tube(1 points)
		final var result14 = tube.findIntersections(new Ray(new Point(1.5, 0, 1), vm302));
		final var exp14 = List.of(new Point(0, 0, 2));
		assertNotNull(result14, "Can't be empty list");
		assertEquals(1, result14.size(), "Wrong number of points");
		assertEquals(exp14, result14, "Ray start in the tube");

		// TC28: Ray start after the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(-1.5, 0, 3), vm302)), "Ray start after the tube");

		// TC29: Ray start at the end of the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 0, 2), vm302)), "Ray start at the end of the tube");

		// TC30: Ray start began the tube(1 points)
		final var result15 = tube.findIntersections(new Ray(new Point(2, 0, 0.666666666666667), vm302));
		final var exp15 = List.of(new Point(0, 0, 2));
		assertNotNull(result15, "Can't be empty list");
		assertEquals(1, result15.size(), "Wrong number of points");
		assertEquals(exp15, result15, "Ray start began the tube");

		// TC31: Ray start at the center of tube(1 points)
		final var result16 = tube.findIntersections(new Ray(new Point(1, 0, 1.3333333333333333), vm302));
		final var exp16 = List.of(new Point(0, 0, 2));
		assertNotNull(result16, "Can't be empty list");
		assertEquals(1, result16.size(), "Wrong number of points");
		assertEquals(exp16, result16, "Ray start at the center of tube");

		// **** Group 7: line passes inside the tube, start down base of tube on center
		// TC32: Ray start before the tube (2 points)
		final var result17 = tube.findIntersections(new Ray(new Point(2.5, 0, -2), vm1015));
		final var exp17 = List.of(new Point(1.1666666666666667, 0, 0), new Point(0, 0, 1.75));
		assertNotNull(result17, "Can't be empty list");
		assertEquals(2, result17.size(), "Wrong number of points");
		assertEquals(exp17, result17, "Ray start before the tube");

		// TC33: Ray start in the tube(1 points)
		final var result18 = tube.findIntersections(new Ray(new Point(0.5, 0, 1), vm1015));
		final var exp18 = List.of(new Point(0, 0, 1.75));
		assertNotNull(result18, "Can't be empty list");
		assertEquals(1, result18.size(), "Wrong number of points");
		assertEquals(exp18, result18, "Ray start in the tube");

		// TC34: Ray start after the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(-0.5, 0, 4), vm102)), "Ray start after the tube");

		// TC35: Ray start at the end of the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 0, 3), vm102)), "Ray start at the end of the tube");

		// TC36: Ray start began the tube(1 points)
		final var result19 = tube.findIntersections(new Ray(new Point(1.1666666666666667, 0, 0), vm1015));
		final var exp19 = List.of(new Point(0, 0, 1.75));
		assertNotNull(result19, "Can't be empty list");
		assertEquals(1, result19.size(), "Wrong number of points");
		assertEquals(exp19, result19, "Ray start began the tube");

		// TC37: Ray start at the center of tube(0 points)
		final var result20 = tube.findIntersections(new Ray(new Point(1, 0, 0.25), vm1015));
		final var exp20 = List.of(new Point(0, 0, 1.75));
		assertNotNull(result20, "Can't be empty list");
		assertEquals(1, result20.size(), "Wrong number of points");
		assertEquals(exp20, result20, "Ray start at the center of tube");

		// **** Group 8: line passes inside the tube, start between the base along the
		// tube
		// TC38: Ray start before the tube (2 points)
		final var result21 = tube.findIntersections(new Ray(new Point(2.5, 0, -0.5), vm101));
		final var exp21 = List.of(new Point(2, 0, 0), new Point(0, 0, 2));
		assertNotNull(result21, "Can't be empty list");
		assertEquals(2, result21.size(), "Wrong number of points");
		assertEquals(exp21, result21, "Ray start before the tube");

		// TC39: Ray start in the tube(1 points)
		final var result22 = tube.findIntersections(new Ray(new Point(1.5, 0, 0.5), vm101));
		final var exp22 = List.of(new Point(0, 0, 2));
		assertNotNull(result22, "Can't be empty list");
		assertEquals(1, result22.size(), "Wrong number of points");
		assertEquals(exp22, result22, "Ray start in the tube");

		// TC40: Ray start after the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(-1, 0, 3), vm101)), "Ray start after the tube");

		// TC41: Ray start at the end of the tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 0, 2), vm101)), "Ray start at the end of the tube");

		// TC42: Ray start began the tube(1 points)
		final var result23 = tube.findIntersections(new Ray(new Point(2, 0, 0), vm101));
		final var exp23 = List.of(new Point(0, 0, 2));
		assertNotNull(result23, "Can't be empty list");
		assertEquals(1, result23.size(), "Wrong number of points");
		assertEquals(exp23, result23, "Ray start began the tube");

		// TC43: Ray start at the center of tube(1 points)
		final var result24 = tube.findIntersections(new Ray(new Point(1, 0, 1), vm101));
		final var exp24 = List.of(new Point(0, 0, 2));
		assertNotNull(result24, "Can't be empty list");
		assertEquals(1, result24.size(), "Wrong number of points");
		assertEquals(exp24, result24, "Ray start at the center of tube");

		// =============== Boundary Values Tests ==================

		// **** Group 9: Ray's line crosses the base center and orthogonal
		// TC44: Ray start before the tube (2 points)
		final var result25 = tube.findIntersections(new Ray(p120, v0m10));
		final var exp25 = List.of(p110, p1m10);
		assertNotNull(result25, "Can't be empty list");
		assertEquals(2, result25.size(), "Wrong number of points");
		assertEquals(exp25, result25, " Ray start before the tube");

		// TC45:Ray start began the tube (1 points)
		final var result26 = tube.findIntersections(new Ray(p110, v0m10));
		final var exp26 = List.of(p1m10);
		assertNotNull(result26, "Can't be empty list");
		assertEquals(1, result26.size(), "Wrong number of points");
		assertEquals(exp26, result26, "Ray start began the tube");

		// TC46:Ray start at the center of tube(1 point)
		final var result27 = tube.findIntersections(new Ray(new Point(1, 0, 0), v100));
		final var exp27 = List.of(p200);
		assertNotNull(result27, "Can't be empty list");
		assertEquals(1, result27.size(), "Wrong number of points");
		assertEquals(exp27, result27, "Ray start at the center of tube");

		// TC47: Ray start in the tube(1 point)
		final var result28 = tube.findIntersections(new Ray(new Point(1.5, 0, 0), v100));
		final var exp28 = List.of(p200);
		assertNotNull(result28, "Can't be empty list");
		assertEquals(1, result28.size(), "Wrong number of points");
		assertEquals(exp28, result28, "Ray start in the tube");

		// TC48:Ray start at the end of the tube(0 point)
		assertNull(tube.findIntersections(new Ray(new Point(2, 0, 0), v100)), "Ray start at the end of the tube");

		// TC49: Ray start after the tube(0 point)
		assertNull(tube.findIntersections(new Ray(new Point(2.5, 0, 0), v100)), "Ray start after the tube");

		// **** Group 10: Ray's line is flat tangent to the sphere (all tests 0 points)
		// TC51: Ray tangent to tube - starts before tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 1, 0), v100)), "Tangent ray before contact");

		// TC52: Ray tangent to tube - starts at tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 1, 0), v100)), "Tangent ray at contact");

		// TC53: Ray tangent to tube - starts after tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(2, 1, 0), v100)), "Tangent ray after contact");

		// **** Group 11: Ray's line is deep tangent to the sphere (all tests 0 points)
		// TC54: Ray tangent to tube - starts before tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(0, 1, 0), vm102)), "Tangent ray before contact");

		// TC55: Ray tangent to tube - starts at tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 1, 0), vm102)), "Tangent ray at contact");

		// TC56: Ray tangent to tube - starts after tangent point (0 points)
		assertNull(tube.findIntersections(new Ray(new Point(2, 1, 0), vm102)), "Tangent ray after contact");

		// **** Group 12: Ray's start on the base
		// TC57: Ray starts at sphere and goes inside (1 point)
		final var result29 = tube.findIntersections(new Ray(p200, vm110));
		final var exp29 = List.of(p110);
		assertNotNull(result29, "Can't be empty list");
		assertEquals(1, result29.size(), "Wrong number of points");
		assertEquals(exp29, result29, "Ray starts at sphere and goes inside");

		// TC58: Ray starts at sphere and goes outside (0 points)
		assertNull(tube.findIntersections(new Ray(p200, v1m10)), "Ray starts at sphere and goes outside");

		// **** Group 13: Special cases
		// TC59: Ray's line is outside sphere, ray is orthogonal to start-to-center line
		assertNull(tube.findIntersections(new Ray(new Point(-1, 0, 0), v010)), "Orthogonal ray outside tube");

		// TC60: Ray is orthogonal and inside center of tube, inside the tube center(0
		// points)
		List<Point> result30 = tube.findIntersections(new Ray(p100, v001));
		final var exp30 = List.of(p103);
		assertNotNull(result30, "Ray starts inside the tube");
		assertEquals(1, result30.size(), "TC02: Expected one intersection point");
		assertEquals(exp30, result30, "Ray is orthogonal and inside center of tube");

		// TC61: Ray is orthogonal and inside center of tube, inside up the tube
		// length(0 points)
		List<Point> result36 = tube.findIntersections(new Ray(new Point(1, 0, 1), v001));
		final var exp36 = List.of(p103);
		assertNotNull(result36, "Ray starts inside the tube");
		assertEquals(1, result36.size(), "TC02: Expected one intersection point");
		assertEquals(exp36, result36, "Ray is orthogonal and inside center of tube");

		// TC62: Ray is orthogonal and inside center of tube, outside down tube length(0
		// points)
		List<Point> result37 = tube.findIntersections(new Ray(new Point(1, 0, -1), v001));
		final var exp37 = List.of(p100, p103);
		assertNotNull(result37, "Ray starts inside the tube");
		assertEquals(2, result37.size(), "TC02: Expected one intersection point");
		assertEquals(exp37, result37, "Ray is orthogonal and inside center of tube");

		// TC63: Ray is orthogonal and outside vis versa center of tube(0 points)
		assertNull(tube.findIntersections(new Ray(new Point(1, 0, -1), v00m1)),
				"Ray is orthogonal and outside vers versa center of tube");

		// TC64: Ray is contained in tube length
		assertNull(tube.findIntersections(new Ray(new Point(2, 0, 0), v001)), "Ray is contained in tube length");

		// TC65: Ray is contained in tube width
		List<Point> result38 = tube.findIntersections(new Ray(new Point(-1, 0, 0), v100));
		final var exp38 = List.of(new Point(0, 0, 0), p200);
		assertNotNull(result38, "Ray starts inside the tube");
		assertEquals(2, result38.size(), "TC02: Expected one intersection point");
		assertEquals(exp38, result38, "Ray is orthogonal and inside center of tube");

	}

}
