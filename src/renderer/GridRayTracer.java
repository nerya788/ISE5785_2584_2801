/**
 * 
 */
package renderer;

import java.util.LinkedList;
import java.util.List;

import geometries.Intersectable;
import geometries.Intersectable.Intersection;

import renderer.Camera.rayCreationSpace;
import scene.Scene;
import primitives.*;
import static java.lang.System.out;

/**
 * 
 */
public class GridRayTracer extends SimpleRayTracer {

	public static final int n = 9;

	/**
	 * Constructs a {@code GridRayTracer} using the provided scene.
	 * 
	 * @param newScene the scene to be copied into the tracer engine
	 */
	public GridRayTracer(Scene scene) {
		super(scene);
	}

	/**
	 * Traces a given {@link Ray} and returns the resulting {@link Color}.
	 * <p>
	 * If no intersection is found, the background color is returned. Otherwise,
	 * returns the ambient light at the closest intersection point.
	 *
	 * @param ray the ray to trace
	 * @return the resulting color at the ray's closest intersection point
	 */
	@Override
	public Color traceRay(rayCreationSpace details) {
		Color color = super.traceRay(details);

		if (!notSame(details, color))
			return color;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				Point newPIJ = details.pIJ().add(details.vRight()
						.scale((-details.rX() / 2) + i * (details.rX() / n) + Math.random() * (details.rX() / n))
						.add(details.vUp().scale(
								(-details.rY() / 2) + j * (details.rY() / n) + Math.random() * (details.rY() / n))));

				color = color.add(super.traceRay(new rayCreationSpace(details.p0(),details.vRight(),details.vUp(),newPIJ, details.rX(), details.rY())));
			}
		}

		return color.scale((double) 1 / (n * n + 1));
	}

	/**
	 * Check the four corner samples of the pixel: return true if any corner's
	 * color differs from the provided reference color.
	 *
	 * @param details sampling & pixel geometry information
	 * @param color   reference color to compare against
	 * @return {@code true} when at least one corner differs, {@code false} when all equal
	 */
	private boolean notSame(rayCreationSpace details, Color color) {
		for (int i = -1; i <= 1; i = i + 2) {
			for (int j = -1; j <= 1; j = j + 2) {
				Point p = details.pIJ().add(details.vRight().scale(i * details.rX() / 2.0))
						.add(details.vUp().scale(j * details.rY() / 2.0));

				if (!super.traceRay(new rayCreationSpace(details.p0(),details.vRight(),details.vUp(),p, details.rX(), details.rY())).equals(color)) {
					return true;
				}
			}
		}
		return false;
	}
}
