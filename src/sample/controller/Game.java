package sample.controller;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import sample.PlayerInput;
import sample.model.Player;
import sample.model.enums.TAGS;
import sample.model.physics.PhysicsEngine;
import sample.model.shapes.CustomShape;
import sample.model.shapes.CustomSquare;

import java.util.ArrayList;
public class Game{

@FXML
Canvas gameCanvas;

    private ArrayList<CustomSquare> objectsOnCanvas;
    private PhysicsEngine physicsEngine;
    private Player player;
    private PlayerInput playerInput;
    private GraphicsContext graphicsContext;


    public void startGame(ArrayList<CustomSquare>objectsOnCanvas) {
        this.objectsOnCanvas = objectsOnCanvas;
        physicsEngine = new PhysicsEngine(objectsOnCanvas);
        graphicsContext = gameCanvas.getGraphicsContext2D();
        updateObjectsCanvas(objectsOnCanvas);
        getPlayer();
        startGameLoop(objectsOnCanvas);
    }

    private void startGameLoop(ArrayList<CustomSquare> objectsOnCanvas) {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                 updateGraphicsContextBackground();
                for(CustomShape customShape: objectsOnCanvas){
                   customShape.update();
                }
                physicsEngine.update();
            }
        };
        animationTimer.start();
    }


    private void updateGraphicsContextBackground() {
        graphicsContext.setFill(Color.LIGHTCYAN);
        graphicsContext.fillRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
    }

    private void updateObjectsCanvas(ArrayList<CustomSquare> objectsOnCanvas) {
        for(CustomShape customShape : objectsOnCanvas){
            customShape.updateGraphicsContext(gameCanvas.getGraphicsContext2D());
            customShape.setInEditor(false);
        }
    }

    private void getPlayer() {
        for(CustomSquare customSquare: objectsOnCanvas) {
            if (customSquare.getTag() == TAGS.PLAYER){
                player = new Player(customSquare);
                playerInput = new PlayerInput(gameCanvas.getScene(), player);
                playerInput.start();
            }
        }


    }


}
