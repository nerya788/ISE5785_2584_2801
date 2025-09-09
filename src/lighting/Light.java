/**
 * Base class for all types of lights in the scene.
 * <p>
 * A light defines an intensity (color and strength) that contributes to the
 * illumination of objects. Specific light types (e.g., directional, point, spot)
 * extend this class and provide additional behavior for how the light interacts
 * with the scene.
 */
package lighting;

import primitives.Color;

/**
 * The {@code Light} abstract class represents a generic light source. It stores
 * the basic property of light — its {@link Color} intensity. Subclasses define
 * how the light propagates and interacts with objects.
 */
abstract class Light {

	/** The intensity (color and brightness) of the light source */
	final protected Color intensity;

	/**
	 * Constructs a new {@code Light} with the given intensity.
	 *
	 * @param intensity the color and strength of the light
	 */
	public Light(Color intensity) {
		this.intensity = intensity;
	}

	/**
	 * Returns the intensity of this light source.
	 *
	 * @return the {@link Color} representing the light’s intensity
	 */
	public Color getIntensity() {
		return intensity;
	}

}
