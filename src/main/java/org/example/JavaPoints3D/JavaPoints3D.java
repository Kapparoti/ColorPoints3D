package org.example.JavaPoints3D;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;



public class JavaPoints3D extends Application {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;

    private Pane root3D;

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
        root3D.getChildren().add(shape);
        redraw_points();
    }

    public static void main(String[] args) { launch(); }

    @Override
    public void start(Stage stage) throws IOException {
        //Loading scenes
        FXMLLoader fxmlLoader = new FXMLLoader(JavaPoints3D.class.getResource("overlay.fxml")); //Overlay scene with controls
        Pane root = fxmlLoader.load();
        FXMLLoader fxmlLoader1 = new FXMLLoader(JavaPoints3D.class.getResource("scene3D.fxml")); //3D scene
        root3D = fxmlLoader1.load();

        //Setting up the 3D SubScene
        SubScene scene3D = new SubScene(root3D, WIDTH, HEIGHT);
        camera = new Camera3D();
        scene3D.setCamera(camera);
        root.getChildren().add(scene3D);

        //Setting up the main scene
        Scene mainScene = new Scene(root, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        mainScene.setFill(Color.SILVER);
        stage.setTitle("JavaPoints3D");
        stage.setScene(mainScene);
        stage.show();

        //Zoom handler
        stage.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> { camera.zoom(scrollEvent.getDeltaY()); });

        //Camera3D movement handler
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
