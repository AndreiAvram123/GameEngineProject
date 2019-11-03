package sample.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import netscape.security.UserTarget;
import processing.core.PApplet;
import sample.controller.LevelEditor;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("main_scene.fxml").openStream());
        primaryStage.setTitle("Andrei's game engine");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
          ((LevelEditor) fxmlLoader.getController()).startEditor(primaryStage);


    }

}
