/**
 * A spotlight is a point light source with a specific direction,
 * simulating a cone of light (like a flashlight or a stage spotlight).

 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The {@code SpotLight} class represents a point light source with a
 * preferred direction, creating a cone-shaped illumination.

 */
public class SpotLight extends PointLight {

	/** The normalized direction of the spotlight’s axis */
	private final Vector direction;
	
	/**
     * Cosine of the maximum allowed angle between the light’s axis and the direction
     * toward the target point. Effectively defines the spotlight’s beam width.
     */
	private double narrowBeam = 90;
	
	/**
     * Constructs a {@code SpotLight} with the given intensity, position, and direction.
     *
     * @param intensity the {@link Color} intensity of the light
     * @param position  the {@link Point} position of the light source
     * @param direction the {@link Vector} direction of the spotlight’s axis (will be normalized)
     */
	public SpotLight(Color intensity ,Point position, Vector direction) {
		super(intensity, position);
		this.direction = direction.normalize();
		this.narrowBeam = 0;
	}
	
	/**
     * Constructs a {@code SpotLight} with the given intensity, position, direction, and cone angle.
     *
     * @param intensity the {@link Color} intensity of the light
     * @param position  the {@link Point} position of the light source
     * @param direction the {@link Vector} direction of the spotlight’s axis (will be normalized)
     * @param angleDeg   the spotlight cone angle in degrees (narrower angle = tighter beam)
     */
	public SpotLight(Color intensity ,Point position, Vector direction, double angleDeg) {
		super(intensity, position);
		this.direction = direction.normalize();
		this.narrowBeam = Math.cos(Math.toRadians(angleDeg));
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

	public SpotLight setNarrowBeam(double angleDeg) {
		narrowBeam = Math.cos(Math.toRadians(angleDeg));
		return this;
	}	
	
	/**
     * Returns the intensity of the spotlight at a given point.
     *
     * @param target the {@link Point} being illuminated
     * @return the attenuated {@link Color} intensity at the target point,
     *         or {@link Color#BLACK} if the point lies outside the spotlight’s cone
     */
	@Override
	public Color getIntensity(Point target) {

 		double dot = direction.normalize().dotProduct(super.getL(target).normalize());
		if (dot < narrowBeam)
			return Color.BLACK;
		else if (dot >= narrowBeam - Math.cos(Math.toRadians(20)))
			return super.getIntensity(target).scale(dot);
		else
			return super.getIntensity(target).scale((dot -  narrowBeam - Math.cos(Math.toRadians(20))) / -30);
	}
}

