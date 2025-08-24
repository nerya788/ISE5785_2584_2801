/**
 * 
 */
package lighting;
import primitives.Color;

/**
 * 
 */
abstract class Light {
	final protected Color intensity;
	
	public Light(Color _intensity){
		intensity = _intensity;
		}
	
	public Color getIntensity() {
		return intensity;
		}
	
}
