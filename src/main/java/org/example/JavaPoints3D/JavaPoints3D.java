package org.example.JavaPoints3D;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;

import javafx.scene.input.ScrollEvent;

import javafx.scene.paint.PhongMaterial;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class JavaPoints3D extends Application {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;

    private static final Sphere highlight = new Sphere(1);

    private double lastMouseX = 0;
    private double lastMouseY = 0;

    private Group group3D;
    private Camera3D camera;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Loading scenes
        FXMLLoader fxmlLoader = new FXMLLoader(JavaPoints3D.class.getResource("overlay.fxml")); //Overlay scenes with the gui controls
        Pane root = fxmlLoader.load();

        //Setting up the 3D SubScene
        group3D = new Group();
        SubScene scene3D = new SubScene(group3D, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        root.getChildren().add(scene3D);

        //Setting up the main scene
        Scene mainScene = new Scene(root, WIDTH, HEIGHT, true, SceneAntialiasing.BALANCED);
        mainScene.setFill(Color.SILVER);
        stage.setTitle("JavaPoints3D");
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.show();

        //Setting up the camera
        camera = new Camera3D();
        scene3D.setCamera(camera);

        //Setting up the highlight node
        highlight.setMaterial( new PhongMaterial(Color.web("#ffff0010")));
        highlight.setOpacity(0.1);
        group3D.getChildren().add(highlight);

        //Zoom handler
        mainScene.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> camera.setZoom(camera.getZoom() + scrollEvent.getDeltaY() / 10));

        //Drag handler
        mainScene.setOnMousePressed(dragEnterEvent -> {
            lastMouseX = dragEnterEvent.getSceneX();
            lastMouseY = dragEnterEvent.getSceneY();
        });

        mainScene.setOnMouseDragged(mouseDragEvent -> {
            if (lastMouseX >= 0 && lastMouseY >= 0) {
                camera.rotateX(lastMouseX - mouseDragEvent.getSceneX());
                lastMouseX = mouseDragEvent.getSceneX();
                camera.rotateY(lastMouseY - mouseDragEvent.getSceneY());
                lastMouseY = mouseDragEvent.getSceneY();
            }
        });

        mainScene.setOnMouseReleased(_ -> {
            lastMouseX = -1;
            lastMouseY = -1;
        });

        //Adding some points
        CustomPoint3D startingPoint = new CustomPoint3D(0, 0, 0);
        add_shape(startingPoint);
        highlightPoint(startingPoint);

        add_shape(new CustomPoint3D(15, 0, 0));

        add_shape(new CustomPoint3D(0, 10, 0));

        add_shape(new CustomPoint3D(0, 0, 25));

        add_shape(new CustomPoint3D(10, 10, 10));
    }

    private void add_shape(CustomPoint3D point) {
        group3D.getChildren().add(point);

        // Click handler
        point.setOnMouseClicked(_ -> highlightPoint(point));
    }

    public void highlightPoint(CustomPoint3D point) {
        camera.setPivot(point.getX(), point.getY(), point.getZ());

        highlight.setTranslateX(point.getX());
        highlight.setTranslateY(point.getY());
        highlight.setTranslateZ(point.getZ());

        highlight.toFront();
    }

}
