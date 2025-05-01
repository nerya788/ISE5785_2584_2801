package lighting;
import primitives.*;

public class AmbientLight {
	private final Color intensity;
	
	AmbientLight(Color _intensity){
		intensity =  _intensity;
	}

	public static AmbientLight NONE = new AmbientLight(Color.BLACK);
	public Color getIntensity() {
		return intensity;
	}
	
	
}
