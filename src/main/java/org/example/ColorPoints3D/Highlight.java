package org.example.ColorPoints3D;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Highlight extends Sphere implements Spatial {
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

    public Highlight() {
        super(RADIUS);
        setMaterial( new PhongMaterial(Color.rgb(255, 255, 255, 0.1)));
    }
}
