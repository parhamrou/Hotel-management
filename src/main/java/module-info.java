module com.example.hotelmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.hotelmanagement to javafx.fxml;
    exports com.hotelmanagement;
}