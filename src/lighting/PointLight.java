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
	
	public PointLight(Point _position, Color _intensity) {
		super(_intensity);
		position = _position;
	}
	
	public PointLight setkC(double _kC) {
		kC = _kC;
		return this;
	}
	
	public PointLight setkL(double _kL) {
		kC = _kL;
		return this;
	}
	public PointLight setkQ(double _kQ) {
		kC = _kQ;
		return this;
	}
	
	@Override
	public Color getIntensity(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector getL(Point p) {
		// TODO Auto-generated method stub
		return null;
	}

}
