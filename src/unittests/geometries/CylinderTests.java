/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import primitives.*;
import geometries.*;

/**
 * Testing Cylinders
 * @author Nerya
 */
class CylinderTests {
	final double Two = 2.0;
	/**
	 * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		double height = 2.0;
		Point point100 = new Point(1, 0, 0);
		Vector vec200 = new Vector(2, 0, 0);
		Ray ray = new Ray (point100, vec200);
		Cylinder cylinder = new Cylinder(ray, Two, height);
		
		Point point1050 = new Point(1, 0.5, 0);
		Point point210 = new Point(2, 1, 0);
		Point point3050 = new Point(3, 0.5, 0);
		
		Point point110 = new Point(1, 1, 0);
		Point point310 = new Point(3, 1, 0);
		Point point300 = new Point(3, 0, 0);
		
		// ============================ Equivalence Partitions Tests ============================	
		assertEquals(new Vector (0, 1, 0), cylinder.getNormal(point210),   "wrong normal of side cylinder");
		assertEquals(new Vector (-1, 0, 0), cylinder.getNormal(point1050), "wrong normal of base1 cylinder");
		assertEquals(new Vector (1, 0, 0), cylinder.getNormal(point3050),  "wrong normal of base2 cylinder");		
		
		// ============================ Boundary Values Tests ===================================
		assertEquals(new Vector (-1, 0, 0), cylinder.getNormal(point100), "wrong normal of center base1 cylinder");
		assertEquals(new Vector (1, 0, 0),  cylinder.getNormal(point300),  "wrong normal of center base2 cylinder");	
		assertEquals(new Vector (-1, 0, 0), cylinder.getNormal(point110), "wrong normal of edge center base1 cylinder");
		assertEquals(new Vector (1, 0, 0),  cylinder.getNormal(point310),  "wrong normal of edge center base2 cylinder");	

	}

}
