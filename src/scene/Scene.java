package scene;

import lighting.AmbientLight;
import primitives.Color;
import geometries.*;


/**
 * Represents a 3D scene containing geometries, background color, and ambient light.
 * <p>
 * This class serves as a container for the basic elements required in a rendering engine.
 */
public class Scene {
	
	/** The name of the scene (for identification or debugging purposes). */
	public String name;
	
	/** The background color of the scene (default is black). */
	public Color background = Color.BLACK;
	
	/** The ambient light used in the scene (default is none). */
	public AmbientLight ambientLight = AmbientLight.NONE;
	
	/** The collection of geometric objects in the scene. */
	public Geometries geometries = new Geometries();
	
	/**
     * Constructs a new {@code Scene} with the given name.
     *
     * @param _name the name of the scene
     */
	public Scene(String _name){
		name =_name;
	}
	
	/**
     * Sets the background color of the scene.
     *
     * @param _background the background {@link Color}
     * @return the current {@code Scene} instance (for method chaining)
     */
	public Scene setBackground(Color _background) {
		background =_background;
		return this;
	}
	
	/**
     * Sets the ambient light of the scene.
     *
     * @param _ambientLight the {@link AmbientLight} to use
     * @return the current {@code Scene} instance (for method chaining)
     */
	public Scene setAmbientLight(AmbientLight _ambientLight) {
		ambientLight = _ambientLight;
		return this;
	}
	
	/**
     * Sets the geometries of the scene.
     *
     * @param _geometries the {@link Geometries} collection to use
     * @return the current {@code Scene} instance (for method chaining)
     */
	public Scene setGeometries (Geometries _geometries)  {
		geometries = _geometries;
		return this;
	}
}
