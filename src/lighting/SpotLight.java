/**
 * 
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * 
 */
public class SpotLight extends PointLight {

	private final Vector direction;
	
	public SpotLight(Vector _direction ,Point _position, Color _intensity) {
		super(_position,_intensity);
		direction = _direction;
	}
	
	public SpotLight setkC(double _kC) {
		super.setkC(_kC);
		return this;
	}
	
	public SpotLight setkL(double _kL) {
		super.setkC(_kL);
		return this;
	}
	public SpotLight setkQ(double _kQ) {
		super.setkC(_kQ);
		return this;
	}
}
