/**
 * Represents the material properties of a surface in the rendering engine.
 * <p>
 * A material defines how an object interacts with light, including ambient,
 * diffuse, and specular reflection components, as well as shininess for
 * specular highlights.
 */
package primitives;

/**
 * The {@code Material} class encapsulates the coefficients that define how
 * light interacts with a geometric surface.
 * <ul>
 *   <li>{@code kA} – Ambient reflection coefficient (how much ambient light is reflected)</li>
 *   <li>{@code kD} – Diffuse reflection coefficient (Lambertian reflection)</li>
 *   <li>{@code kS} – Specular reflection coefficient (Phong reflection)</li>
 *   <li>{@code nsh} – Shininess factor (controls the size/intensity of specular highlights)</li>
 * </ul>
 */
public class Material {
	
	/** Ambient reflection coefficient (default: {@link Double3#ONE}) */
	public Double3 kA = Double3.ONE;
	
	/** Diffuse reflection coefficient (default: {@link Double3#ZERO}) */
	public Double3 kD = Double3.ZERO;
	
	/** Specular reflection coefficient (default: {@link Double3#ZERO}) */
	public Double3 kS = Double3.ZERO;
	
	/** Shininess factor for specular highlights (default: 0) */
	public int nsh = 0;


	/**
     * Sets the ambient reflection coefficient.
     * 
     * @param _kA ambient coefficient as {@link Double3}
     * @return this material (for method chaining)
     */
	public Material setKA(Double3 _kA) {
		kA = _kA;
		return this;
	}
	
	/**
     * Sets the ambient reflection coefficient.
     * 
     * @param _kA ambient coefficient as scalar (applied to all components)
     * @return this material (for method chaining)
     */
	public Material setKA(double _kA) {
		kA = new Double3(_kA);
		return this;
	}
	
	/**
     * Sets the diffuse reflection coefficient.
     * 
     * @param _kD diffuse coefficient as {@link Double3}
     * @return this material (for method chaining)
     */
	public Material setKD(Double3 _kD) {
		kD = _kD;
		return this;
	}
	
	/**
     * Sets the diffuse reflection coefficient.
     * 
     * @param _kD diffuse coefficient as scalar (applied to all components)
     * @return this material (for method chaining)
     */
	public Material setKD(double _kD) {
		kD = new Double3(_kD);
		return this;
	}
	
	/**
     * Sets the specular reflection coefficient.
     * 
     * @param _kS specular coefficient as {@link Double3}
     * @return this material (for method chaining)
     */
	public Material setKS(Double3 _kS) {
		kS = _kS;
		return this;
	}
	
	/**
     * Sets the specular reflection coefficient.
     * 
     * @param _kS specular coefficient as scalar (applied to all components)
     * @return this material (for method chaining)
     */
	public Material setKS(double _kS) {
		kS = new Double3(_kS);
		return this;
	}
	
	/**
     * Sets the shininess factor (Phong exponent) that controls
     * the size and intensity of specular highlights.
     * 
     * @param _nsh shininess value (higher = smaller, sharper highlights)
     * @return this material (for method chaining)
     */
	public Material setShininess(int _nsh) {
		nsh = _nsh;
		return this;
	}
}
