package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * Geometries class represents a collection of geometric objects that can be
 * intersected with a ray. It allows aggregation of multiple geometries and
 * provides a unified interface for intersection calculations.
 */
public class Geometries extends Intersectable {

	private final List<Intersectable> allgeometries = new LinkedList<>();

	/**
	 * Default constructor for the Geometries class. Creates an empty collection of
	 * geometries.
	 */
	public Geometries() {
	}

	/**
	 * Constructor for the Geometries class. Allows initialization with a collection
	 * of intersectable geometries.
	 *
	 * @param geometries A variable number of geometries to add to the collection.
	 */
	public Geometries(Intersectable... geometries) {
		add(geometries);
	}

	/**
	 * Adds one or more intersectable geometries to the collection.
	 *
	 * @param geometries A variable number of geometries to be added.
	 */
	public void add(Intersectable... geometries) {
		for (Intersectable geo : geometries) {
			allgeometries.add(geo);
		}
	}

	@Override
    /**
     * Finds all intersection points between the given ray and the geometries in this collection.
     * <p>
     * This method supports two modes of operation, controlled by the static flag
     * {@link renderer.SimpleRayTracer#BVH_ENABLED}:
     * <ul>
     * <li><b>BVH Disabled:</b> Performs a simple, exhaustive search, checking for intersections
     * against every geometry in the collection.</li>
     * <li><b>BVH Enabled:</b> Performs an optimized search using a two-phase Bounding
     * Volume Hierarchy approach. It first checks if the ray intersects the bounding box
     * of the entire collection, and only then checks against the bounding boxes of
     * individual child geometries.</li>
     * </ul>
     *
     * @param ray The ray for which to find intersection points.
     * @return A list of intersection points, or {@code null} if no intersections are found.
     */
    public List<Intersection> calculateIntersectionsHelper(Ray ray) {
        // If the BVH switch is disabled, perform the old, un-optimized search.
        if (!renderer.SimpleRayTracer.BVH_ENABLED) {
            List<Intersection> intersections = null;
            for (Intersectable geometry : allgeometries) {
                var geometryIntersections = geometry.calculateIntersections(ray);
                if (geometryIntersections != null) {
                    if (intersections == null) {
                        intersections = new java.util.LinkedList<>();
                    }
                    intersections.addAll(geometryIntersections);
                }
            }
            return intersections;
        }

        // --- BVH Accelerated Logic ---

        // Step 1: Broad-phase check against the bounding box of the entire collection.
        AABB mainBox = this.getBoundingBox();
        // Optimization: If the ray misses the main box, we can prune this entire branch and exit early.
        if (mainBox != null && !ray.isIntersecting(mainBox)) {
            return null;
        }

        // If the ray hits the main box, proceed to check the children geometries.
        List<Intersection> intersections = null;
        for (Intersectable geometry : allgeometries) {
            // Step 2: Narrow-phase check against each child's individual bounding box.
            AABB geoBox = geometry.getBoundingBox();
            
            // An object might be infinite (like a Plane) and have no box (geoBox == null).
            // In that case, we must perform the detailed check.
            if (geoBox == null || ray.isIntersecting(geoBox)) {
                // Optimization: Perform the expensive, detailed intersection test ONLY if the ray
                // hits the child's box (or if the child has no box).
                var geometryIntersections = geometry.calculateIntersections(ray);
                if (geometryIntersections != null) {
                    if (intersections == null) {
                        intersections = new java.util.LinkedList<>();
                    }
                    intersections.addAll(geometryIntersections);
                }
            }
        }
        return intersections;
    }
	
	@Override
    public AABB getBoundingBox() {
        if (allgeometries.isEmpty()) {
            return null;
        }

        AABB boundingBox = null;
        for (Intersectable geo : allgeometries) {
            AABB geoBox = geo.getBoundingBox();
            // We use our new factory method to merge the boxes.
            boundingBox = primitives.BoundingBoxFactory.union(boundingBox, geoBox);
        }

        return boundingBox;
    }
	
}
