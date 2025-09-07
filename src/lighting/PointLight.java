/**
 * A point light source that emits light equally in all directions
 * from a specific position in space. The light intensity decreases
 * with distance according to attenuation factors.
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The {@code PointLight} class represents a light source located at a
 * specific point in space. Its rays spread outward in all directions,
 * and the intensity diminishes with distance based on configurable
 * attenuation factors.
 */
public class PointLight extends Light implements LightSource {

	/** The position of the light source in the scene */
	protected final Point position;
	
	/** Constant attenuation factor (default: 1.0) */
	private double kC = 1.0;
	
	/** Linear attenuation factor (default: 0.0) */
	private double kL = 0.0;
	
	/** Quadratic attenuation factor (default: 0.0) */
	private double kQ = 0.0;
	
	/**
     * Constructs a {@code PointLight} with the given intensity and position.
     *
     * @param intensity the {@link Color} intensity of the light
     * @param position  the {@link Point} position of the light source
     */
	public PointLight(Color intensity, Point position) {
		super(intensity);
		this.position = position;
	}
	
	/**
     * Sets the constant attenuation factor.
     *
     * @param kC constant attenuation
     * @return this light (for method chaining)
     */
	public PointLight setKc(double kC) {
		this.kC = kC;
		return this;
	}
	
	/**
     * Sets the linear attenuation factor.
     *
     * @param kL linear attenuation
     * @return this light (for method chaining)
     */
	public PointLight setKl(double kL) {
		this.kL = kL;
		return this;
	}
	
	/**
     * Sets the quadratic attenuation factor.
     *
     * @param kQ quadratic attenuation
     * @return this light (for method chaining)
     */
	public PointLight setKq(double kQ) {
		this.kQ = kQ;
		return this;
	}
	
	/**
     * Returns the intensity of the light at a given point, attenuated by distance.
     *
     * @param target the {@link Point} being illuminated
     * @return the attenuated {@link Color} intensity at the point
     */
	@Override
	public Color getIntensity(Point target) {
		double distance = target.distance(position);
		return intensity.scale(1 / (kC + kL * distance + kQ * distance * distance));
	}

	/**
     * Returns the direction of the light rays from the light source to the target point.
     *
     * @param target the {@link Point} being illuminated
     * @return a normalized {@link Vector} pointing from the light source to the target
     */
	@Override
	public Vector getL(Point target) {
		return target.subtract(position).normalize();
	}

	/**
	 * Return the distance between point light to a point
	 * @param point intersection {@link Point} from the distance
	 */
	@Override
	public double getDistance(Point pointIntersection) {
		return position.distance(pointIntersection);	
	}

}
