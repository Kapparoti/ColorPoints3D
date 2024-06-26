package org.example.JavaPoints3D;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;

import javafx.scene.*;
import javafx.scene.paint.Color;








public class JavaPoints3D extends Application {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private Pane root;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaPoints3D.class.getResource("main_window.fxml"));
        root = fxmlLoader.load();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);

        stage.setTitle("JavaPoints3D");
        stage.setScene(scene);
        stage.show();

        Camera3D camera = new Camera3D();
        scene.setCamera(camera);

        stage.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> { camera.zoom(scrollEvent.getDeltaY()); });

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case W:
                    camera.moveUp();
                case A:
                    camera.moveLeft();
                case S:
                    camera.moveDown();
                case D:
                    camera.moveRight();
            }
        });

        Sphere sphere = new Sphere();
        sphere.translateXProperty().set(WIDTH/2.0);
        sphere.translateYProperty().set(HEIGHT/2.0);
        add_shape(sphere);

        Sphere sphere2 = new Point3D();
        sphere2.translateXProperty().set(WIDTH/4.0);
        sphere2.translateYProperty().set(HEIGHT/4.0);
        add_shape(sphere2);
    }

    private void add_shape(Shape3D shape) {
        root.getChildren().add(shape);
    }

}
