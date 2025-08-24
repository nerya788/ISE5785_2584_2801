/**
 * 
 */
package primitives;

/**
 * 
 */
public class Material {
	public Double3 KA = Double3.ONE;
	
	public Material setKA(Double3 _KA) {
		KA = _KA;
		return this;
	}
	
	public Material setKA(double _KA) {
		KA = new Double3(_KA);
		return this;
	}

}
