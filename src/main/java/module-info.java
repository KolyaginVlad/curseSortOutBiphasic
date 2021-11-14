module ru.course.cursesortoutbiphasic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens ru.course.cursesortoutbiphasic to javafx.fxml;
    exports ru.course.cursesortoutbiphasic;
}