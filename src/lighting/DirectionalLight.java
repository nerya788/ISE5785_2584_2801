/**
 * A light source that simulates light coming from a specific direction,
 * like sunlight. The light rays are considered parallel, and the intensity
 * is constant everywhere in the scene.
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The {@code DirectionalLight} class represents a directional light source.
 */
public class DirectionalLight extends Light implements LightSource {

	/** The normalized direction of the light rays */
	private final Vector direction;

	/**
	 *
	 * @param intensity the {@link Color} intensity of the light
	 * @param direction the direction of the light rays (will be normalized)
	 */
	public DirectionalLight(Color intensity, Vector direction) {
		super(intensity);
		this.direction = direction.normalize();
	}

	/**
	 * @param p the {@link Point} being illuminated (unused, since intensity is
	 *          uniform)
	 * @return the light’s {@link Color} intensity
	 */
	@Override
	public Color getIntensity(Point p) {
		return intensity;
	}

	/**
	 * @param p the {@link Point} being illuminated (unused, since direction is
	 *          uniform)
	 * @return the normalized {@link Vector} of the light’s direction
	 */
	@Override
	public Vector getL(Point p) { // point not Unnecessary
		return direction;
	}

	/**
	 * Return the distance between point light to a point
	 * 
	 * @param point intersection {@link Point} from the distance
	 */
	@Override
	public double getDistance(Point pointIntersection) {
		return Double.POSITIVE_INFINITY;
	}

}
