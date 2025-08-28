package renderer;

import java.util.List;
import geometries.Intersectable.Intersection;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import static java.lang.System.out;

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
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}

	/**
     * Preprocesses an intersection by computing and storing its normal,
     * intersection direction, and their dot product.
     *
     * @param target the intersection object to preprocess
     * @param ray    the direction of the incoming ray
     * @return {@code true} if the dot product is non-zero (valid intersection), {@code false} otherwise
     */
	public boolean preprocessIntersection (Intersection target ,Vector ray) {
		
		target.directionIntersect = ray.normalize();
		target.normal = target.geometry.getNormal(target.point);
		target.dotNormalAndIntersect = Util.alignZero(ray.dotProduct(target.normal));
		
		return !Util.isZero(target.dotNormalAndIntersect);
	}
	
	/**
     * Configures the intersection with respect to a specific light source.
     * Computes the light direction, dot products, and validates contribution.
     *
     * @param target     the intersection object to update
     * @param typeLight the light source affecting the point
     * @return {@code true} if the light contributes to shading, {@code false} otherwise
     */
	public boolean setLightSource (Intersection target ,LightSource typeLight) {
		
		target.lightType = typeLight;
		target.directionLight = typeLight.getL(target.point).scale(-1.0).normalize();
		target.dotNormalAndLight = Util.alignZero(target.directionLight.dotProduct(target.normal));
		
		return !(Util.isZero(target.dotNormalAndLight) && Util.isZero(target.dotNormalAndIntersect));
	}
	

    /**
     * Calculates the local lighting effects (diffuse + specular) at the given intersection.
     *
     * @param intersection the intersection point to evaluate
     * @return the computed color contribution at this point
     */
    private Color calcLocalEffects (Intersection intersection)
    {
        Color color = intersection.geometry.getEmission();
        for (LightSource lightSource: scene.lights) {
            if (!setLightSource(intersection,lightSource))
                continue;
            if (intersection.dotNormalAndLight * intersection.dotNormalAndIntersect  < 0)
            { 
                Color iL = lightSource.getIntensity(intersection.point);
                color = color
                        .add(iL
                        .scale(calcDiffusive(intersection)
                        .add(calcSpecular(intersection))));
            }
        }
        return color;
    }

	/**
     * Calculates the specular reflection coefficient using the Phong reflection model.
     *
     * @param intersection the intersection to evaluate
     * @return the specular reflection component as a {@link Double3}
     */
	private Double3 calcSpecular(Intersection intersection)  {
		return intersection.geometry.getMaterial().kD.scale(Math.abs(intersection.directionLight.dotProduct(intersection.normal)));
	}
	
	/**
     * Calculates the diffuse reflection component using the Lambertian model
     * combined with a specular highlight factor.
     *
     * @param intersection the intersection to evaluate
     * @return the diffuse reflection component as a {@link Double3}
     */
	private Double3 calcDiffusive(Intersection intersection) {
		Vector reflect = intersection.normal.scale(intersection.directionLight.dotProduct(intersection.normal)).scale(2.0).subtract(intersection.directionLight).normalize();
		return intersection.geometry.getMaterial().kS.scale(Math.pow(Math.max(0,intersection.directionIntersect.dotProduct(reflect) * -1.0), intersection.geometry.getMaterial().nsh));
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
			return calcColor(ray.findClosestIntersection(intersections), ray);
	}
	
	/**
     * Computes the final color at the given intersection point by combining
     * local lighting effects and ambient light.
     *
     * @param intersection the intersection to evaluate
     * @param ray          the incoming ray that generated this intersection
     * @return the resulting color at the intersection point
     */
	private Color calcColor(Intersection intersection, Ray ray) {
		if (!preprocessIntersection(intersection ,ray.getDirection()))
			return Color.BLACK;
		return calcLocalEffects(intersection).add(scene.ambientLight.getIntensity().scale(intersection.geometry.getMaterial().kA)); 
	}

}
