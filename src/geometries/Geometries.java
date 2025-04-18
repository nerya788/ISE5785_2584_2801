package geometries;

import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

public class Geometries implements Intersectable {
    private final List<Intersectable> allIntersectable = new LinkedList<>();

    public Geometries() {}

    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    public void add(Intersectable... geometries) {
        for (Intersectable geo : geometries) {
            allIntersectable.add(geo);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> intersections = null;

        for (Intersectable geo : allIntersectable) {
            List<Point> points = geo.findIntersections(ray);
            if (points != null) {
                if (intersections == null)
                    intersections = new LinkedList<>();
                intersections.addAll(points);
            }
        }

        return intersections;
    }
}

