package org.example.JavaPoints3D;

import javafx.geometry.Point3D;
import javafx.scene.Camera;

public interface Position {

    default Point3D getPosition() {
        return Point3D.ZERO;
    }


    default void setPosition(Point3D position) { }

    default void setPosition(double x, double y, double z) {
        setPosition(new Point3D(x, y, z));
    }


    default double getDistance(Point3D position) {
        return getPosition().distance(position);
    }

    default double getDistance(CustomPoint3D point) {
        return getPosition().distance(point.getPosition());
    }

    default double getDistance(Camera3D camera) {
        return getPosition().distance(camera.getPosition());
    }

}
