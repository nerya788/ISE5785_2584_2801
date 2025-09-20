package unittests.renderer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.Camera.rayCreationSpace;

/**
 * Testing Camera Class
 * 
 * @author Dan
 */
class CameraTest {
	/** Camera builder for the tests */

	private final Camera.Builder cameraBuilder = Camera.getBuilder().setLocation(Point.ZERO).setViewPlaneDistance(10);
	/** Assert failure message for a bad ray */
	private static final String BAD_RAY = "Bad ray";

	/**
	 * Test method for {@link renderer.Camera#constructRay(int, int, int, int)}.
	 */
	@Test
	void testConstructRay() {
		cameraBuilder.setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0));
		Camera camera1 = cameraBuilder.setViewPlaneSize(8, 8).build();
		Camera camera2 = cameraBuilder.setViewPlaneSize(6, 6).build();

		// ============ Equivalence Partitions Tests ==============
		// EP01: 4X4 Inside (1,1)
		rayCreationSpace details1 = camera1.constructRay(4, 4, 1, 1);
		assertEquals(new Ray(Point.ZERO, new Vector(1, -1, -10)),
				new Ray(details1.p0(), details1.pIJ().subtract(details1.p0())), BAD_RAY);

		// =============== Boundary Values Tests ==================
		// BV01: 4X4 Corner (0,0)
		rayCreationSpace details2 = camera1.constructRay(4, 4, 0, 0);
		assertEquals(new Ray(Point.ZERO, new Vector(3, -3, -10)),
				new Ray(details2.p0(), details2.pIJ().subtract(details2.p0())), BAD_RAY);

		// BV02: 4X4 Side (0,1)
		rayCreationSpace details3 = camera1.constructRay(4, 4, 1, 0);
		assertEquals(new Ray(Point.ZERO, new Vector(1, -3, -10)),
				new Ray(details3.p0(), details3.pIJ().subtract(details3.p0())), BAD_RAY);

		// BV03: 3X3 Center (1,1)
		rayCreationSpace details4 = camera2.constructRay(3, 3, 1, 1);
		assertEquals(new Ray(Point.ZERO, new Vector(0, 0, -10)),
				new Ray(details4.p0(), details4.pIJ().subtract(details4.p0())), BAD_RAY);

		// BV04: 3X3 Center of Upper Side (0,1)
		rayCreationSpace details5 = camera2.constructRay(3, 3, 1, 0);
		assertEquals(new Ray(Point.ZERO, new Vector(0, -2, -10)),
				new Ray(details5.p0(), details5.pIJ().subtract(details5.p0())), BAD_RAY);

		// BV05: 3X3 Center of Left Side (1,0)
		rayCreationSpace details6 = camera2.constructRay(3, 3, 0, 1);
		assertEquals(new Ray(Point.ZERO, new Vector(2, 0, -10)),
				new Ray(details6.p0(), details6.pIJ().subtract(details6.p0())), BAD_RAY);

		// BV06: 3X3 Corner (0,0)
		rayCreationSpace details7 = camera2.constructRay(3, 3, 0, 0);
		assertEquals(new Ray(Point.ZERO, new Vector(2, -2, -10)),
				new Ray(details7.p0(), details7.pIJ().subtract(details7.p0())), BAD_RAY);
	}

	@Test
	void testBuilder() throws Exception {
		cameraBuilder.setViewPlaneSize(4, 4).setResolution(2, 2);

		// ============ Equivalence Partitions Tests ==============
		// EP01: set to a target point without up vector
		Point target1 = new Point(10, -10, 0);
		Camera camera1 = cameraBuilder.setDirection(target1).build();
		Point center1 = target1.subtract(Point.ZERO).normalize().scale(10);
		Vector right1 = Vector.AXIS_Z;
		Vector up1 = new Vector(1, 1, 0).normalize();
		Vector direction1 = center1.add(up1.normalize()).subtract(right1).subtract(Point.ZERO);
		rayCreationSpace details8 = camera1.constructRay(2, 2, 0, 0);
		assertEquals(new Ray(Point.ZERO, direction1), new Ray(details8.p0(), details8.pIJ().subtract(details8.p0())));

		// EP02: set to a target point with up vector
		Point target2 = new Point(0, 5, 0);
		Camera camera2 = cameraBuilder.setDirection(target2, new Vector(0, 1, 1)).build();
		Point center2 = new Point(0, 10, 0);
		Vector right2 = Vector.AXIS_X;
		Vector up2 = Vector.AXIS_Z;
		Vector direction2 = center2.add(up2).subtract(right2).subtract(Point.ZERO);
		rayCreationSpace details9 = camera2.constructRay(2, 2, 0, 0);
		assertEquals(new Ray(Point.ZERO, direction2), new Ray(details9.p0(), details9.pIJ().subtract(details9.p0())));
	}
}
