module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires java.sql;

    opens com.example to javafx.fxml;
    exports com.example;
}
