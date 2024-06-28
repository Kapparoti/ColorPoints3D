package org.example.JavaPoints3D;


import javafx.scene.PerspectiveCamera;

import javafx.scene.transform.Translate;
import javafx.scene.transform.Rotate;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Camera3D extends PerspectiveCamera {
    private final Translate cameraRadius = new Translate(0, 0, -40);

    public void setZoom(double value) { cameraRadius.setZ(value); }

    public double getZoom() { return cameraRadius.getZ(); }


    private final Translate cameraPivot = new Translate();

    public void setPivot(double x, double y, double z) {
        cameraPivot.setX(x);
        cameraPivot.setY(y);
        cameraPivot.setZ(z);
    }

    public Translate getPivot() { return cameraPivot; }


    public Camera3D() {
        super(true);
        farClipProperty().set(Double.MAX_VALUE);

        Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
        getTransforms().addAll (
                cameraPivot,
                yRotate,
                cameraRadius
        );

        //Animate the camera cameraRadius
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.seconds(0),
                        new KeyValue(yRotate.angleProperty(), 0)
                ),
                new KeyFrame(
                        Duration.seconds(15),
                        new KeyValue(yRotate.angleProperty(), 360)
                )
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

}
