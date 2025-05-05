package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

/**
 * 
 */
public class SimpleRayTracer extends RayTracerBase {

	/**
	 * מפעיל את קונטרקטור האבא
	 * @param copy
	 */
	public SimpleRayTracer(Scene copy) {
		super(copy);
	}

	@Override
	public Color traceRay(Ray ray) {
		// TODO Auto-generated method stub
		// למצוא חת=יתוכמ
		List<Point> intersections = scene.geometries.findIntersections(ray);
		if(intersections == null) {
			return scene.background;
		}
		else {
			Point point = ray.findClosestPoint(intersections);
			return calcColor(point);
		}
	}
	
	/**
	 * 
	 * @param point
	 * @return
	 */
	private Color calcColor(Point point) {
		return scene.ambientLight.getIntensity(); 
	}

}
