package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import sample.interfaces.Movable;
import sample.model.CustomShape;
import sample.model.Player;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
    @FXML
    Canvas canvas;
    private ArrayList<CustomShape> objectsOnCanvas;
    private GraphicsContext graphicsContext;
    private Player player;
    public void addElementsOnScreen(ArrayList<CustomShape>objectsOnCanvas){
          this.objectsOnCanvas = objectsOnCanvas;
          graphicsContext = canvas.getGraphicsContext2D();
          updateObjectsGraphicsContext();
          getPlayer();
          updateCanvas();
          if(player!=null) {
              checkInput();
          }
    }

    private void getPlayer() {
        for(CustomShape customShape : objectsOnCanvas){
            if(customShape.hasComponent(Player.class.getSimpleName())){
                player = (Player)customShape.getComponent(Player.class.getSimpleName());
            }
        }
    }

    private void updateObjectsGraphicsContext() {
         for(CustomShape customShape : objectsOnCanvas){
             customShape.updateGraphicsContext(graphicsContext);
         }
    }


    private void checkInput(){
            canvas.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
                Movable playerMovement = player;
                switch (key.getCode()){
                    case A:{
                        playerMovement.moveLeft();
                        break;
                    }
                    case W:{
                        playerMovement.moveUp();
                        break;
                    }
                    case D:{
                        playerMovement.moveRight();
                        break;
                    }
                    case S:{
                        playerMovement.moveDown();
                        break;
                    }


                }
            });

    }

    private void updateCanvas(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateGraphicsContextBackground();
                for(CustomShape customShape : objectsOnCanvas){
                    customShape.update();
                }

            }
        },0,50);
    }
    private void updateGraphicsContextBackground() {
        graphicsContext.setFill(Color.LIGHTCYAN);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }
}
