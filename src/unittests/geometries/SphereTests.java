/**
 * 
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import primitives.*;
import geometries.*;
import org.junit.jupiter.api.Test;

/**
 * Testing Spheres
 * @author Nerya
 */
class SphereTests {
	double DELTA = 0.00000000001;
	/**
	 * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Point point121 = new Point (1,2,1);
		Point point123 = new Point (1,2,3);
		double radius = 2;
		Sphere sphere = new Sphere(point123,radius);
		
		// ============================ Equivalence Partitions Tests ============================
		 
		assertEquals(new Vector (0,0,-1), sphere.getNormal(point121),  "wrong normal of plane");
	}

}
