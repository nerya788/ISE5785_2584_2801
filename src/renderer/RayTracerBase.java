package renderer;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;
import primitives.Ray;
import renderer.Camera.rayCreationSpace;
import scene.Scene;

/**
 * Abstract base class for ray tracing engines.
 * <p>
 * This class provides the basic infrastructure for performing ray tracing,
 * including access to the scene's geometries, background, and ambient light.
 * Specific ray tracing strategies should extend this class and implement the
 * {@link #traceRay(Ray)} method.
 */
public abstract class RayTracerBase {

	/**
	 * The scene to be rendered, including geometries, lighting, and background.
	 */
	protected final Scene scene;

	/**
	 * Constructs a RayTracerBase with a deep copy of the given scene's core
	 * attributes.
	 * <p>
	 * Only essential components of the scene are copied (name, background, ambient
	 * light, geometries), not including camera, lights, or advanced settings.
	 *
	 * @param newScen the scene to copy into the ray tracer
	 */
	public RayTracerBase(Scene newScen) {
		scene = new Scene(newScen.name);
		scene.setBackground(newScen.background);
		scene.setAmbientLight(newScen.ambientLight);
		scene.setGeometries(newScen.geometries);
		scene.setlights(newScen.lights);

	}

	/**
	 * Calculates the color resulting from tracing a given ray through the scene.
	 * <p>
	 * This method must be implemented by subclasses to define a specific ray
	 * tracing algorithm.
	 *
	 * @param ray the ray to trace
	 * @return the computed {@link Color} resulting from the intersection of the ray
	 *         with the scene
	 */
	public abstract Color traceRay(rayCreationSpace details);
}
