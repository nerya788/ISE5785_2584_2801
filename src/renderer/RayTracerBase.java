package renderer;

import geometries.Geometries;
import lighting.AmbientLight;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * 
**/
public abstract class RayTracerBase {
	
	/**
	 * 
	 */
	protected final Scene scene = null; // לתת לו ערך כאן??
	
	/**
	 * 
	 * @param copy
	 */
	public RayTracerBase(Scene copy){
		scene.name = copy.name;
		scene.setBackground(copy.background);
		scene.setAmbientLight(copy.ambientLight);
		scene.setGeometries(copy.geometries);
		
	}
	
	/**
	 * 
	 * @param ray
	 * @return
	 */
	public abstract Color traceRay(Ray ray);
}
