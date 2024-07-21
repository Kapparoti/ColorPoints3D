package org.example.ColorPoints3D;

import javafx.geometry.Point3D;
import javafx.scene.transform.Transform;

import java.util.Collection;


public interface Spatial {
    Collection<Transform> getTransforms();

    double getTranslateX();
    void setTranslateX(double x);

    double getTranslateY();
    void setTranslateY(double y);

    double getTranslateZ();
    void setTranslateZ(double z);


    default Point3D getPosition() {
        return new Point3D(getTranslateX(), getTranslateY(), getTranslateZ());
    }

    default void setPosition(Point3D position) {
        getTransforms().clear();
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
    }
    default void setPosition(double x, double y, double z) {
        setPosition(new Point3D(x, y, z));
    }

}
