package org.example.JavaPoints3D;

import javafx.geometry.Point3D;
import javafx.scene.shape.Sphere;

public class CustomPoint3D extends Sphere implements Position {
    private static final int RADIUS = 20;

    @Override
    public Point3D getPosition() {
        return new Point3D(getTranslateX(), getTranslateY(), getTranslateZ());
    }
    @Override
    public void setPosition(Point3D position) {
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
    }
    @Override
    public void setPosition(double x, double y, double z) {
        setTranslateX(x);
        setTranslateY(y);
        setTranslateZ(z);
    }

    public CustomPoint3D(Point3D position) {
        super(RADIUS);
        this.setPosition(position);
    }

    public CustomPoint3D(double x, double y, double z) {
        super(RADIUS);
        this.setPosition(x, y, z);
    }



}
