package org.example.JavaPoints3D;

import javafx.geometry.Point3D;

public interface Spatial {
    int WIDTH = 960;
    int HEIGHT = 720;

    default Point3D getPosition() {
        return new Point3D(WIDTH / 2.0, HEIGHT / 2.0, 0);
    }

    default double getX() { return getPosition().getX(); }
    default double getY() { return getPosition().getY(); }
    default double getZ() { return getPosition().getZ(); }

    default void setPosition(Point3D position) {

    }

    default void setPosition(double x, double y, double z) {
        setPosition(new Point3D(x, y, z));
    }


    default double getDistance(Point3D position) {
        return getPosition().distance(position);
    }

    default double getDistance(CustomPoint3D point) {
        return getPosition().distance(point.getPosition());
    }

    default double getDistance(double x, double y, double z) {
        return getPosition().distance(x, y, z);
    }

}
