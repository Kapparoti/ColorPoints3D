package org.example.ColorPoints3D;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import java.util.Random;
import javafx.scene.input.ScrollEvent;


public class ColorPoints3D extends Application {
    //Size of the window
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;

    //Interface
    private final ColorPicker colorPick = new ColorPicker();
    private final CheckBox enableHighlight = new CheckBox("Enable selected point highlight");
    private final Button randomButton = new Button("Spawn random");
    private final Button clearButton = new Button("Delete all");

    //Point select
    private ColorPoint selectedPoint;
    private Highlight highlight;

    //For mouse drag
    private double lastMouseX = 0;
    private double lastMouseY = 0;

    //Group of the points and camera
    private Group group3D;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Loading base scene
        FXMLLoader fxmlLoader = new FXMLLoader(ColorPoints3D.class.getResource("overlay.fxml"));
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
        stage.setTitle("ColorPoints3D");
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.show();

        //Setting up the camera
        scene3D.setCamera(CustomCamera.getInstance());

        //Setting up highlight node
        highlight = new Highlight();
        group3D.getChildren().add(highlight);

        //Zoom handler
        mainScene.addEventHandler(ScrollEvent.SCROLL, scrollEvent -> CustomCamera.getInstance().setRadius(CustomCamera.getInstance().getRadius() + scrollEvent.getDeltaY() / 10));

        //Drag handler
        mainScene.setOnMousePressed(dragEnterEvent -> {
            lastMouseX = dragEnterEvent.getSceneX();
            lastMouseY = dragEnterEvent.getSceneY();
        });

        mainScene.setOnMouseDragged(mouseDragEvent -> {
            if (lastMouseX >= 0 && lastMouseY >= 0) {
                CustomCamera.getInstance().rotateX(lastMouseX - mouseDragEvent.getSceneX());
                lastMouseX = mouseDragEvent.getSceneX();
                CustomCamera.getInstance().rotateY(lastMouseY - mouseDragEvent.getSceneY());
                lastMouseY = mouseDragEvent.getSceneY();
            }
        });

        mainScene.setOnMouseReleased(_ -> {
            lastMouseX = -1;
            lastMouseY = -1;
        });

        //Color picker
        colorPick.setOnAction(_ -> selectedPoint.setColor(colorPick.getValue()));

        //Selected point highlight checkbox
        enableHighlight.setSelected(true);
        enableHighlight.setOnAction(_ -> highlight.setVisible(enableHighlight.isSelected()));

        //Random button
        randomButton.setOnAction(_ -> {
            Random r = new Random();
            double maxRange = (Math.abs(CustomCamera.getInstance().getRadius()) / 5);
            add_shape(new ColorPoint(
                    selectedPoint.getPosition().getX() + r.nextDouble(2 * maxRange) - maxRange,
                    selectedPoint.getPosition().getY() + r.nextDouble(2 * maxRange) - maxRange,
                    selectedPoint.getPosition().getZ() + r.nextDouble(2 * maxRange) - maxRange,
                    Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)))
            );
        });

        //Clear button
        clearButton.setOnAction(_ -> {
            group3D.getChildren().clear();
            group3D.getChildren().add(CustomCamera.getInstance());
        });

        //Adding starting point
        ColorPoint startingPoint = new ColorPoint(0, 0, 0, null);
        add_shape(startingPoint);
        highlightPoint(startingPoint);
    }

    private void add_shape(ColorPoint point) {
        group3D.getChildren().add(point);
        // Click handler
        point.setOnMouseClicked(_ -> highlightPoint(point));
    }

    public void highlightPoint(ColorPoint point) {
        selectedPoint = point;

        CustomCamera.getInstance().setPivot(selectedPoint.getPosition());

        colorPick.setValue(selectedPoint.getColor());

        if (highlight.getParent() == null) { group3D.getChildren().add(highlight); }

        highlight.setPosition(selectedPoint.getPosition());

        highlight.toFront();
    }

}
