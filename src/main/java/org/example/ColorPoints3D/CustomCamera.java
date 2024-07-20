package org.example.ColorPoints3D;


import javafx.scene.PerspectiveCamera;
import javafx.geometry.Point3D;
import javafx.scene.transform.Translate;
import javafx.scene.transform.Rotate;

public class CustomCamera extends PerspectiveCamera {
    private final Rotate xRotate = new Rotate(0, Rotate.Y_AXIS);
    private final Rotate yRotate = new Rotate(0, Rotate.X_AXIS);

    private final Translate cameraRadius = new Translate(0, 0, -40);

    private final Translate cameraPivot = new Translate(0, 0);


    public Translate getPivot() { return cameraPivot; }

    public void setPivot(Point3D pivot) {
        cameraPivot.setX(pivot.getX());
        cameraPivot.setY(pivot.getY());
        cameraPivot.setZ(pivot.getZ());
    }


    public double getZoom() { return cameraRadius.getZ(); }

    public void setZoom(double value) {
        if (value < -1) { cameraRadius.setZ(value); }
    }


    public CustomCamera() {
        super(true);
        farClipProperty().set(Double.MAX_VALUE);

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
        yRotate.setAngle(yRotate.getAngle() + delta / 2);
    }
}
