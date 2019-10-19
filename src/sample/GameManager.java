package sample;

import javafx.scene.Parent;
import sample.controller.Controller;

public class GameManager {

    private int fps;
     private Parent root;

     private Controller controller;



     public GameManager(int fps, Controller controller, Parent root){
         this.controller = controller;
         this.root = root;
         startGame(fps);
         controller.startInterface();

     }



     private void startGame(int fps){
         this.fps = 1000/fps;

     }







}
