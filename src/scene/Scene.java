package scene;

import lighting.AmbientLight;
import primitives.Color;
import geometries.*;



public class Scene {
	public String name;
	public Color background = Color.BLACK;
	public AmbientLight ambientLight = AmbientLight.NONE;
	public Geometries geometries = new Geometries();
	
	public Scene(String _name){
		name =_name;
	}
	
	public Scene setBackground(Color _background) {
		background =_background;
		return this;
	}
	 public Scene setAmbientLight(AmbientLight _ambientLight) {
		 ambientLight = _ambientLight;
		 return this;
	 }
	
	public Scene setGeometries (Geometries _geometries)  {
		geometries = _geometries;
		return this;
	}
}
