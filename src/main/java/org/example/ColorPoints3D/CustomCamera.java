package org.example.ColorPoints3D;


import javafx.scene.PerspectiveCamera;
import javafx.geometry.Point3D;
import javafx.scene.transform.Translate;
import javafx.scene.transform.Rotate;


public class CustomCamera extends PerspectiveCamera {
    private static CustomCamera uniqueInstance;

    private final Rotate xRotate = new Rotate(0, Rotate.Y_AXIS);
    private final Rotate yRotate = new Rotate(0, Rotate.X_AXIS);

    private final Translate cameraRadius = new Translate(0, 0, -40);

    private final Translate cameraPivot = new Translate(0, 0);


    // Singleton behaviour
    private CustomCamera() {
        super(true);
        farClipProperty().set(Double.MAX_VALUE);

        getTransforms().addAll(
                cameraPivot,
                xRotate,
                yRotate,
                cameraRadius
        );
    }

    public static CustomCamera getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new CustomCamera();
        }
        return uniqueInstance;
    }


    public Translate getPivot() { return cameraPivot; }

    public void setPivot(Point3D pivot) {
        cameraPivot.setX(pivot.getX());
        cameraPivot.setY(pivot.getY());
        cameraPivot.setZ(pivot.getZ());
    }


    public double getRadius() { return cameraRadius.getZ(); }

    public void setRadius(double value) {
        if (value < -1) { cameraRadius.setZ(value); }
    }


    public void rotateX(double delta) {
        xRotate.setAngle(xRotate.getAngle() - delta / 2);
    }

    public void rotateY(double delta) {
        yRotate.setAngle(yRotate.getAngle() + delta / 2);
    }
}
