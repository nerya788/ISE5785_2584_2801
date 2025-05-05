package renderer;

import primitives.*;
import scene.Scene;

import static primitives.Util.isZero;
import java.util.MissingResourceException;

public class Camera implements Cloneable {
	
	// fields
	private Point p0;
	private Vector vTo;
	private Vector vUp;
	private Vector vRight;
	private double height = 0.0;
	private double width = 0.0;
	private double distance = 0.0;
	
	// new fields for stage 5 
	private ImageWriter imageWriter;
	private RayTracerBase rayTracer;
	private int nX = 1;
	private int nY = 1;
	
	// nested class builder
	public static class Builder {
		private final Camera camera = new Camera();
		
		public Builder setRayTracer(Scene scene, RayTracerType tracerType) {
			if(tracerType == RayTracerType.SIMPLE) {
				camera.rayTracer = new SimpleRayTracer(scene);
			}
			else {
				camera.rayTracer = null;
			}
			return this;
		}
		
		/**
         * Sets the location for the camera.
         *
         * @param p0 the location to set for the camera.
         * @return the Builder instance.
         */
		public Builder setLocation(Point p0) {
			 camera.p0 = p0;
	         return this;
		}
		
		public Builder setDirection(Vector vTo, Vector vUp) throws IllegalArgumentException {
            if (!isZero(vUp.dotProduct(vTo)))
                throw new IllegalArgumentException("the vectors vTo and vUp are not perpendicular");
            camera.vUp = vUp.normalize();
            camera.vTo = vTo.normalize();
			camera.vRight = vTo.crossProduct(vUp).normalize();
			camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();

            return this;
        }
		
		
        public Builder setDirection(Point target) {
            if (camera.p0 == null)
                throw new IllegalStateException("Camera location (p0) must be set before target.");

            // חישוב הוקטור vTo
            camera.vTo = target.subtract(camera.p0).normalize();

            // הגדרת vUp לפי ערך קבוע (שימוש ב-AXIS_Z) - אם אנחנו צריכים וקטור ראשוני
            camera.vUp = new Vector(0, 0, 1); // ברירת מחדל כלשהי – אפשר לשנות לפי הצורך

            // חישוב vRight
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();

            // חישוב vUp בצורה נכונה אחרי חישוב vRight
            camera.vUp = camera.vTo.crossProduct(camera.vRight).normalize();  // חישוב מדויק של vUp

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
		
		public Builder setResolution(int nX, int nY) throws Exception {
			if(nX < 0 || nY < 0) {
				throw new Exception("the resolution couldnt be negative");
			}
			camera.nX = nX;
			camera.nY = nY;
			camera.imageWriter = new ImageWriter(nX,nY);
			
			if(camera.rayTracer == null) {
				camera.rayTracer = new SimpleRayTracer(null);
			}
			
			return this;	
		}
		
		/**
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
		**/
		
		/**
         * Builds and returns the Camera instance.
         *
         * @return the built Camera instance.
         */
        public Camera build() {
            String h = "height", w = "width", d = "distance";

            if (isZero(camera.height))
                throw new MissingResourceException("Missing data to render", "Camera", h);
            if (isZero(camera.width))
                throw new MissingResourceException("Missing data to render", "Camera", w);
            if (isZero(camera.distance))
                throw new MissingResourceException("Missing data to render", "Camera", d);
            if (camera.p0 == null)
                throw new MissingResourceException("Missing data to render", "Camera", "location");
            if (camera.vUp == null)
                throw new MissingResourceException("Missing data to render", "Camera", "vUp");
            if (camera.vTo == null)
                throw new MissingResourceException("Missing data to render", "Camera", "vTo");

            if (camera.height < 0)
                throw new IllegalArgumentException("The " + h + " value is invalid");
            if (camera.width < 0)
                throw new IllegalArgumentException("The " + w + " value is invalid");
            if (camera.distance < 0)
                throw new IllegalArgumentException("The " + d + " value is invalid");

            //if (camera.rayTracer == null)
                //throw new MissingResourceException("Missing data to render", "Camera", "rayTracer");
            //if (camera.imageWriter == null)
                //throw new MissingResourceException("Missing data to render", "Camera", "imageWriter");
            
            try {
                return (Camera) camera.clone(); //fix
            } catch (CloneNotSupportedException ex) {
                throw new MissingResourceException(ex.getMessage(), "", "");
            }
        }
        
        public Builder setDegreeClockwise(double angleDegrees) {
			
			setDegreeCounterclockwise(-1 * angleDegrees);
			return this;
		}
		
		public Builder setDegreeCounterclockwise(double angleDegrees) {
			double radianRadians = Math.toRadians(angleDegrees);

			double cos = Math.cos(radianRadians);
			double sin = Math.sin(radianRadians);

			// Rodriguez formula:
			camera.vRight = camera.vRight.scale(cos).add(camera.vTo.crossProduct(camera.vRight).scale(sin))
					.add(camera.vTo.scale(camera.vTo.dotProduct(camera.vRight) * (1 - cos))).normalize();

			camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();

			return this;
		}
		
		public Builder setPosition(Point p0, Point target) {
			//todo 
			return this;
		}
    }
	
