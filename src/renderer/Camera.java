package renderer;

import primitives.*;

public class Camera implements Cloneable {

    // fields
    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double height = 0.0;
    private double width = 0.0;
    private double distance = 0.0;

    // Builder מקונן
    public static class Builder {
        private final Camera camera = new Camera();

        public Builder setLocation(Point p0) {
            camera.p0 = p0;
            return this;
        }

        public Builder setDirection(Vector vTo, Vector vUp) {
            if (!Util.isZero(vTo.dotProduct(vUp)))
                throw new IllegalArgumentException("Vectors vTo and vUp must be orthogonal.");

            camera.vTo = vTo.normalize();
            camera.vUp = vUp.normalize();
            camera.vRight = camera.vTo.crossProduct(camera.vUp).normalize();
            return this;
        }

        public Builder setDirection(Point target) {
            if (camera.p0 == null)
                throw new IllegalStateException("Camera location (p0) must be set before target.");

            Vector vTo = target.subtract(camera.p0).normalize();
            Vector vUp = new Vector(0, 1, 0); // ברירת מחדל כלשהי – אפשר לשנות לפי הצורך

            return setDirection(vTo, vUp);
        }

        public Builder setDirection(Point target, Vector vUp) {
            if (camera.p0 == null)
                throw new IllegalStateException("Camera location (p0) must be set before target.");

            camera.vTo = target.subtract(camera.p0).normalize();
            camera.vRight = camera.vTo.crossProduct(vUp).normalize();
            camera.vUp = camera.vRight.crossProduct(camera.vTo);
            return this;
        }

        public Builder setViewPlaneSize(double height, double width) {
            camera.height = height;
            camera.width = width;
            return this;
        }

        public Builder setViewPlaneDistance(double distance) {
            camera.distance = distance;
            return this;
        }

        public Builder setResolution(int nX, int nY) {
            // ניתן לממש בהמשך – לא בשדה כרגע
            return this;
        }

        public Camera build() {
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

    // בנאים
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
    }

    private Camera() {
        // בנאי ריק לשימוש פנימי ע"י ה־Builder
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

    // Cloneable
    @Override
    public Camera clone() {
        return new Camera(this);
    }

    // מחזיר Builder חדש
    public static Builder getBuilder() {
        return new Builder();
    }

    // דוגמת מתודה (עוד לא מומשה)
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }
}
