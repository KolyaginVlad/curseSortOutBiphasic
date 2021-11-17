package ru.course.cursesortoutbiphasic;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class SecondInterface {
    //В скобках указываются типы осей
    @FXML
    private LineChart<NumberAxis, NumberAxis> chart;

    @FXML
    private Button goToTable;

    @FXML
    void initialize() {
        //Добавил ради отличий: проверяет корректно ли инициализировались переменные (часть автогенерации)
        assert chart != null : "fx:id=\"chart\" was not injected: check your FXML file 'graph.fxml'.";
        assert goToTable != null : "fx:id=\"goToTable\" was not injected: check your FXML file 'graph.fxml'.";
        goToTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Stage stage = Main.getSavedStage();
                    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
                    //Преобразуем, устанавливая размеры окна
                    Scene scene = new Scene(fxmlLoader.load(), 800, 700);
                    //Устанавливаем внутренности окна
                    stage.setScene(scene);
                    //Демострируем окно (до этого его не видно)
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        //Создаём классы, хранящие точки на графике
        XYChart.Series simple = new XYChart.Series();
        XYChart.Series nature = new XYChart.Series();
        XYChart.Series absorption = new XYChart.Series();
        List<Base> nodes = Main.getBases();
        //Заполняем
        for (int i = 0; i < nodes.size(); i++) {
            simple.getData().add(new XYChart.Data(nodes.get(i).getSize(), nodes.get(i).getSimple()));
            nature.getData().add(new XYChart.Data(nodes.get(i).getSize(), nodes.get(i).getNature()));
            absorption.getData().add(new XYChart.Data(nodes.get(i).getSize(), nodes.get(i).getAbsorption()));
        }
        //Задаём им имена
        simple.setName("Сортировка простым слиянием");
        nature.setName("Сортировка естественным слияние");
        absorption.setName("Сортировка методом поглощения");
        //Отображем
        chart.getData().add(simple);
        chart.getData().add(nature);
        chart.getData().add(absorption);
    }
}