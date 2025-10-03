package renderer;

import java.util.List;
import geometries.Intersectable.Intersection;
import lighting.LightSource;
import primitives.*;
import renderer.Camera.rayCreationSpace;
import scene.Scene;
import static java.lang.System.out;

/**
 * A simple implementation of the {@link RayTracerBase} class.
 * <p>
 * This basic ray tracer computes the color of a pixel based only on ambient
 * lighting and the first intersection point between a ray and the scene
 * geometries.
 * <p>
 * It does not account for reflections, refractions, shading, or lighting
 * sources.
 */
public class SimpleRayTracer extends RayTracerBase {

	/** A global switch to enable or disable the BVH optimization. */
	public static boolean BVH_ENABLED = false;

	// Delta of distance for shading rays that we take from the body to the light
	// source to prevent self-shading
	private static final double DELTA = 0.1;
	private static final int MAX_CALC_COLOR_LEVEL = 10;
	private static final double MIN_CALC_COLOR_K = 0.001;
	private static final Double3 INITIAL_K = Double3.ONE;

	/**
	 * Constructs a {@code SimpleRayTracer} using the provided scene.
	 * 
	 * @param newScene the scene to be copied into the tracer engine
	 */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	protected Intersection findClosestIntersection(Ray ray) {
		if (scene.geometries.findIntersections(ray) != null)
			return ray.findClosestIntersection(scene.geometries.calculateIntersectionsHelper(ray));
		else
			return null;
	}

	/**
	 * Determines whether a given intersection point is illuminated (unshaded) with
	 * respect to a specific light source.
	 * 
	 * @param intersection the {@link Intersection} containing the hit point, its
	 *                     normal, and the light source being evaluated
	 * @return {@code true} if the light source is visible from the intersection
	 *         point (no blocking geometry exists between them), {@code false} if
	 *         the point is in shadow
	 */
	private boolean unshaded(Intersection intersection) {
		Vector directionShaded = intersection.directionLight.scale(-1);
		Ray lightRay = new Ray(intersection.point, directionShaded, intersection.normal, DELTA);

		var intersections = scene.geometries.calculateIntersections(lightRay);
		if (intersections == null)
			return true;

		double distance = intersection.lightType.getDistance(intersection.point);
		for (Intersection intersect : intersections) {
			if (intersection.point.distance(intersect.point) < distance
					&& intersect.material.kT.lowerThan(MIN_CALC_COLOR_K))
				return false;
		}
		return true;
	}

	private Double3 transparency(Intersection intersection) {
		Double3 ktr = Double3.ONE;
		Vector directionShaded = intersection.directionLight.scale(-1);
		Ray lightRay = new Ray(intersection.point, directionShaded, intersection.normal, DELTA);

		var intersections = scene.geometries.calculateIntersections(lightRay);
		if (intersections == null)
			return Double3.ONE;

		double distance = intersection.lightType.getDistance(intersection.point);
		for (Intersection intersect : intersections) {
			if (intersection.point.distance(intersect.point) < distance)
				ktr = ktr.product(intersect.material.kT);
			if (ktr.lowerThan(MIN_CALC_COLOR_K))
				return Double3.ZERO;
		}
		return ktr;
	}

	/**
	 * Preprocesses an intersection by computing and storing its normal,
	 * intersection direction, and their dot product.
	 *
	 * @param target the intersection object to preprocess
	 * @param ray    the direction of the incoming ray
	 * @return {@code true} if the dot product is non-zero (valid intersection),
	 *         {@code false} otherwise
	 */
	public boolean preprocessIntersection(Intersection target, Vector ray) {
		target.directionIntersect = ray.normalize();
		target.normal = target.geometry.getNormal(target.point);
		target.dotNormalAndIntersect = Util.alignZero(ray.dotProduct(target.normal));

		return !Util.isZero(target.dotNormalAndIntersect);
	}

	/**
	 * Configures the intersection with respect to a specific light source. Computes
	 * the light direction, dot products, and validates contribution.
	 *
	 * @param target    the intersection object to update
	 * @param typeLight the light source affecting the point
	 * @return {@code true} if the light contributes to shading, {@code false}
	 *         otherwise
	 */
	public boolean setLightSource(Intersection target, LightSource typeLight) {
		target.lightType = typeLight;
		target.directionLight = typeLight.getL(target.point);
		target.dotNormalAndLight = Util.alignZero(target.directionLight.dotProduct(target.normal));

		return !(Util.isZero(target.dotNormalAndLight) && Util.isZero(target.dotNormalAndIntersect));
	}