	 /**
     * Private constructor to prevent direct instantiation.
     * Use the Builder to create an instance.
     */
	private Camera() {
	}
	
	/**
     * Returns a new Builder instance to build a Camera.
     *
     * @return a new Builder instance.
     */
	public static Builder getBuilder() {
		return new Builder();
	}

	/**
     * Constructs a ray through a given pixel.
     *
     * @param nX the number of horizontal pixels.
     * @param nY the number of vertical pixels.
     * @param j  the pixel column.
     * @param i  the pixel row.
     * @return the constructed ray.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
    	Point pc = p0.add(vTo.scale(distance));
    	
    	double rX = width / nX;
    	double rY = height / nY;
    	
    	double xJ = (j - (double)(nX-1)/2) * rX;
    	double yI = - (i - (double)(nY-1)/2) * rY;
    	
    	Point pIJ = pc;
    	
    	if (!isZero(xJ))
    		pIJ = pIJ.add(vRight.scale(xJ));
    	if (!isZero(yI))
    		pIJ = pIJ.add(vUp.scale(yI));
    	
    	Vector vIJ = pIJ.subtract(p0);
  
        return new Ray(p0,vIJ);
    }
    
    /**
     * 
     * @return
     */
    public Camera renderImage() {
    	   ImageWriter images = new ImageWriter(nX,nY);
		   for (int i = 0; i < nX; i++) {
			   for (int j = 0; j < nY; j++) {
				   castRay(i, j);
			   }
		   }
				   
    	// throw new UnsupportedOperationException("klum bentaim");
		return this;
    }
    
    /**
     * 
     * @param interval
     * @param color
     * @return
     */
    public Camera printGrid(int interval, Color color) {
    	 ImageWriter images = new ImageWriter(nX,nY);
		   for (int i = 0; i < nX; i++) {
			   for (int j = 0; j < nY; j++) {
				   castRay(i, j);
		   
			    	if (i % interval == 0)
						   images.writePixel(i, j, new Color(225,0,0));
					else if (j % interval == 0)
						   images.writePixel(i, j, new Color(225,0,0));
					//   else
					//	   images.writePixel(i, j, new Color(225,225,0));
			   }
		   }
		   
			for (int i = 0; i < nX; i++) 
				   images.writePixel(i, nY - 1, new Color(225,0,0));
			for (int j = 0; j < nY; j++)
				   images.writePixel(nX - 1, j, new Color(225,0,0));
			
    	return this;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public Camera WriteToImage(String name) {
    	imageWriter.writeToImage(name);
    	return this;
    }
    
    private void castRay(int i, int j) {
    	Ray ray = constructRay(nX, nY, i, j);
    	Color color = rayTracer.traceRay(ray);
    	imageWriter.writePixel(i, j, color);
    }
}
