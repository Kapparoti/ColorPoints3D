package org.example.JavaPoints3D;

import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;

public class Camera3D extends PerspectiveCamera implements Position {
    private static final double CAMERA_MOVE_SPEED = 5;

    @Override
    public Point3D getPosition() {
        return new Point3D(getTranslateX(), getTranslateY(), getTranslateZ());
    }
    @Override
    public void setPosition(Point3D position) {
        setTranslateX(position.getX());
        setTranslateY(position.getY());
        setTranslateZ(position.getZ());
    }

    public void zoom (double factor) { ///////////////////////////////////|||||||||//// DA FARE CON RAGGIO
        setTranslateZ(getTranslateZ() + factor);
    }

    public void moveUp() { ////////////////////////////////////////////////////////|/// DA ROTAZIONE SULLA SFERA
        //setRotationAxis(getPosition() + );
        //setRotate(CAMERA_MOVE_SPEED);
        setTranslateY(getTranslateY() - CAMERA_MOVE_SPEED);
    }

    public void moveLeft() { /////////////////////////////////////////////////////////// DA ROTAZIONE SULLA SFERA
        setTranslateX(getTranslateX() - CAMERA_MOVE_SPEED);
    }

    public void moveDown() { /////////////////////////////////////////////////////////// DA ROTAZIONE SULLA SFERA
        setTranslateY(getTranslateY() + CAMERA_MOVE_SPEED);
    }

    public void moveRight() { /////////////////////////////////////////////////////////// DA ROTAZIONE SULLA SFERA
        setTranslateX(getTranslateX() + CAMERA_MOVE_SPEED);
    }

}
