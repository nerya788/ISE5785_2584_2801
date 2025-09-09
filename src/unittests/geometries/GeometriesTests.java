/**
 *
 */
package unittests.geometries;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import primitives.*;
import geometries.*;

/**
 * Unit tests for the {@link Geometries} class, focusing on its intersection
 * handling.
 */
class GeometriesTests {

	/**
	 * Tests the {@link Geometries#findIntersections(Ray)} method with various
	 * scenarios:
	 * <ul>
	 * <li>Empty collection of geometries</li>
	 * <li>No intersections</li>
	 * <li>Intersections with one or more geometries</li>
	 * <li>All geometries being intersected</li>
	 * </ul>
	 */
	@Test
	void testFindIntersections() {
		final Point p100 = new Point(1, 0, 0);
		final Vector v001 = new Vector(0, 0, 1);
		final Tube tube = new Tube(new Ray(p100, v001), 1);

		final Point pm2m20 = new Point(-2, -2, 0);
		final Point p232 = new Point(2, 3, 2);
		final Sphere sphere1 = new Sphere(pm2m20, 1);
		final Sphere sphere2 = new Sphere(p232, 1);

		// Test: Empty collection (BVA)
		Geometries geometries = new Geometries();
		assertNull(geometries.findIntersections(new Ray(new Point(0, 0, 0), new Vector(1, 1, 1))),
				"Expected null for empty collection");

		// Test: No shape intersects (BVA)
		geometries.add(tube, sphere1, sphere2);
		assertNull(geometries.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, -1, -1))),
				"Expected null for a ray that misses all shapes");

		// Test: Only one shape intersects (BVA)
		Ray ray1 = new Ray(new Point(0, -3, 0), new Vector(-2, 1, 0)); // Intersects sphere1 only
		assertEquals(2, geometries.findIntersections(ray1).size(),
				"Expected one intersection for a ray hitting only one shape");

		// Test: Some shapes intersect (EP)
		Ray ray2 = new Ray(new Point(1.5, 4, 2), new Vector(0, -1, 0)); // Intersects sphere2 and tube
		assertEquals(4, geometries.findIntersections(ray2).size(),
				"Expected multiple intersections for a ray hitting some shapes");

		// Test: All shapes intersect (BVA)
		Ray ray3 = new Ray(new Point(4, 4.875, 2.75), new Vector(-1, -1.25, -0.5)); // Intersects all shapes
		assertEquals(6, geometries.findIntersections(ray3).size(),
				"Expected intersections with all shapes for a suitable ray");
	}

}
