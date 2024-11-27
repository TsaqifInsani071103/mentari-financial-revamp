module com.mentarirvmp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    // requires com.google.gson;
    // requires java.prefs;

    opens com.mentarirvmp.controllers to javafx.fxml;
    opens com.mentarirvmp.utils to javafx.fxml;
    opens com.mentarirvmp.fileoperations to javafx.fxml;

    opens com.mentarirvmp to javafx.fxml;
    exports com.mentarirvmp;
}
