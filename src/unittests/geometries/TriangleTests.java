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
class TriangleTests {
	double DELTA=0.00000000001;
	/**
	 * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
	 */
	@Test
	void testGetNormal() {
		Point point213 = new Point(2,1,3);
		Point point312 = new Point(3,1,2);
		Point point112 = new Point(1,1,2);
		Point[] vertices = new Point[] {point213,point312,point112};
		Triangle triangle = new Triangle(vertices);
		
		// ============================ Equivalence Partitions Tests ============================
		 
		assertEquals(new Vector(0,-1,0), triangle.getNormal(point213), DELTA, "wrong normal of plane");
	}

}
