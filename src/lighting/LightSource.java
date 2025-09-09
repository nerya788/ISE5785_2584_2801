/**
 * Represents a source of light in the scene.
 * <p>
 * Light sources define how light intensity and direction are computed
 * at a given point in space. Implementations include point lights,
 * directional lights, and spotlights, each with their own behavior.
 */
package lighting;

import primitives.*;

/**
 * The {@code LightSource} interface defines the contract for all
 * 
 */
public interface LightSource {

	/**
	 * Returns the intensity (color contribution) of the light at a given point.
	 *
	 * @param p the {@link Point} being illuminated
	 * @return the {@link Color} intensity of the light at the point
	 */
	Color getIntensity(Point p);

	/**
	 * 
	 * @param p the {@link Point} being illuminated
	 * @return a {@link Vector} representing the light’s direction relative to the
	 *         point
	 */
	Vector getL(Point p);

	/**
	 * Return the distance between point light to a point
	 * 
	 * @param point intersection {@link Point} from the distance
	 */
	double getDistance(Point p);

}
