module com.example.hotelmanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.hotelmanagement to javafx.fxml;
    exports com.hotelmanagement;
}