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

	//Delta of distance for shading rays that we take from the body to the light source to prevent self-shading 
	private static final double DELTA = 0.1;
	
	/**
     * Constructs a {@code SimpleRayTracer} using the provided scene.
     * 
     * @param newScene the scene to be copied into the tracer engine
     */
	public SimpleRayTracer(Scene scene) {
		super(scene);
	}
	
	/**
	 * Determines whether a given intersection point is illuminated (unshaded)
	 * with respect to a specific light source.

	 *
	 * @param intersection the {@link Intersection} containing the hit point,
	 *                     its normal, and the light source being evaluated
	 * @return {@code true} if the light source is visible from the intersection
	 *         point (no blocking geometry exists between them), {@code false}
	 *         if the point is in shadow
	 */
	private boolean unshaded(Intersection intersection) {
		Vector directionShaded = intersection.directionLight.scale(-1);
		Vector deltaVector = intersection.normal.scale(intersection.dotNormalAndLight < 0 ? DELTA : -DELTA);
		Point point =  intersection.point.add(deltaVector);
	    Ray lightRay = new Ray(point, directionShaded);
		
		  var intersections = scene.geometries.findIntersections(lightRay);
		  if(intersections == null) return true;
		  
		  double distance = intersection.lightType.getDistance(intersection.point); 
		   
		  for (Point intersect : intersections) { 
	      if( intersection.point.distance(intersect) < distance )
	    	  return false;
		  }
		  
		return true;
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
		target.directionLight = typeLight.getL(target.point);
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
			if ((intersection.dotNormalAndLight * intersection.dotNormalAndIntersect > 0)
					&& unshaded(intersection))
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
     * Calculates the diffuse reflection component using the Lambertian model
     * combined with a specular highlight factor.
     *
     * @param intersection the intersection to evaluate
     * @return the diffuse reflection component as a {@link Double3}
     */
	private Double3 calcDiffusive(Intersection intersection)  {
		return intersection.material.kD.scale(Math.abs(intersection.dotNormalAndLight));
	}
	
	/**
     * Calculates the specular reflection coefficient using the Phong reflection model.
     * 
     * use the formula:
     * VectorReflect = 2 * directionLight.dotProduct(normal)  * normal - directionLight.
     * specularColor = Ks * (-1  *  directionIntersect.dotProduct(VectorReflect)) ^ nsh.
     * 
     * Which in short, if we put everything together, we get:
     * specularColor = Ks * (-1  *  directionIntersect.dotProduct(directionLight - 2 * directionLight.dotProduct(normal)  * normal)) ^ nsh.
     * 
     * @param intersection the intersection to evaluate
     * @return the specular reflection component as a {@link Double3}
     */
	private Double3 calcSpecular(Intersection intersection) {

		return intersection.material.kS.scale(Math.pow(Math.max(0,intersection.directionIntersect.scale(-1).dotProduct(intersection.directionLight
						   .subtract(intersection.normal.scale(intersection.dotNormalAndLight).scale(2.0)).normalize())),intersection.material.nsh));
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
		return calcLocalEffects(intersection).add(scene.ambientLight.getIntensity().scale(intersection.material.kA)); 
	}

}
