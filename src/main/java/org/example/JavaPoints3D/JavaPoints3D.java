package org.example.JavaPoints3D;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;






public class JavaPoints3D extends Application {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;

    private Pane root;

    private Camera3D camera;

    private ArrayList<CustomPoint3D> shapes = new ArrayList<CustomPoint3D>();

    private void sort_shapes() {
        shapes.sort(new Comparator<CustomPoint3D>() {
            @Override
            public int compare(CustomPoint3D o1, CustomPoint3D o2) {
                return Double.compare(o2.getDistance(camera), o1.getDistance(camera));
            }
        });
    }

    private void add_shape(CustomPoint3D shape) {
        shapes.add(shape);
        sort_shapes();
        root.getChildren().add(shape);
        redraw_points();
    }

    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(JavaPoints3D.class.getResource("main_window.fxml"));
        root = fxmlLoader.load();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.SILVER);

        stage.setTitle("JavaPoints3D");
        stage.setScene(scene);
        stage.show();

        camera = new Camera3D();
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

        add_shape(new CustomPoint3D(WIDTH/4.0, HEIGHT/4.0, 100));

        add_shape(new CustomPoint3D(WIDTH/2.0, HEIGHT/2.0, 10));

        add_shape(new CustomPoint3D(WIDTH/4.0, HEIGHT/4.0, 1000));
    }

    private void redraw_points() {
        for (CustomPoint3D shape : shapes) {
            shape.toFront();
        }
    }
}
