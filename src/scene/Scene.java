package scene;

import lighting.*;
import primitives.Color;
import geometries.*;

import java.util.LinkedList;
import java.util.List;




/**
 * Represents a 3D scene containing geometries, background color, and ambient light.
 * <p>
 * This class serves as a container for the basic elements required in a rendering engine.
 */
public class Scene {
	
	/** The name of the scene (for identification or debugging purposes). */
	public String name;
	
	public List<LightSource> lights = new LinkedList<>();
	
	/** The background color of the scene (default is black). */
	public Color background = Color.BLACK;
	
	/** The ambient light used in the scene (default is none). */
	public AmbientLight ambientLight = AmbientLight.NONE;
	
	/** The collection of geometric objects in the scene. */
	public Geometries geometries = new Geometries();
	
	/**
     * Constructs a new {@code Scene} with the given name.
     *
     * @param name the name of the scene
     */
	public Scene(String name){
		this.name =name;
	}
	
	/**
     * Sets the background color of the scene.
     *
     * @param background the background {@link Color}
     * @return the current {@code Scene} instance (for method chaining)
     */
	public Scene setBackground(Color background) {
		this.background =background;
		return this;
	}
	
	/**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight the {@link AmbientLight} to use
     * @return the current {@code Scene} instance (for method chaining)
     */
	public Scene setAmbientLight(AmbientLight ambientLight) {
		this.ambientLight = ambientLight;
		return this;
	}
	
	/**
     * Sets the geometries of the scene.
     *
     * @param geometries the {@link Geometries} collection to use
     * @return the current {@code Scene} instance (for method chaining)
     */
	public Scene setGeometries (Geometries geometries)  {
		this.geometries = geometries;
		return this;
	}
	
	public Scene setlights(List<LightSource> lights) {
		this.lights = lights;
		return this;
	}
}
