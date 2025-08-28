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
public class PointLight extends Light implements LightSource {

	protected final Point position;
	
	private double kC = 1.0;
	private double kL = 0.0;
	private double kQ = 0.0;
	
	public PointLight( Color _intensity, Point _position) {
		super(_intensity);
		position = _position;
	}
	
	public PointLight setKc(double _kC) {
		kC = _kC;
		return this;
	}
	
	public PointLight setKl(double _kL) {
		kL = _kL;
		return this;
	}
	public PointLight setKq(double _kQ) {
		kQ = _kQ;
		return this;
	}
	
	@Override
	public Color getIntensity(Point target) {
		double distance = target.distance(position);
		return intensity.scale (1 / (kC + kL * distance + kQ * distance * distance));
	}

	@Override
	public Vector getL(Point target) {
		return target.subtract(position).normalize();
	}

}
