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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Base> bases = Main.getBases();
        //Заполняем средними значениями
        Map<Long, Double> mapSimple = new HashMap<>();
        Map<Long, Double> mapNature = new HashMap<>();
        Map<Long, Double> mapAbs = new HashMap<>();

        for (int i = 0; i < bases.size(); i++) {
            if (!mapSimple.containsKey(bases.get(i).getSize())) {
                int count = 0;
                mapSimple.put(bases.get(i).getSize(), 0D);
                mapAbs.put(bases.get(i).getSize(), 0D);
                mapNature.put(bases.get(i).getSize(), 0D);
                for (int j = 0; j < bases.size(); j++) {
                    if (bases.get(i).getSize() == bases.get(j).getSize()) {
                        mapSimple.put(bases.get(i).getSize(), mapSimple.get(bases.get(i).getSize()) + bases.get(j).getSimple());
                        mapAbs.put(bases.get(i).getSize(), mapAbs.get(bases.get(i).getSize()) + bases.get(j).getAbsorption());
                        mapNature.put(bases.get(i).getSize(), mapNature.get(bases.get(i).getSize()) + bases.get(j).getNature());
                        count++;
                    }
                }
                mapSimple.put(bases.get(i).getSize(), mapSimple.get(bases.get(i).getSize())/count);
                mapNature.put(bases.get(i).getSize(), mapNature.get(bases.get(i).getSize())/count);
                mapAbs.put(bases.get(i).getSize(), mapAbs.get(bases.get(i).getSize())/count);
            }
        }
        //Заполняем
        for (Long l: mapSimple.keySet()
             ) {
            simple.getData().add(new XYChart.Data(l, mapSimple.get(l)));
            nature.getData().add(new XYChart.Data(l, mapNature.get(l)));
            absorption.getData().add(new XYChart.Data(l, mapAbs.get(l)));
        }
        simple.getData().add(new XYChart.Data(0, 0));
        nature.getData().add(new XYChart.Data(0, 0));
        absorption.getData().add(new XYChart.Data(0, 0));
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