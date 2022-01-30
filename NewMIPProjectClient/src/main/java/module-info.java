module com.example.newmipprojectclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.client to javafx.fxml;
    exports com.client;
}