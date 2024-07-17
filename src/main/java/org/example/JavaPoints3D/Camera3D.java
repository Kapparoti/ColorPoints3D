package org.example.JavaPoints3D;


import javafx.scene.PerspectiveCamera;

import javafx.scene.transform.Translate;
import javafx.scene.transform.Rotate;

public class Camera3D extends PerspectiveCamera {
    private final Rotate xRotate;
    private final Rotate yRotate;

    private final Translate cameraRadius = new Translate(0, 0, -40);

    private final Translate cameraPivot = new Translate();


    public void setPivot(double x, double y, double z) {
        cameraPivot.setX(x);
        cameraPivot.setY(y);
        cameraPivot.setZ(z);
    }

    public Translate getPivot() { return cameraPivot; }


    public void setZoom(double value) { cameraRadius.setZ(value); }

    public double getZoom() { return cameraRadius.getZ(); }


    public Camera3D() {
        super(true);
        farClipProperty().set(Double.MAX_VALUE);
        nearClipProperty().set(0);

        xRotate = new Rotate(0, Rotate.Y_AXIS);
        yRotate = new Rotate(0, Rotate.X_AXIS);

        getTransforms().addAll(
                cameraPivot,
                xRotate,
                yRotate,
                cameraRadius
        );
    }

    public void rotateX(double delta) {
        xRotate.setAngle(xRotate.getAngle() - delta / 2);
    }

    public void rotateY(double delta) {
        yRotate.setAngle(yRotate.getAngle() - delta / 2);
    }
}
