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
class TubeTests {

	/**
	 * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Point point000 = new Point(0,0,0);
		Vector vec100 = new Vector(1,0,0);
		Ray ray = new Ray (point000,vec100);
		Tube tube = new Tube(ray,2.0);
		
		Point point110 = new Point(1,1,0);
		Point point0050 = new Point(0,0.5,0);
		
		// ============================ Boundary Values Tests ===================================
		assertEquals(new Vector (-1,0,0), tube.getNormal(point0050),  "wrong normal of base tube");
		
		// ============================ Equivalence Partitions Tests ============================
		assertEquals(new Vector (0,1,0), tube.getNormal(point110),  "wrong normal of side tube");	
	}

}
