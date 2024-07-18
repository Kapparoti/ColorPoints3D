package org.example.JavaPoints3D;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.CheckBox;
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
    //Size of the window
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;

    //Interface
    private final ColorPicker colorPick = new ColorPicker();
    private final CheckBox enableHighlight = new CheckBox("Enable selected point highlight");
    private final Button randomButton = new Button("Spawn random");
    private final Button clearButton = new Button("Delete all");

    //Point select
    private CustomPoint3D selectedPoint;
    private Sphere highlight;

    //For mouse drag
    private double lastMouseX = 0;
    private double lastMouseY = 0;

    //Group of the points and camera
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
        menuHBox.getChildren().add(enableHighlight);
        menuHBox.getChildren().add(randomButton);
        menuHBox.getChildren().add(clearButton);

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

        //Setting up highlight node
        highlight = new Sphere(1);
        highlight.setMaterial( new PhongMaterial(Color.rgb(255, 255, 255, 0.1)));
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

        //Color picker
        colorPick.setOnAction(_ -> {
            selectedPoint.setColor(colorPick.getValue());
        });

        //Selected point highlight checkbox
        enableHighlight.setSelected(true);
        enableHighlight.setOnAction(_ -> highlight.setVisible(enableHighlight.isSelected()));

        //Random button
        randomButton.setOnAction(_ -> {
            Random r = new Random();
            double maxRange = (Math.abs(camera.getZoom()) / 5);
            add_shape(new CustomPoint3D(
                    selectedPoint.getTranslateX() + r.nextDouble(2 * maxRange) - maxRange,
                    selectedPoint.getTranslateY() + r.nextDouble(2 * maxRange) - maxRange,
                    selectedPoint.getTranslateZ() + r.nextDouble(2 * maxRange) - maxRange,
                    Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)))
            );
        });

        //Clear button
        clearButton.setOnAction(_ -> {
            group3D.getChildren().clear();
            group3D.getChildren().add(camera);
        });

        //Adding some points
        CustomPoint3D startingPoint = new CustomPoint3D(0, 0, 0, null);
        add_shape(startingPoint);
        highlightPoint(startingPoint);

        add_shape(new CustomPoint3D(15, 0, 0, null));

        add_shape(new CustomPoint3D(0, 10, 0, null));

        add_shape(new CustomPoint3D(0, 0, 25, null));

        add_shape(new CustomPoint3D(10, 10, 10, null));
    }

    private void add_shape(CustomPoint3D point) {
        group3D.getChildren().add(point);
        // Click handler
        point.setOnMouseClicked(_ -> highlightPoint(point));
    }

    public void highlightPoint(CustomPoint3D point) {
        selectedPoint = point;

        camera.setPivot(selectedPoint.getX(), selectedPoint.getY(), selectedPoint.getZ());

        colorPick.setValue(selectedPoint.getColor());

        if (highlight.getParent() == null) { group3D.getChildren().add(highlight); }

        highlight.setTranslateX(selectedPoint.getX());
        highlight.setTranslateY(selectedPoint.getY());
        highlight.setTranslateZ(selectedPoint.getZ());

        highlight.toFront();
    }

}
