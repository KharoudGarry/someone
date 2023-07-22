module com.example.assignmenttwo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.assignmenttwo to javafx.fxml;
    exports com.example.assignmenttwo;
}