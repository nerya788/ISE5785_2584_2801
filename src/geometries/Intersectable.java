package geometries;

import java.util.List;

import primitives.*;
import lighting.*;

/**
 * Interface representing geometric objects that can be intersected by a
 * {@link Ray}. Classes implementing this interface provide methods for finding
 * intersections.
 */
public abstract class Intersectable {
	protected AABB boundingBox = null;

	/**
	 * Returns the bounding box of the geometry, calculating it only once (caching).
	 * 
	 * @return The cached AABB of the geometry.
	 */
	public AABB getBoundingBox() {
		if (boundingBox == null) {
			boundingBox = createBoundingBox();
		}
		return boundingBox;
	}

	/**
	 * Creates the actual bounding box for the geometry. This method is called only
	 * once by the caching mechanism in getBoundingBox().
	 * 
	 * @return A new AABB for the geometry.
	 */
	protected abstract AABB createBoundingBox();

	public static class Intersection {
		public final Geometry geometry;
		public final Point point;
		public final Material material;;

		public Vector directionIntersect;
		public Vector directionLight;
		public Vector normal;
		public double dotNormalAndIntersect;
		public double dotNormalAndLight;
		public LightSource lightType;

		/**
		 * Constructs a new Intersection object with a given geometry and intersection
		 * point.
		 *
		 * @param geometry the {@link Geometry} object that was intersected
		 * @param point    the {@link Point} at which the intersection occurred
		 */
		public Intersection(Geometry geometry, Point point) {
			this.geometry = geometry;
			this.point = point;

			if (geometry != null)
				this.material = geometry.getMaterial();
			else
				this.material = null;
		}

		@Override
		public String toString() {
			return "Intersection{" + "geometry=" + geometry + ", point=" + point + '}';
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			Intersection otherIntersection = (Intersection) obj;
			return geometry == otherIntersection.geometry && point.equals(otherIntersection.point);
		}
	}

	/**
	 * Abstract method to be implemented by subclasses. Calculates the intersection
	 * points (with geometry) between this shape and a given ray.
	 *
	 * @param ray the ray to intersect with
	 * @return list of {@link Intersection} points, or {@code null} if none
	 */
	protected abstract List<Intersection> calculateIntersectionsHelper(Ray ray);

	public final List<Intersection> calculateIntersections(Ray ray) {
		return calculateIntersectionsHelper(ray);
	}

	/**
	 * Finds the intersection points between the current object and the given
	 * {@link Ray}.
	 *
	 * @param ray The ray used to find intersections with the object.
	 * @return A list of {@link Point} objects representing the intersection points,
	 *         or {@code null} if there are no intersections.
	 */
	public final List<Point> findIntersections(Ray ray) {
		List<Intersection> geoList = calculateIntersections(ray);
		if (geoList == null)
			return null;
		return geoList.stream().map(i -> i.point).toList();

	}

}
