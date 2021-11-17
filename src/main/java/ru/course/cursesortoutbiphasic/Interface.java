package ru.course.cursesortoutbiphasic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Класс для управления главным окном
 * Указывается в main.fxml
 *
 * @FXML используется для связки переменных и их отображения. Имена==fx:id
 * initialize() вызовется при отображении экрана. В нём прописываются начальные данные для объектов
 */
public class Interface {
    @FXML
    private TextField count;

    @FXML
    private Button compare;

    //В угловые ковычки передаю класс, который хранит информацию о строке. Переменные - значения в столбцах
    @FXML
    private TableView<Base> table;

    //В угловых ковычках класс, который хранит информацию и тип значения. Использую классы-обёртки потому что джава не принимает в такие ковычки примитивы(Это всё называется дженерики)
    @FXML
    private TableColumn<Base, Long> size;

    @FXML
    private TableColumn<Base, Long> simple;

    @FXML
    private TableColumn<Base, Long> nature;

    @FXML
    private TableColumn<Base, Long> absorption;

    @FXML
    private Button clean;

    @FXML
    private Button toGraph;

    @FXML
    void initialize() {
        //Добавил ради отличий: проверяет корректно ли инициализировались переменные (часть автогенерации)
        assert count != null : "fx:id=\"count\" was not injected: check your FXML file 'main.fxml'.";
        assert compare != null : "fx:id=\"compare\" was not injected: check your FXML file 'main.fxml'.";
        assert table != null : "fx:id=\"table\" was not injected: check your FXML file 'main.fxml'.";
        assert size != null : "fx:id=\"size\" was not injected: check your FXML file 'main.fxml'.";
        assert simple != null : "fx:id=\"simple\" was not injected: check your FXML file 'main.fxml'.";
        assert nature != null : "fx:id=\"nature\" was not injected: check your FXML file 'main.fxml'.";
        assert absorption != null : "fx:id=\"absorption\" was not injected: check your FXML file 'main.fxml'.";
        assert clean != null : "fx:id=\"clean\" was not injected: check your FXML file 'main.fxml'.";
        assert toGraph != null : "fx:id=\"toGraph\" was not injected: check your FXML file 'main.fxml'.";
        //очищает таблицу и график при нажатии
        clean.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.getBases().clear();
                table.setItems(null);
            }
        });
        //при нажатии переходит на страницу с графиком
        toGraph.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Stage newStage = Main.getSavedStage();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("graph.fxml"));
                    //Преобразуем, устанавливая размеры окна
                    Scene scene = new Scene(fxmlLoader.load(), 800, 800);
                    //Устанавливаем внутренности окна
                    newStage.setScene(scene);
                    //Демострируем окно (до этого его не видно)
                    newStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //при нажатии начинает вычисления
        compare.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Берём текст с поля для ввода и приводим к числу
                long len = Long.parseLong(count.getText());
                //Создаём класс функций
                Algoritms algoritms = new Algoritms();
                //Начинаем вычисление, указав длинну
                Base baseToAdd = algoritms.time(len);
                //Добавляем в список строк
                Main.getBases().add(baseToAdd);
                //Отображаем новый список
                ObservableList<Base> list = FXCollections.observableList(Main.getBases());
                table.setItems(list);
            }
        });
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        simple.setCellValueFactory(new PropertyValueFactory<>("simple"));
        nature.setCellValueFactory(new PropertyValueFactory<>("nature"));
        absorption.setCellValueFactory(new PropertyValueFactory<>("absorption"));
        //Отображаем список линий
        ObservableList<Base> list = FXCollections.observableList(Main.getBases());
        table.setItems(list);
    }
}
