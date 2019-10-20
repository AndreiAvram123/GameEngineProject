package sample;

import javafx.scene.Parent;
import sample.controller.LevelEditor;

public class GameManager {

    private int fps;
     private Parent root;

     private LevelEditor levelEditor;



     public GameManager(int fps, LevelEditor levelEditor, Parent root){
         this.levelEditor = levelEditor;
         this.root = root;
         startGame(fps);
         levelEditor.startEditor();

     }



     private void startGame(int fps){
         this.fps = 1000/fps;

     }







}
