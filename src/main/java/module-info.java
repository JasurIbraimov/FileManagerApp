module com.jasur.files {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.jasur.files to javafx.fxml;
    exports com.jasur.files;
}