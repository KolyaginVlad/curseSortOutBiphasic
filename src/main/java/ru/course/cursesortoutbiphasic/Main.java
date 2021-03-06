package ru.course.cursesortoutbiphasic;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Главный класс, в котором находится точка входа в программу.
 * Наследуется от Application и переопределяет его метод start
 * Stage - класс, отвечающий за представление окна. Кнопки свернуть, растянуть и закрыть, размеры окна, его заголовок.
 * throws IOException- output\input exception - ошибка ввода\вывода - исключение, которое выбрасывается если при вызове fxmlLoader.load() не будет найден файл.
 * @Override - аннотация, помогающая программисту не ошибиться при переопределении метода. Если вы нарушите струтуру переопределяемого метода, то она начнёт ругаться
 **/
public class Main extends Application {
    //инициализируем список
    private static ArrayList<Base> bases = new ArrayList<>();
    private static Stage savedStage;

    @Override
    public void start(Stage stage) throws IOException {
        //Создаём загрузчик fxml файло, который преопразует fxml в графическое представление (Scene)
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main.fxml"));
        //Преобразуем, устанавливая размеры окна
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        //Ставим заголовок
        stage.setTitle("Алгоритмы внешней сортировки");
        //Делаем жёсткий размер окна
        stage.setResizable(false);
        //Устанавливаем внутренности окна
        stage.setScene(scene);
        //Демострируем окно (до этого его не видно)
        stage.show();
        //сохраняем ссылку на окно
        savedStage = stage;
    }

    public static void main(String[] args) {
        //Запускаем процесс отображения окна
        launch(args);
    }

    public static List<Base> getBases() {
        return bases;
    }

    public static Stage getSavedStage() {
        return savedStage;
    }

}