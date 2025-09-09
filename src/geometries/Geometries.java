package geometries;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable.Intersection;
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

	/**
	 * Finds all intersection points between the given ray and the geometries in the
	 * collection.
	 *
	 * @param ray The ray for which to find intersection points.
	 * @return A list of intersection points, or {@code null} if no intersections
	 *         are found.
	 */
	@Override
	/*
	 * protected List<Intersection> calculateIntersectionsHelper(Ray ray) {
	 * List<Intersection> intersections = null;
	 * 
	 * for (Geometry geometry : scene.geometries) var geometryIntersections =
	 * geometry.calculateIntersections(ray); if there are elements in
	 * geometryIntersections: if intersections is empty – create it with
	 * geometryIntersectionselse – add geometryIntersections to intersections return
	 * intersections; }
	 */

	public List<Intersection> calculateIntersectionsHelper(Ray ray) {
		List<Intersection> intersections = null;

		for (Intersectable geometry : allgeometries) {
			var geometryIntersections = geometry.calculateIntersections(ray);
			if (geometryIntersections != null) {
				if (intersections == null)
					intersections = new LinkedList<>();
				intersections.addAll(geometryIntersections);
			}
		}

		return intersections;
	}
}
