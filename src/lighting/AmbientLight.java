package lighting;
import primitives.*;

/**
 * Represents ambient lighting in a scene.
 * Ambient light provides a constant lighting level applied to all objects equally,
 * regardless of their position or orientation.
 */
public class AmbientLight {
	
	/** The intensity (color) of the ambient light */
	private final Color intensity;
	
	/**
     * Constructs an AmbientLight with a given intensity.
     * 
     * @param _intensity the {@link Color} representing the light intensity
     */
	public AmbientLight(Color _intensity){
		intensity =  _intensity;
	}

	/**
     * A predefined instance representing no ambient light (black).
     */
	public static AmbientLight NONE = new AmbientLight(Color.BLACK);
	
	 /**
     * Returns the intensity of the ambient light.
     * 
     * @return the {@link Color} representing the light intensity
     */
	public Color getIntensity() {
		return intensity;
	}
	
	
}
