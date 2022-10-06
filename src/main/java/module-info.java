module com.example.hotelmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.controllers to javafx.fxml;
    exports com.app;
    opens com.controllers.reception to javafx.fxml;
    opens com.controllers.restaurant to javafx.fxml;
    opens com.controllers.mainPage to javafx.fxml;
}