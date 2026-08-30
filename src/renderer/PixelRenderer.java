package renderer;

import java.util.List;

import primitives.Color;
import primitives.Point;
import primitives.Ray;

/**
 * Computes the color of a single pixel by sampling one or more rays over the
 * pixel's area and averaging the results.
 * <p>
 * This is the seam between "which rays represent this pixel" (here) and "what
 * color is one ray" ({@link RayTracerBase#traceRay(Ray)}). The ray tracer is
 * never told anything about pixels, view planes or sampling.
 * <p>
 * Three modes, in priority order:
 * <ol>
 * <li><b>Adaptive super-sampling</b> ({@code adaptiveDepth > 0}): trace the pixel
 * corners, then recursively subdivide only the quadrants whose corner colors
 * disagree, down to {@code adaptiveDepth} levels. Cheap on flat areas, dense on
 * edges.</li>
 * <li><b>Uniform super-sampling</b> ({@code density > 1}): a
 * {@code density × density} (optionally jittered) grid of rays, averaged.</li>
 * <li><b>No anti-aliasing</b>: a single ray through the pixel center.</li>
 * </ol>
 */
class PixelRenderer {

	/** The engine that colors one ray. */
	private final RayTracerBase rayTracer;
	/** Grid density per axis for uniform super-sampling ({@code >= 1}). */
	private final int density;
	/** Whether grid samples are jittered inside their cells. */
	private final boolean jitter;
	/** Maximum recursion depth for adaptive super-sampling ({@code 0} disables). */
	private final int adaptiveDepth;

	/**
	 * @param rayTracer     the ray tracing engine
	 * @param density       uniform super-sampling grid density per axis
	 * @param jitter        whether to jitter uniform grid samples
	 * @param adaptiveDepth adaptive super-sampling recursion depth ({@code 0} off)
	 */
	PixelRenderer(RayTracerBase rayTracer, int density, boolean jitter, int adaptiveDepth) {
		this.rayTracer = rayTracer;
		this.density = Math.max(1, density);
		this.jitter = jitter;
		this.adaptiveDepth = Math.max(0, adaptiveDepth);
	}

	/**
	 * @return {@code true} if this renderer shoots a single ray per pixel
	 */
	boolean isSingleSample() {
		return adaptiveDepth == 0 && density <= 1;
	}

	/**
	 * Computes the color of the pixel described by {@code pixel}, as seen from
	 * {@code eye}.
	 *
	 * @param eye   the camera position (all sample rays originate here)
	 * @param pixel the pixel's area on the view plane
	 * @return the averaged pixel color
	 */
	Color renderPixel(Point eye, TargetArea pixel) {
		if (adaptiveDepth > 0) {
			Point[] c = pixel.corners();
			return adaptive(eye, pixel, trace(eye, c[0]), trace(eye, c[1]), trace(eye, c[2]), trace(eye, c[3]),
					adaptiveDepth);
		}

		List<Point> samples = pixel.grid(density, jitter);
		Color sum = Color.BLACK;
		for (Point p : samples)
			sum = sum.add(trace(eye, p));
		return sum.reduce(samples.size());
	}

	/**
	 * Recursive adaptive super-sampling over a rectangular area whose four corner
	 * colors are already known.
	 *
	 * @param eye   the camera position
	 * @param area  the area to sample (bottom-left, bottom-right, top-left,
	 *              top-right ordering as in {@link TargetArea#corners()})
	 * @param cBL   color at the bottom-left corner
	 * @param cBR   color at the bottom-right corner
	 * @param cTL   color at the top-left corner
	 * @param cTR   color at the top-right corner
	 * @param depth remaining recursion levels
	 * @return the estimated average color of the area
	 */
	private Color adaptive(Point eye, TargetArea area, Color cBL, Color cBR, Color cTL, Color cTR, int depth) {
		Color average = cBL.add(cBR, cTL, cTR).reduce(4);
		if (depth == 0 || (cBL.equals(cBR) && cBL.equals(cTL) && cBL.equals(cTR)))
			return average;

		// Sample the shared points once, then hand them to the four quadrants.
		Color cB = trace(eye, area.pointAt(0, -1)); // bottom edge midpoint
		Color cT = trace(eye, area.pointAt(0, 1)); // top edge midpoint
		Color cL = trace(eye, area.pointAt(-1, 0)); // left edge midpoint
		Color cR = trace(eye, area.pointAt(1, 0)); // right edge midpoint
		Color cC = trace(eye, area.center()); // center

		TargetArea[] q = area.subdivide(); // BL, BR, TL, TR
		return adaptive(eye, q[0], cBL, cB, cL, cC, depth - 1) //
				.add(adaptive(eye, q[1], cB, cBR, cC, cR, depth - 1), //
						adaptive(eye, q[2], cL, cC, cTL, cT, depth - 1), //
						adaptive(eye, q[3], cC, cR, cT, cTR, depth - 1)) //
				.reduce(4);
	}

	/**
	 * Traces one ray from {@code eye} towards {@code target}.
	 *
	 * @param eye    ray origin
	 * @param target a point the ray passes through
	 * @return the ray's color
	 */
	private Color trace(Point eye, Point target) {
		return rayTracer.traceRay(new Ray(eye, target.subtract(eye)));
	}
}
