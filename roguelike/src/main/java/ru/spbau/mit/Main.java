package ru.spbau.mit;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.spbau.mit.controller.WorldController;
import ru.spbau.mit.model.World;
import ru.spbau.mit.view.WorldView;

public class Main extends Application {
    private static final int WINDOW_SIZE = 1000;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World!");

        World world = new World();

        WorldView worldView = new WorldView(world, WINDOW_SIZE, world.getSize());
        Scene primaryScene = new Scene(worldView.getRoot(),
                worldView.getWidth(), worldView.getHeight());

        WorldController worldController = new WorldController(world, primaryScene);

        primaryStage.setScene(primaryScene);
        primaryStage.show();
    }
}
