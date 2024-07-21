package org.example.ColorPoints3D;

import javafx.scene.shape.Sphere;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;


public class Highlight extends Sphere implements Spatial {
    private static final int RADIUS = 1;


    public Highlight() {
        super(RADIUS);
        setMaterial( new PhongMaterial(Color.rgb(255, 255, 255, 0.1)));
    }
}
