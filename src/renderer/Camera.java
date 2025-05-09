package renderer;

import primitives.*;
import scene.Scene;

import static primitives.Util.isZero;
import java.util.MissingResourceException;

/**
 * The {@code Camera} class represents a virtual camera in 3D space.
 * It defines the camera's position and orientation and provides the functionality
 * for generating rays through pixels and rendering images using ray tracing.
 */
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
	
					/**
					 * Builder class for constructing a {@link Camera} instance using
					 * the Builder design pattern.
					 */
					public static class Builder {
						private final Camera camera = new Camera();
						
						/**
						 * Sets the ray tracer engine based on the selected type.
						 *
						 * @param scene the scene to trace rays in
						 * @param tracerType the type of ray tracer to use (e.g., SIMPLE)
						 * @return the Builder instance
						 */
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
						
						/**
						 * Sets the camera orientation based on target point and up direction.
						 *
						 * @param vTo the direction toward which the camera points
						 * @param vUp the upward direction
						 * @return the Builder instance
						 * @throws IllegalArgumentException if vTo and vUp are not orthogonal
						 */
						public Builder setDirection(Vector vTo, Vector vUp) throws IllegalArgumentException {
				            if (!isZero(vUp.dotProduct(vTo)))
				                throw new IllegalArgumentException("the vectors vTo and vUp are not perpendicular");
				            camera.vUp = vUp.normalize();
				            camera.vTo = vTo.normalize();
							camera.vRight = vTo.crossProduct(vUp).normalize();
							camera.vUp = camera.vRight.crossProduct(camera.vTo).normalize();
				
				            return this;
				        }
						
				
						/**
						 * Sets the camera's orientation automatically based on a target point.
						 * Assumes a default upward direction (Z axis).
						 *
						 * @param target a point to look at
						 * @return the Builder instance
						 */
				        public Builder setDirection(Point target) {
				            if (camera.p0 == null)
				                throw new IllegalStateException("Camera location (p0) must be set before target.");
				
				            camera.vTo = target.subtract(camera.p0).normalize();
				            camera.vUp = new Vector(0, 0, 1); // default
				
				            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
				            camera.vUp = camera.vTo.crossProduct(camera.vRight).normalize();  // חישוב מדויק של vUp
				
				            return this;
				        }
				
						
				        /**
				         * Sets the camera's direction based on a target point and an up vector.
				         *
				         * @param target a point to look at
				         * @param vUp the upward direction
				         * @return the Builder instance
				         */
						public Builder setDirection(Point target,Vector vUp) {
							if (camera.p0 == null)
				                throw new IllegalStateException("Camera location (p0) must be set before target.");
							camera.vTo = target.subtract(camera.p0).normalize();
							camera.vRight = camera.vTo.crossProduct(vUp).normalize();
							camera.vUp = camera.vRight.crossProduct(camera.vTo);
							
							return this;	
						}
						
						/**
						 * Sets the size of the view plane.
						 *
						 * @param height the height of the view plane
						 * @param width the width of the view plane
						 * @return the Builder instance
						 */
						public Builder setViewPlaneSize(double height,double width) {
							camera.height = height;
							camera.width = width;
							return this;
						}
						
						/**
						 * Sets the distance from the camera to the view plane.
						 *
						 * @param distance the distance to the view plane
						 * @return the Builder instance
						 */
						public Builder setViewPlaneDistance(double distance) {
							camera.distance = distance;
							return this;	
						}
						
						/**
						 * Sets the resolution of the image and initializes the {@link ImageWriter}.
						 *
						 * @param nX number of horizontal pixels
						 * @param nY number of vertical pixels
						 * @return the Builder instance
						 * @throws Exception if resolution values are negative
						 */
						public Builder setResolution(int nX, int nY) throws Exception {
							camera.nX = nX;
							camera.nY = nY;
							camera.imageWriter = new ImageWriter(nX,nY);
							
							if(camera.rayTracer == null) {
								camera.rayTracer = new SimpleRayTracer(null);
							}
							
							return this;	
						}
				        
				        /**
				         * Rotates the camera clockwise around its viewing axis by the given angle.
				         *
				         * @param angleDegrees the angle in degrees
				         * @return the Builder instance
				         */
				        public Builder setDegreeClockwise(double angleDegrees) {
							
							setDegreeCounterclockwise(-1 * angleDegrees);
							return this;
						}
						
				        /**
				         * Rotates the camera counterclockwise around its viewing axis by the given angle.
				         *
				         * @param angleDegrees the angle in degrees
				         * @return the Builder instance
				         */
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
						
						/**
						 * Sets the camera position and target point.
						 *
						 * @param p0 camera location, all other fields added in step 4 must be filled in
						 * @return the Builder instance
						 */
						public Builder setPosition(Point p0) {
							Point target = camera.p0.add(camera.vTo.scale(camera.distance));

						    Vector vToNew = target.subtract(p0).normalize();

						    if (vToNew.equals(camera.vTo)) {
						        camera.p0 = p0;
						        return this;
						    }

						    Vector axis = camera.vTo.crossProduct(vToNew).normalize();
						    double angle = Math.acos(camera.vTo.dotProduct(vToNew));

						    camera.distance = target.distance(p0);
						    camera.vUp = rotateAroundAxis(camera.vUp, axis, angle).normalize();
						    camera.vRight = rotateAroundAxis(camera.vRight, axis, angle).normalize();
						    camera.vTo = vToNew;
						    camera.p0 = p0;

						    return this;
						}
						
						/**
						 * 
						 * @param setVector - the vector whose direction you want to change
						 * @param axis - the reference vector about which you want to rotate the vector
						 * @param angle - the angle by which you want to change the direction of the vector around the axis
						 * @return
						 */
						private Vector rotateAroundAxis(Vector setVector, Vector axis, double angle) {
						    Vector v1 = setVector.scale(Math.cos(angle));
						    Vector v2 = axis.crossProduct(setVector).scale(Math.sin(angle));
						    Vector v3 = axis.scale(axis.dotProduct(setVector) * (1 - Math.cos(angle)));
						    return v1.add(v2).add(v3);
						}

						/**
						 * Finalizes the construction of the camera, verifying all required fields are initialized.
						 *
						 * @return a new {@link Camera} instance
						 * @throws MissingResourceException if required fields are missing
						 * @throws IllegalArgumentException if invalid values are provided
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
							if(camera.nX < 0 || camera.nY < 0)
								throw new IllegalArgumentException("the resolution couldnt be negative");

				            if (camera.rayTracer == null)
				                throw new MissingResourceException("Missing data to render", "Camera", "rayTracer");
				            if (camera.imageWriter == null)
				                throw new MissingResourceException("Missing data to render", "Camera", "imageWriter");
				            
				            try {
				                return (Camera) camera.clone(); //fix
				            } catch (CloneNotSupportedException ex) {
				                throw new MissingResourceException(ex.getMessage(), "", "");
				            }
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
     * Renders an image by casting rays through each pixel and writing the color to the image buffer.
     *
     * @return the Camera instance (for chaining)
     */
    public Camera renderImage() {
		   for (int i = 0; i < nX; i++)
			   for (int j = 0; j < nY; j++)
				   castRay(i, j);
		
		   return this;
    }
    
    /**
     * Casts a ray through the given pixel and writes the computed color to the image.
     *
     * @param i the x-coordinate of the pixel
     * @param j the y-coordinate of the pixel
     */
    private void castRay(int i, int j) {
    	Ray ray = constructRay(nX, nY, i, j);
    	Color color = rayTracer.traceRay(ray);
    	imageWriter.writePixel(i, j, color);
    }

    /**
     * Draws a grid on the rendered image by coloring every pixel at the given interval.
     * The grid is drawn using the specified color on both horizontal and vertical lines,
     * and also includes the bottom and right borders of the image.
     *
     * @param interval the spacing between grid lines, in pixels
     * @param color the color used to draw the grid lines
     * @return the current Camera object (for method chaining)
     */
    public Camera printGrid(int interval, Color color) {
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(i, j, color);
                }
            }
        }

        // full the grid
        for (int i = 0; i < nX; i++)
            imageWriter.writePixel(i, nY - 1, color);
        for (int j = 0; j < nY; j++)
            imageWriter.writePixel(nX - 1, j, color);

        return this;
    }
    
    /**
     * Writes the rendered image to a file.
     *
     * @param name the name of the image file (with or without path and/or extension)
     * @return the Camera instance (for chaining)
     */
    public Camera WriteToImage(String name) {
    	imageWriter.writeToImage(name);
    	return this;
    }
}
