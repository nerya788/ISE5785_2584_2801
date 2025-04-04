/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
import geometries.*;
import org.junit.jupiter.api.Test;

/**
 * 
 */
class PlaneTests {
	double DELTA=0.00000000001;
	/**
	 * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
	     Point point213 = new Point(2,1,3);
		 Point point312 = new Point(3,1,2);
		 Point point112 = new Point(1,1,2);
		 Plane plane = new Plane (point213,point312,point112);
		 
		// ============================ Equivalence Partitions Tests ============================
		 
		assertEquals(new Vector(0,-1,0), plane.getNormal(point213),  "wrong normal of plane");
	}

	/**
	 * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
	 */
	@Test
	void testPlane() {
		 Point point123 = new Point(1,2,3);
		 Point point224 = new Point(2,2,4);
		 Point point321 = new Point(3,2,1);
		 Plane plane = new Plane (point123,point321,point224);
		 
		// ============================ Equivalence Partitions Tests ============================
		 
		 assertEquals(0.0, plane.getNormal(point321).dotProduct(point321.subtract(point123)),  "wrong normal of plane");
		 assertEquals(0.0, plane.getNormal(point321).dotProduct(point224.subtract(point123)),  "wrong normal of plane");
		 
		// ============================ Boundary Values Tests ====================================

		 assertThrows(IllegalArgumentException.class, () -> new Plane (point123,point123,point224) ,"point1 and point2 same, cannot creat zero normal vector");
		 assertThrows(IllegalArgumentException.class, () -> new Plane (point123,point224,point123) ,"point1 and point3 same, cannot creat zero normal vector");
		 assertThrows(IllegalArgumentException.class, () -> new Plane (point224,point123,point123) ,"point2 and point3 same, cannot creat zero normal vector");
		 assertThrows(IllegalArgumentException.class, () -> new Plane (point123,point123,point123) ,"all the point same, normal vector cannot be zero vector");
		 
		 Point point246 = new Point(2,4,6);
		 Point point369 = new Point(3,6,9);
		 assertThrows(IllegalArgumentException.class, () -> new Plane (point123,point246,point369) ,"all the point on same direct, normal vector cannot be zero vector");
	}

}
