package org.example.JavaPoints3D;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;

import javafx.scene.input.ScrollEvent;

import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.PhongMaterial;

import javafx.scene.shape.Sphere;

import java.util.Random;


public class JavaPoints3D extends Application {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;

    private final ColorPicker colorPick = new ColorPicker();

    private final Button spawnRandom = new Button("Random");

    private final Sphere highlight = new Sphere(1);

    private double lastMouseX = 0;
    private double lastMouseY = 0;

    private Group group3D;
    private Camera3D camera;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Loading base scene
        FXMLLoader fxmlLoader = new FXMLLoader(JavaPoints3D.class.getResource("overlay.fxml"));
        Pane root = fxmlLoader.load();

        //Setting up the interface menu
        HBox menuHBox = new HBox();
        menuHBox.setMinHeight(30);
        menuHBox.setSpacing(50);

        menuHBox.getChildren().add(colorPick);
        menuHBox.getChildren().add(spawnRandom);

        root.getChildren().add(menuHBox);

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
        highlight.setMaterial( new PhongMaterial(Color.rgb(200, 200, 200, 0.1)));
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


        //Random button
        spawnRandom.setOnAction(_ -> {
            Random r = new Random();
            double maxRange = Math.abs(camera.getZoom());
            add_shape(new CustomPoint3D(
                    highlight.getTranslateX() + r.nextDouble(r.nextDouble(maxRange)),
                    highlight.getTranslateY() + r.nextDouble(r.nextDouble(maxRange)),
                    highlight.getTranslateZ() + r.nextDouble(r.nextDouble(maxRange)),
                    Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)))
            );
        });
        //Adding some points
        CustomPoint3D startingPoint = new CustomPoint3D(0, 0, 0, Color.rgb(0, 0, 0));
        add_shape(startingPoint);
        highlightPoint(startingPoint);

        add_shape(new CustomPoint3D(15, 0, 0, Color.rgb(0, 0, 0)));

        add_shape(new CustomPoint3D(0, 10, 0, Color.rgb(0, 0, 0)));

        add_shape(new CustomPoint3D(0, 0, 25, Color.rgb(0, 0, 0)));

        add_shape(new CustomPoint3D(10, 10, 10, Color.rgb(0, 0, 0)));
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
