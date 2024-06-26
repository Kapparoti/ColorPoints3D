package org.example.JavaPoints3D;

import javafx.scene.PerspectiveCamera;

public class Camera3D extends PerspectiveCamera {
    private static final double CAMERA_MOVE_SPEED = 10;


    public void zoom(double factor) {
        setTranslateZ(getTranslateZ() + factor); /////////////////////////////////////// DA FARE CON RAGGIO
    }

    public void moveUp() { /////////////////////////////////////////////////////////// DA ROTAZIONE SULLA SFERA
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
