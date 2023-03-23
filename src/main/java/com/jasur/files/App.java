package com.jasur.files;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage)  {
        FileModel fileModel = new FileModel("", "", "", ".txt");
        View view = new View(stage);
        Controller controller = new Controller(view, fileModel);
    }

    public static void main(String[] args) {
        launch();
    }
}