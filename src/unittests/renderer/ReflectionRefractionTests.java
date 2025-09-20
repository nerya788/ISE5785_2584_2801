package unittests.renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.Camera;
import renderer.RayTracerType;
import renderer.Camera.Builder;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author Dan Zilberstein
 */
class ReflectionRefractionTests {
	/** Default constructor to satisfy JavaDoc generator */
	ReflectionRefractionTests() {
		/* to satisfy JavaDoc generator */ }

	/** Scene for the tests */
	private final Scene scene = new Scene("Test scene");
	/** Camera builder for the tests with triangles */
	private final Camera.Builder cameraBuilder = Camera.getBuilder() //
			.setRayTracer(scene, RayTracerType.SIMPLE);

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	void twoSpheres() {
		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.3)), //
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100))); //
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		cameraBuilder.setLocation(new Point(0, 0, 1000)) //
				.setDirection(Point.ZERO, Vector.AXIS_Y) //
				.setViewPlaneDistance(1000).setViewPlaneSize(150, 150) //
				.setResolution(500, 500) //
				.build() //
				.renderImage() //
				.writeToImage("refractionTwoSpheres");
	}

	/** Produce a picture of a sphere lighted by a spot light */
	@Test
	void twoSpheresOnMirrors() {
		scene.geometries.add( //
				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100)) //
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20) //
								.setKT(new Double3(0.5, 0, 0))), //
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20)) //
						.setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)), //
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), //
						new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKR(1)), //
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), //
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
		scene.setAmbientLight(new AmbientLight(new Color(26, 26, 26)));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		cameraBuilder.setLocation(new Point(0, 0, 10000)) //
				.setDirection(Point.ZERO, Vector.AXIS_Y) //
				.setViewPlaneDistance(10000).setViewPlaneSize(2500, 2500) //
				.setResolution(500, 500) //
				.build() //
				.renderImage() //
				.writeToImage("reflectionTwoSpheresMirrored");
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	void trianglesTransparentSphere() {
		scene.geometries.add(
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
		scene.setAmbientLight(new AmbientLight(new Color(38, 38, 38)));
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)).setKl(4E-5)
				.setKq(2E-7));

		cameraBuilder.setLocation(new Point(0, 0, 1000)) //
				.setDirection(Point.ZERO, Vector.AXIS_Y) //
				.setViewPlaneDistance(1000).setViewPlaneSize(200, 200) //
				.setResolution(600, 600) //
				.build() //
				.renderImage() //
				.writeToImage("refractionShadow");
	}

	/**
	 * Test for stage7 bonus1 scene, "Bonus1 Scene". This method constructs a
	 * complex scene designed to fulfill all bonus requirements by showcasing a
	 * variety of geometries, materials, and lighting effects. The scene includes
	 * over 10 objects of all implemented types (Plane, Sphere, Triangle, Polygon,
	 * Cylinder, Tube) and demonstrates advanced rendering features such as
	 * reflection, refraction, and complex shadows from multiple light sources.
	 */
	@Test
	public void stage7Bonus1() {
		// Use a local Scene and Camera.Builder to ensure test isolation and prevent
		// conflicts.
		Scene myScene = new Scene("Bonus1 Scene").setAmbientLight(new AmbientLight(new Color(20, 20, 30))) // Set a
																											// minimal
																											// cool
																											// ambient
																											// light to
																											// maintain
																											// deep
																											// shadows
				.setBackground(new Color(5, 5, 10)); // Set a near-black background

		Camera.Builder myBonusCamera = Camera.getBuilder().setLocation(new Point(-300, -100, 400)) // Position the
																									// camera for a
																									// wide, cinematic
																									// three-quarter
																									// view
				.setDirection(new Point(0, -80, 0), new Vector(0, 1, 0)) // Aim towards the center of the composition
				.setViewPlaneDistance(450).setViewPlaneSize(300, 300).setRayTracer(myScene, RayTracerType.GRID);

		// --- Material Definitions ---
		Material floorMaterial = new Material().setKD(0.4).setKS(0.8).setShininess(200).setKR(0.4);
		Material wallMaterial = new Material().setKD(0.6).setKS(0.2).setShininess(10);
		Material glass = new Material().setKD(0.05).setKS(0.9).setShininess(500).setKT(0.9).setKR(0.1);
		Material mirror = new Material().setKD(0.05).setKS(0.1).setShininess(100).setKR(0.9);
		Material matteRed = new Material().setKD(0.8).setKS(0.3).setShininess(50);
		Material metallicGold = new Material().setKD(0.4).setKS(0.8).setShininess(300).setKR(0.6);
		Material glossyGreen = new Material().setKD(0.5).setKS(0.7).setShininess(150);

		// --- Scene Geometry: Stage (2 Planes) ---
		myScene.geometries.add(new Plane(new Point(0, -200, 0), new Vector(0, 1, 0)) // Reflective floor
				.setEmission(new Color(20, 20, 25)).setMaterial(floorMaterial),
				new Plane(new Point(0, 0, -400), new Vector(0, 0, 1)) // Matte back wall
						.setEmission(new Color(15, 15, 20)).setMaterial(wallMaterial));

		// --- Sculpture 1: A large glass pyramid composed of 4 Triangles to demonstrate
		// refraction. ---
		Point pyrBase1 = new Point(100, -200, -250);
		Point pyrBase2 = new Point(250, -200, -250);
		Point pyrBase3 = new Point(250, -200, -100);
		Point pyrBase4 = new Point(100, -200, -100);
		Point pyrTop = new Point(175, 50, -175);
		myScene.geometries.add(
				new Triangle(pyrBase1, pyrBase2, pyrTop).setMaterial(wallMaterial).setEmission(Color.BLUE),
				new Triangle(pyrBase2, pyrBase3, pyrTop).setMaterial(wallMaterial).setEmission(Color.BLUE),
				new Triangle(pyrBase3, pyrBase4, pyrTop).setMaterial(wallMaterial).setEmission(Color.BLUE),
				new Triangle(pyrBase4, pyrBase1, pyrTop).setMaterial(wallMaterial).setEmission(Color.BLUE));

		// --- Sculpture 2: A central pedestal (Box) with a mirror Sphere. ---
		addBoxToScene(myScene, new Point(0, -150, 0), 120, 100, 120, wallMaterial, new Color(40, 40, 45));
		myScene.geometries
				.add(new Sphere(new Point(0, -70, 0), 40).setMaterial(mirror).setEmission(new Color(200, 200, 200)));

		// --- Sculpture 3: A metallic Cylinder on a pentagonal base (Polygon). ---
		Point p1 = new Point(-180, -199, 50); // Using y=-199 to avoid z-fighting with the floor
		Point p2 = new Point(-120, -199, 120);
		Point p3 = new Point(-150, -199, 200);
		Point p4 = new Point(-240, -199, 200);
		Point p5 = new Point(-270, -199, 120);
		myScene.geometries
				.add(new Polygon(p1, p2, p3, p4, p5).setMaterial(metallicGold).setEmission(new Color(180, 150, 90)));
		myScene.geometries.add(new Cylinder(new Ray(new Point(-200, -200, 140), new Vector(0, 1, 0)), 30, 150)
				.setEmission(new Color(255, 215, 0)).setMaterial(metallicGold));

		// --- Floating Elements & Accents (Totaling >10 objects) ---
		// 1. A magenta neon Tube acting as an emissive light source.
		myScene.geometries.add(new Tube(new Ray(new Point(-400, 100, -100), new Vector(1, -0.2, 0.5)), 5)
				.setEmission(new Color(255, 0, 150)).setMaterial(new Material())); // Material with no shading
																					// properties, only emission.

		// 2. A small glass Sphere to demonstrate further refraction.
		myScene.geometries.add(new Sphere(new Point(-50, -150, 180), 30).setMaterial(glass));

		// 3. A small red Sphere with a matte finish.
		myScene.geometries
				.add(new Sphere(new Point(200, -170, 50), 30).setMaterial(matteRed).setEmission(new Color(RED)));

		// 4. A small green Sphere with a glossy finish.
		myScene.geometries
				.add(new Sphere(new Point(150, -100, -50), 35).setMaterial(glossyGreen).setEmission(new Color(GREEN)));

		// --- Lighting Configuration (3-Point Lighting) ---
		// Key Light: A sharp spotlight to cast dramatic, hard-edged shadows and create
		// strong highlights.
		myScene.lights
				.add(new SpotLight(new Color(700, 500, 400), new Point(-300, 100, 200), new Vector(1, -0.5, -0.75))
						.setKl(0.00001).setKq(0.000005));

		// Fill Light: A distant point light to introduce a cool color tint to the
		// shadowed areas.
		myScene.lights
				.add(new PointLight(new Color(100, 150, 255), new Point(300, 50, 300)).setKl(0.0001).setKq(0.00001));

		// Rim Light: A directional light to create subtle highlights on object edges,
		// separating them from the background.
		myScene.lights.add(new DirectionalLight(new Color(50, 40, 30), new Vector(1, -0.2, 1)));

		// --- Render Execution ---
		// First, build the original camera and render the original image

		Camera originalCamera = myBonusCamera.setResolution(500, 500).build();

		originalCamera.renderImage().writeToImage("stage7Bonus1_Original");

		/*
		 * // --- New View 1: Orbit 35 degrees to the left --- // We orbit around the
		 * original "up" vector to move horizontally. Camera cameraRightOrbit =
		 * Camera.getBuilder(originalCamera) .orbit(new
		 * Vector(-0.02,1,0.03).normalize(), -35) .build();
		 * 
		 * cameraRightOrbit.renderImage() .writeToImage("stage7Bonus1_LeftOrbit");
		 * 
		 * 
		 * // --- New View 2: Orbit 38 degrees up --- // We orbit around the original
		 * "right" vector to move vertically. Camera cameraTopOrbit =
		 * Camera.getBuilder(originalCamera) .orbit(new Vector(0.8,0,0.6).normalize(),
		 * -38) .build();
		 * 
		 * cameraTopOrbit.renderImage() .writeToImage("stage7Bonus1_TopOrbit");
		 * 
		 * 
		 * // --- New View 3: A rotated "Dutch Angle" shot (Roll) --- // The roll
		 * function remains the same, as it's a different type of rotation. Camera
		 * cameraRolledView = Camera.getBuilder(originalCamera) .setDegreeClockwise(30)
		 * .build();
		 * 
		 * cameraRolledView.renderImage() .writeToImage("stage7Bonus1_RolledView");
		 * 
		 */

	}

	/**
	 * A utility method to construct a box (cuboid) from 6 Polygons and add it to a
	 * given Scene.
	 * 
	 * @param scene    The scene to add the box's geometries to.
	 * @param center   The center point of the box.
	 * @param width    The width of the box along the X-axis.
	 * @param height   The height of the box along the Y-axis.
	 * @param depth    The depth of the box along the Z-axis.
	 * @param material The material to apply to all faces of the box.
	 * @param emission The emission color of the box.
	 */
	private void addBoxToScene(Scene scene, Point center, double width, double height, double depth, Material material,
			Color emission) {
		double w = width / 2;
		double h = height / 2;
		double d = depth / 2;

		Point baseCenter = center.add(new Vector(0, -h, 0));

		Point p0 = baseCenter.add(new Vector(-w, 0, -d));
		Point p1 = baseCenter.add(new Vector(w, 0, -d));
		Point p2 = baseCenter.add(new Vector(w, 0, d));
		Point p3 = baseCenter.add(new Vector(-w, 0, d));

		Vector heightVec = new Vector(0, height, 0);
		Point p4 = p0.add(heightVec);
		Point p5 = p1.add(heightVec);
		Point p6 = p2.add(heightVec);
		Point p7 = p3.add(heightVec);

		Polygon front = new Polygon(p3, p2, p6, p7);
		Polygon back = new Polygon(p0, p1, p5, p4);
		Polygon top = new Polygon(p4, p5, p6, p7);
		Polygon bottom = new Polygon(p0, p1, p2, p3);
		Polygon left = new Polygon(p0, p3, p7, p4);
		Polygon right = new Polygon(p1, p2, p6, p5);

		front.setMaterial(material).setEmission(emission);
		back.setMaterial(material).setEmission(emission);
		top.setMaterial(material).setEmission(emission);
		bottom.setMaterial(material).setEmission(emission);
		left.setMaterial(material).setEmission(emission);
		right.setMaterial(material).setEmission(emission);

		scene.geometries.add(front, back, top, bottom, left, right);
	}
}
