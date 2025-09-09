/**
 * A spotlight is a point light source with a specific direction,
 * simulating a cone of light (like a flashlight or a stage spotlight).

 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The {@code SpotLight} class represents a point light source with a preferred
 * direction, creating a cone-shaped illumination.
 * 
 */
public class SpotLight extends PointLight {

	/** The normalized direction of the spotlight’s axis */
	private final Vector direction;

	/**
	 * Constructs a {@code SpotLight} with the given intensity, position, and
	 * direction.
	 *
	 * @param intensity the {@link Color} intensity of the light
	 * @param position  the {@link Point} position of the light source
	 * @param direction the {@link Vector} direction of the spotlight’s axis (will
	 *                  be normalized)
	 */
	public SpotLight(Color intensity, Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
	}

	/**
	 * Sets the constant attenuation factor.
	 *
	 * @param kC constant attenuation
	 * @return this spotlight (for method chaining)
	 */
	public SpotLight setKc(double kC) {
		super.setKc(kC);
		return this;
	}

	/**
	 * Sets the linear attenuation factor.
	 *
	 * @param kL linear attenuation
	 * @return this spotlight (for method chaining)
	 */
	public SpotLight setKl(double kL) {
		super.setKl(kL);
		return this;
	}

	/**
	 * Sets the quadratic attenuation factor.
	 *
	 * @param kQ quadratic attenuation
	 * @return this spotlight (for method chaining)
	 */
	public SpotLight setKq(double kQ) {
		super.setKq(kQ);
		return this;
	}

	/**
	 * Returns the intensity of the spotlight at a given point.
	 *
	 * @param target the {@link Point} being illuminated
	 * @return the attenuated {@link Color} intensity at the target point, or
	 *         {@link Color#BLACK} if the point lies outside the spotlight’s cone
	 */
	@Override
	public Color getIntensity(Point target) {

		double dot = direction.dotProduct(super.getL(target));
		if (dot < 0)
			return Color.BLACK;
		return super.getIntensity(target).scale(dot);
	}

	/**
	 * Return the distance between point light to a point
	 * 
	 * @param point intersection {@link Point} from the distance
	 */
	@Override
	public double getDistance(Point pointIntersection) {
		return position.distance(pointIntersection);
	}

}
