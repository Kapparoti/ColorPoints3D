package org.example.JavaPoints3D;

import javafx.geometry.Point3D;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.scene.paint.Color;


public class CustomPoint3D extends Sphere implements Spatial {
    private static final int RADIUS = 1;

    private Color color;


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


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
        setMaterial( new PhongMaterial(color));
    }


    public CustomPoint3D(Point3D point, Color color) {
        super(RADIUS);
        setPosition(point);
        setColor(color);
    }
    public CustomPoint3D(double x, double y, double z, Color color) {
        super(RADIUS);
        setPosition(x, y, z);

        if (color == null) {
            color = Color.rgb(100, 100, 100);
        }
        setColor(color);
    }
}
