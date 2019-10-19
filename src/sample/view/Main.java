package sample.view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import sample.GameManager;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {

    private GameManager gameManager;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("main_scene.fxml").openStream());
        primaryStage.setTitle("Andrei's game engine");
        primaryStage.setResizable(false);
        primaryStage.setScene( new Scene(root, 1200, 800));
        primaryStage.show();
        gameManager = new GameManager(10,fxmlLoader.getController(),root
                );

    }



    public static void main(String[] args) {
        launch(args);
    }
}
