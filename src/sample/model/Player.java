package sample.model;
import sample.interfaces.Movable;

public class  Player  implements Movable {
     private int speedX;
     private int speedY;
     private  CustomShape customShape;

     public Player(CustomShape customShape){
         this.customShape = customShape;
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


}
