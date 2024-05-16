module com.mentarirvmp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mentarirvmp to javafx.fxml;
    exports com.mentarirvmp;
}
