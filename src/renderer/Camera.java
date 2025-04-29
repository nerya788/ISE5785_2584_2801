package renderer;

import primitives.*;
import geometries.*;

public class Camera implements Cloneable {
	
	// fields
	private Point p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double height = 0.0;
	private double width = 0.0;
	private double distance = 0.0;

	// nested class builder
	public static class Builder {
		private final Camera camera = new Camera();
		
		public Builder setLocation(Point p0) {
			 camera.p0 = p0;
	         return this;
		}
		
		public Builder setDirection(Vector vTo,Vector vUp) {
			if(Util.isZero(vTo.dotProduct(vUp))) {
				camera.vTo = vTo.normalize();
				camera.vUp = vUp.normalize();
				camera.vRight = vTo.crossProduct(vUp).normalize();
				camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();

			}
			else
				throw new IllegalArgumentException("Vectors vTo and vUp isn't ortogonals.");
			
			return this;
		}
        public Builder setDirection(Point target) {
            if (camera.p0 == null)
                throw new IllegalStateException("Camera location (p0) must be set before target.");

            camera.vTo = target.subtract(camera.p0).normalize();
            camera.vUp = new Vector(0, 0, 1); // ברירת מחדל כלשהי – אפשר לשנות לפי הצורך
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
			camera.vUp = camera.vTo.crossProduct(camera.vRight).normalize();
			
			return this;
        
		}
		
		
		public Builder setDirection(Point target,Vector vUp) {
			if (camera.p0 == null)
                throw new IllegalStateException("Camera location (p0) must be set before target.");
			camera.vTo = target.subtract(camera.p0).normalize();
			camera.vRight = camera.vTo.crossProduct(vUp).normalize();
			camera.vUp = camera.vRight.crossProduct(camera.vTo);

			
			return this;	
		}
		
		public Builder setViewPlaneSize(double height,double width) {
			camera.height = height;
			camera.width = width;
			return this;
		}
		
		public Builder setViewPlaneDistance(double distance) {
			camera.distance = distance;
			return this;	
		}
		
		public Builder setResolution(int nX, int nY) {
			// to implement
			return this;	
		}
		
		public Camera build() {
			// check that there is no uninitilized value.
			
			
			if (camera.p0 == null) throw new IllegalArgumentException("The point p0 is not initialized");
		    if (camera.vTo == null) throw new IllegalArgumentException("The vector vTo is not initialized");
		    if (camera.vUp == null) throw new IllegalArgumentException("The vector vUp is not initialized");
		    if (camera.vRight == null) throw new IllegalArgumentException("The vector vRight is not initialized");
		    if (camera.width == 0.0) throw new IllegalArgumentException("The width is not initialized");
		    if (camera.height == 0.0) throw new IllegalArgumentException("The height is not initialized");
		    if (camera.distance == 0.0) throw new IllegalArgumentException("The distance is not initialized");
		    
            return new Camera(camera); // מחזיר עותק חדש
		}

	}

	// constructors
	public Camera(Point p0, Vector vTo,  Vector vUp) {
		this.vTo = vTo;
		this.vUp = vUp;
		this.p0 = p0;
		
		this.vRight = vTo.crossProduct(vUp).normalize(); 
	}
	
	private Camera(Camera other) {
        this.p0 = other.p0;
        this.vTo = other.vTo;
        this.vUp = other.vUp;
        this.vRight = other.vRight;
        this.height = other.height;
        this.width = other.width;
        this.distance = other.distance;

	}
	
	private Camera() {
		// to implement
	}
	
	// methods for the builder
	public static Builder getBuilder() {
		// to implement
		return new Builder();
	}
	
    // Cloneable
    @Override
    public Camera clone() {
        return new Camera(this);
    }

    
    public Ray constructRay(int nX, int nY, int j, int i) {
    	
    	Point pc = p0.add(vTo.scale(distance));
    	
    	double rX = width / nX;
    	double rY = height / nY;
    	
    	double xJ = (j - (double)(nX-1)/2) * rX;
    	double yI = - (i - (double)(nY-1)/2) * rY;
    	
    	Point pIJ = pc;
    	if (xJ != 0)
    		pIJ = pIJ.add(vRight.scale(xJ));
    	if (yI != 0)
    		pIJ = pIJ.add(vUp.scale(yI));
    	
    	Vector vIJ = pIJ.subtract(p0);
    	
        return new Ray(p0,vIJ);
    }
}