	/**
	 * Calculates the local lighting effects (diffuse + specular) at the given
	 * intersection.
	 *
	 * @param intersection the intersection point to evaluate
	 * @return the computed color contribution at this point
	 */
	private Color calcLocalEffects(Intersection intersection) {
		Color color = intersection.geometry.getEmission();
		for (LightSource lightSource : scene.lights) {
			if (!setLightSource(intersection, lightSource))
				continue;
			if ((intersection.dotNormalAndLight * intersection.dotNormalAndIntersect > 0)
			/* && unshaded(intersection) */ ) {
				Double3 ktr = transparency(intersection);
				if (!ktr.lowerThan(MIN_CALC_COLOR_K)) {
					Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
					color = color.add(iL.scale(calcDiffusive(intersection).add(calcSpecular(intersection))));
				}
			}
		}
		return color;
	}

	/**
	 * Calculates the diffuse reflection component using the Lambertian model
	 * combined with a specular highlight factor.
	 *
	 * @param intersection the intersection to evaluate
	 * @return the diffuse reflection component as a {@link Double3}
	 */
	private Double3 calcDiffusive(Intersection intersection) {
		return intersection.material.kD.scale(Math.abs(intersection.dotNormalAndLight));
	}

	/**
	 * Calculates the specular reflection coefficient using the Phong reflection
	 * model. use the formula: VectorReflect = 2 * directionLight.dotProduct(normal)
	 * * normal - directionLight. specularColor = Ks * (-1 *
	 * directionIntersect.dotProduct(VectorReflect)) ^ nsh.
	 * 
	 * @param intersection the intersection to evaluate
	 * @return the specular reflection component as a {@link Double3}
	 */
	private Double3 calcSpecular(Intersection intersection) {

		return intersection.material.kS.scale(Math.pow(
				Math.max(0, intersection.directionIntersect.scale(-1).dotProduct(intersection.directionLight
						.subtract(intersection.normal.scale(intersection.dotNormalAndLight).scale(2.0)).normalize())),
				intersection.material.nsh));
	}

	/**
	 * Traces a given {@link rayCreationSpace} and returns the resulting
	 * {@link Color}.
	 * <p>
	 * If no intersection is found, the background color is returned. Otherwise,
	 * returns the ambient light at the closest intersection point.
	 *
	 * @param rayCreationSpace the ray to trace
	 * @return the resulting color at the ray's closest intersection point
	 */
	@Override
	public Color traceRay(rayCreationSpace details) {
		Ray ray = new Ray(details.p0(), details.pIJ().subtract(details.p0()));
		Intersection intersection = findClosestIntersection(ray);
		return intersection == null ? scene.background : calcColor(intersection, ray);
	}

	/**
	 * Computes the final color at the given intersection point by combining local
	 * lighting effects and ambient light.
	 *
	 * @param intersection the intersection to evaluate
	 * @param ray          the incoming ray that generated this intersection
	 * @return the resulting color at the intersection point
	 */
	protected Color calcColor(Intersection intersection, Ray ray) {
		if (!preprocessIntersection(intersection, ray.getDirection()))
			return Color.BLACK;
		return calcColor(intersection, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
				.add(scene.ambientLight.getIntensity().scale(intersection.material.kA));
	}

	private Color calcColor(Intersection intersection, Ray ray, int level, Double3 k) {
		Color color = calcLocalEffects(intersection);
		return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
	}

	private Color calcGlobalEffects(Intersection intersection, Ray ray, int level, Double3 k) {
		return calcGlobalEffects(constructReflectedRay(intersection), level, k, intersection.material.kR)
				.add(calcGlobalEffects(constructRefractedRay(intersection), level, k, intersection.material.kT));
	}

	private Color calcGlobalEffects(Ray ray, int level, Double3 k, Double3 kx) {
		Double3 kkx = k.product(kx);
		Intersection nextIntersection = findClosestIntersection(ray);

		if (kkx.lowerThan(MIN_CALC_COLOR_K))
			return Color.BLACK;

		if (nextIntersection == null)
			return scene.background.scale(kx);

		return preprocessIntersection(nextIntersection, ray.getDirection())
				? calcColor(nextIntersection, ray, level - 1, kkx).scale(kx)
				: Color.BLACK;
	}

	private Ray constructRefractedRay(Intersection intersection) {
		return new Ray(intersection.point, intersection.directionIntersect, intersection.normal, DELTA);
	}

	private Ray constructReflectedRay(Intersection intersection) {
		return new Ray(intersection.point,
				intersection.directionIntersect
						.subtract(intersection.normal.scale(intersection.dotNormalAndIntersect).scale(2.0)).normalize(),
				intersection.normal, DELTA);
	}

}
