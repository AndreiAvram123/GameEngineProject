package sample.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.PlayerInput;
import sample.model.CustomShape;
import sample.model.Player;
import sample.model.TAGS;

import java.util.ArrayList;

public class Game{

@FXML
Canvas gameCanvas;

    private ArrayList<CustomShape> objectsOnCanvas;
    private Player player;
    private PlayerInput playerInput;
    private GraphicsContext graphicsContext;

    public void startGame(ArrayList<CustomShape>objectsOnCanvas) {
        this.objectsOnCanvas = objectsOnCanvas;
         graphicsContext = gameCanvas.getGraphicsContext2D();
        updateObjectsCanvas(objectsOnCanvas);
        getPlayer();
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                 updateGraphicsContextBackground();
                for(CustomShape customShape: objectsOnCanvas){
                   customShape.update();
                }
            }
        };
        animationTimer.start();
    }

    private void updateGraphicsContextBackground() {
        graphicsContext.setFill(Color.LIGHTCYAN);
        graphicsContext.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

    private void updateObjectsCanvas(ArrayList<CustomShape> objectsOnCanvas) {
        for(CustomShape customShape : objectsOnCanvas){
            customShape.updateGraphicsContext(gameCanvas.getGraphicsContext2D());
        }
    }

    private void getPlayer() {
        for(CustomShape customShape : objectsOnCanvas) {
            if (customShape.getTag() == TAGS.PLAYER){
                player = new Player(customShape);
                playerInput = new PlayerInput(gameCanvas.getScene(), player);
                playerInput.start();
            }
        }


    }


}
