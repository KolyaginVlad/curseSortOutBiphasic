package ru.course.cursesortoutbiphasic;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Класс для управления главным окном
 * Указывается в main.fxml
 * @FXML используется для связки переменных и их отображения. Имена==fx:id
 * initialize() вызовется при отображении экрана. В нём прописываются начальные данные для объектов
 */
public class MainController {
    @FXML
    private TextField count;

    @FXML
    private Button compare;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn<?, ?> size;

    @FXML
    private TableColumn<?, ?> simple;

    @FXML
    private TableColumn<?, ?> nature;

    @FXML
    private TableColumn<?, ?> absorption;

    @FXML
    private Button clean;

    @FXML
    private Button toGraph;

    @FXML
    void initialize() {

    }
}
