module org.example.points3d {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.xml.crypto;


    opens org.example.ColorPoints3D to javafx.fxml;
    exports org.example.ColorPoints3D;
}