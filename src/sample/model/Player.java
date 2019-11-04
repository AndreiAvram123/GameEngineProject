package sample.model;
import sample.interfaces.Movable;
import sample.model.shapes.CustomSquare;

public class  Player  implements Movable {
     private int speedX;
     private int speedY;
     private CustomSquare customShape;
     public Player(CustomSquare customSquare){
         this.customShape = customSquare;
         speedX =10;
         speedY =10;
       }

    @Override
    public void moveRight() {
        customShape.getTopLeft().setX(customShape.getTopLeft().getX()+speedX);
        customShape.setIsMovingOnX(speedX);
     }
    @Override
    public void moveLeft() {
        customShape.getTopLeft().setX(customShape.getTopLeft().getX()- speedX);
        customShape.setIsMovingOnX(speedX);
    }

    @Override
    public void moveDown(){
        customShape.getTopLeft().setY(customShape.getTopLeft().getY()+ speedY);
       customShape.setIsMovingOnY(speedY);
    }

    @Override
    public void moveUp() {
        customShape.getTopLeft().setY(customShape.getTopLeft().getY() - speedY);
        customShape.setIsMovingOnY(speedY);
    }



}
