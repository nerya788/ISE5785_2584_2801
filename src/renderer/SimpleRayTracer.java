package renderer;

import java.util.List;
import geometries.Intersectable.Intersection;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * A simple implementation of the {@link RayTracerBase} class.
 * <p>
 * This basic ray tracer computes the color of a pixel based only on
 * ambient lighting and the first intersection point between a ray and
 * the scene geometries.
 * <p>
 * It does not account for reflections, refractions, shading, or lighting sources.
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
     * Constructs a {@code SimpleRayTracer} using the provided scene.
     * 
     * @param newScene the scene to be copied into the tracer engine
     */
	public SimpleRayTracer(Scene newScene) {
		super(newScene);
	}

	/**
     * Traces a given {@link Ray} and returns the resulting {@link Color}.
     * <p>
     * If no intersection is found, the background color is returned.
     * Otherwise, returns the ambient light at the closest intersection point.
     *
     * @param ray the ray to trace
     * @return the resulting color at the ray's closest intersection point
     */
	@Override
	public Color traceRay(Ray ray) {
		List<Intersection> intersections = scene.geometries.calculateIntersections(ray);
		if(intersections == null)
			return scene.background;
		else
			return calcColor(ray.findClosestIntersection(intersections));
	}
	
	/**
     * Computes the color at a given point.
     * <p>
     * Currently returns only the ambient light intensity.
     *
     * @param point the point to evaluate
     * @return the color at the given point
     */
	private Color calcColor(Intersection intersection) {
		
		return scene.ambientLight.getIntensity().add(intersection.geometry.getEmission()); 
	}

}
