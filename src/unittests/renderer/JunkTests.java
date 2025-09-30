package unittests.renderer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.*;
import primitives.*;
import renderer.Camera;
import renderer.RayTracerType;
import renderer.Camera.Builder;
import scene.Scene;

class JunkTests {
	/** Default constructor to satisfy JavaDoc generator */
	JunkTests() {
		/* to satisfy JavaDoc generator */ }


	@Test
	void JunkTest1() {
		
		
		
		
		/** Scene for the tests */
		Scene scene = new Scene("JunkTest scene")
				.setAmbientLight(new AmbientLight(new Color(20,20,30)))
				.setBackground(new Color(5,5,10));
		
		
		/** Camera builder for the tests with triangles */
		Camera.Builder CameraB = Camera.getBuilder() //
				.setLocation(new Point(0,50.11,400))
				.setDirection(new Point(0.121,50,0.11), new Vector(0.01,0.001,1.0001))
				.setViewPlaneDistance(400)
				.setViewPlaneSize(300, 300)
				.setRayTracer(scene, RayTracerType.SIMPLE);
		
		
		Material floorMaterial = new Material().setKD(0.4).setKS(0.8).setShininess(200).setKR(0.4);
		Material wallMaterial = new Material().setKD(0.6).setKS(0.2).setShininess(10);
		Material glass = new Material().setKD(0.05).setKS(0.9).setShininess(500).setKT(0.9).setKR(0.1);
		Material mirror = new Material().setKD(0.05).setKS(0.1).setShininess(100).setKR(0.9);
		Material matteRed = new Material().setKD(0.8).setKS(0.3).setShininess(50);
		Material metallicGold = new Material().setKD(0.4).setKS(0.8).setShininess(300).setKR(0.6);
		Material glossyGreen = new Material().setKD(0.5).setKS(0.7).setShininess(150);

		
		
		
		
		
		scene.geometries.add(
				new Plane(new Point(0, 50, -10), new Vector(0, 0, 1))
					.setEmission(new Color(20, 20, 25)).setMaterial(floorMaterial),
				new Sphere(new Point(0,50,0), 30)
					.setEmission(new Color(0, 50, 100)).setMaterial(glass) );
		
		
		
		
		scene.lights.add(new SpotLight(new Color(700, 500, 400), new Point(0,-35,0), new Vector(0,1,0))
				.setKl(0.00001).setKq(0.000005));
		
		
		Camera junkCamera = CameraB.setResolution(2048, 2048).build();

		junkCamera.renderImage()
		              .writeToImage("Junktest1");
		
		
	}
}