package org.example.JavaPoints3D;

import javafx.geometry.Point3D;
import javafx.scene.shape.Sphere;

public class CustomPoint3D extends Sphere implements Spatial {
    private static final int RADIUS = 1;

    @Override
    public Point3D getPosition() {
        return new Point3D(getTranslateX(), getTranslateY(), getTranslateZ());
    }
    @Override
    public void setPosition(Point3D position) {
        getTransforms().clear();
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
    }

    public CustomPoint3D(Point3D point) {
        super(RADIUS);
        this.setPosition(point);
    }
    public CustomPoint3D(double x, double y, double z) {
        super(RADIUS);
        this.setPosition(x, y, z);
    }


}
