package org.example.JavaPoints3D;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;

import javafx.scene.input.ScrollEvent;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


public class JavaPoints3D extends Application {
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;

    private Group group3D;
    private Camera3D camera;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Loading scenes
        FXMLLoader fxmlLoader = new FXMLLoader(JavaPoints3D.class.getResource("overlay.fxml")); //Overlay scene with controls
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

        //Zoom handler
        mainScene.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> camera.setZoom(camera.getZoom() + scrollEvent.getDeltaY() / 10));

        //Adding some points
        add_shape(new CustomPoint3D(0, 0, 0));

        add_shape(new CustomPoint3D(15, 0, 0));

        add_shape(new CustomPoint3D(0, 10, 0));

        add_shape(new CustomPoint3D(0, 0, 25));

        add_shape(new CustomPoint3D(10, 10, 10));
    }

    private void add_shape(CustomPoint3D shape) {
        group3D.getChildren().add(shape);

        // Click handler
        shape.setOnMouseClicked(_ -> camera.setPivot(shape.getTranslateX(), shape.getTranslateY(), shape.getTranslateZ()));
    }
}
