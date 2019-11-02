package sample.model;

import javafx.scene.canvas.GraphicsContext;
import sample.interfaces.Movable;

public class  Player extends Component implements Movable {
     private int speedX;
     private int speedY;

     public Player(CustomShape customShape){
         super(customShape);
         speedX =10;
         speedY =10;
       }


    @Override
    public void moveRight() {
        customShape.setX(customShape.getX()+speedX);
    }

    @Override
    public void moveLeft() {
        customShape.setX(customShape.getX()-speedX);
    }

    @Override
    public void moveDown(){
        customShape.setY(customShape.getY() + speedY);
    }

    @Override
    public void moveUp() {
        customShape.setY(customShape.getY() - speedY);
    }


    @Override
    public void causeEffect() {

    }
}
