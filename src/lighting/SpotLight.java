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
	private double narrowBeam = 90;
	
	public SpotLight(Color _intensity ,Point _position, Vector _direction) {
		super(_intensity, _position);
		direction = _direction.normalize();
		narrowBeam = 0;
	}
	
	public SpotLight(Color _intensity ,Point _position, Vector _direction, double angleDeg) {
		super(_intensity, _position);
		direction = _direction.normalize();
		narrowBeam = Math.cos(Math.toRadians(angleDeg));
	}
	

	public SpotLight setKc(double _kC) {
		super.setKc(_kC);
		return this;
	}
	
	public SpotLight setKl(double _kL) {
		super.setKl(_kL);
		return this;
	}
	public SpotLight setKq(double _kQ) {
		super.setKq(_kQ);
		return this;
	}
	
	@Override
	public Color getIntensity(Point target) {
		
		
		double dot = direction.normalize().dotProduct(super.getL(target).normalize());
		if (dot >= narrowBeam)
			return super.getIntensity(target).scale(dot);
		return Color.BLACK;
	}
}
