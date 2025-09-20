package lighting;

import primitives.*;

/**
 * Represents ambient lighting in a scene. Ambient light provides a constant
 * lighting level applied to all objects equally, regardless of their position
 * or orientation.
 */
public class AmbientLight extends Light {

	/**
	 * Constructs an AmbientLight with a given intensity.
	 * 
	 * @param intensity the {@link Color} representing the light intensity
	 */
	public AmbientLight(Color intensity) {
		super(intensity);
	}

	/**
	 * A predefined instance representing no ambient light (black).
	 */
	public static AmbientLight NONE = new AmbientLight(Color.BLACK);

}
