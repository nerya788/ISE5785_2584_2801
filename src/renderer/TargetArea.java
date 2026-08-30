package renderer;

import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Vector;

/**
 * A rectangular region in 3D space, spanned by two orthogonal axes around a
 * center point.
 * <p>
 * It is the reusable building block for every effect that averages many rays
 * over an area: anti-aliasing (the area is a single pixel on the view plane),
 * and later — with the same code — depth of field (a lens aperture), soft
 * shadows (an area light) or glossy reflection/refraction (a cross-section of
 * the scattering cone).
 * <p>
 * Points are addressed in normalized coordinates where {@code (0, 0)} is the
 * center and each axis runs from {@code -1} on one edge to {@code +1} on the
 * opposite edge.
 */
public class TargetArea {

	/** Center of the rectangle. */
	private final Point center;
	/** Unit vector of the local horizontal axis. */
	private final Vector right;
	/** Unit vector of the local vertical axis. */
	private final Vector up;
	/** Half of the full width (extent along {@link #right}). */
	private final double halfWidth;
	/** Half of the full height (extent along {@link #up}). */
	private final double halfHeight;

	/**
	 * @param center center of the rectangle
	 * @param right  unit vector of the horizontal axis
	 * @param up     unit vector of the vertical axis
	 * @param width  full width along {@code right}
	 * @param height full height along {@code up}
	 */
	public TargetArea(Point center, Vector right, Vector up, double width, double height) {
		this.center = center;
		this.right = right;
		this.up = up;
		this.halfWidth = width / 2;
		this.halfHeight = height / 2;
	}

	/** @return the center point of the area */
	public Point center() {
		return center;
	}

	/**
	 * Returns the point of the area at the given normalized coordinates.
	 *
	 * @param u horizontal position, {@code -1}..{@code 1}
	 * @param v vertical position, {@code -1}..{@code 1}
	 * @return the corresponding point in 3D space
	 */
	public Point pointAt(double u, double v) {
		Point p = center;
		double du = u * halfWidth;
		double dv = v * halfHeight;
		if (!isZero(du))
			p = p.add(right.scale(du));
		if (!isZero(dv))
			p = p.add(up.scale(dv));
		return p;
	}

	/**
	 * Builds a {@code density × density} grid of sample points covering the area.
	 * With {@code jitter} the sample in each cell is randomly displaced inside that
	 * cell (stratified sampling); otherwise it sits at the cell center.
	 *
	 * @param density number of cells per axis; {@code <= 1} yields only the center
	 * @param jitter  whether to randomize the sample inside each cell
	 * @return {@code max(1, density)}{@code ^2} sample points
	 */
	public List<Point> grid(int density, boolean jitter) {
		if (density <= 1)
			return List.of(center);

		List<Point> points = new ArrayList<>(density * density);
		for (int row = 0; row < density; row++) {
			for (int col = 0; col < density; col++) {
				double du = jitter ? Math.random() : 0.5;
				double dv = jitter ? Math.random() : 0.5;
				double u = (col + du) / density * 2 - 1;
				double v = (row + dv) / density * 2 - 1;
				points.add(pointAt(u, v));
			}
		}
		return points;
	}

	/**
	 * The four corners of the area, ordered
	 * {@code [bottom-left, bottom-right, top-left, top-right]}.
	 *
	 * @return the four corner points
	 */
	public Point[] corners() {
		return new Point[] { pointAt(-1, -1), pointAt(1, -1), pointAt(-1, 1), pointAt(1, 1) };
	}

	/**
	 * Splits the area into its four quadrants for recursive adaptive sampling,
	 * ordered {@code [bottom-left, bottom-right, top-left, top-right]} to match
	 * {@link #corners()}.
	 *
	 * @return the four quadrant sub-areas
	 */
	public TargetArea[] subdivide() {
		double w = halfWidth;  // a quadrant's full width equals the current half width
		double h = halfHeight;
		return new TargetArea[] { //
				new TargetArea(pointAt(-0.5, -0.5), right, up, w, h), //
				new TargetArea(pointAt(0.5, -0.5), right, up, w, h), //
				new TargetArea(pointAt(-0.5, 0.5), right, up, w, h), //
				new TargetArea(pointAt(0.5, 0.5), right, up, w, h) };
	}
}
