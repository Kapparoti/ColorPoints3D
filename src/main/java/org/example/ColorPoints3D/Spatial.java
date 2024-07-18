package org.example.ColorPoints3D;

import javafx.geometry.Point3D;

public interface Spatial {
    default Point3D getPosition() {
        return new Point3D(0, 0, 0);
    }

    default void setPosition(Point3D position) {

    }

    default void setPosition(double x, double y, double z) {
        setPosition(new Point3D(x, y, z));
    }
}
