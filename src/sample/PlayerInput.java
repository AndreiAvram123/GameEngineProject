package sample;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import sample.interfaces.Movable;


public class PlayerInput {

    private Movable object;
    private Scene scene;

    public PlayerInput(Scene scene, Movable object){
        this.scene = scene;
        this.object = object;
    }
    public void start(){
        setListenerOnParent();
    }

    private void setListenerOnParent(){
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case A :{
                    object.moveLeft();
                    break;
                }
                case D :{
                    object.moveRight();
                    break;
                }
                case W :{
                    object.moveUp();
                    break;
                }
                case S :{
                    object.moveDown();
                    break;
                }

            }
        });
    }
}
