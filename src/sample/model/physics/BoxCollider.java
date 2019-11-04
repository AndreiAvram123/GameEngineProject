package sample.model.physics;

import org.jetbrains.annotations.NotNull;
import sample.model.Component;
import sample.model.Point;
import sample.model.enums.SIDES;
import sample.model.shapes.CustomSquare;

public class BoxCollider extends Component {

    public BoxCollider(CustomSquare customSquare) {
        super(customSquare);

    }
    public CustomSquare getShape(){
        return customSquare;
    }


    public SIDES getCollisionSide(BoxCollider boxCollider){
          //right
        //left
         //above
        //below
        // if(speedX != 0) left and right
         // if (speedY !=0) top and bottom

        if(isTouchingLeft(boxCollider)){
            return SIDES.LEFT;
        }
        if(isTouchingRight(boxCollider)){
            return SIDES.RIGHT;
        }
        if(isTouchingBelow(boxCollider)){
            return SIDES.BOTTOM;
        }

        if(isTouchingAbove(boxCollider)){
            return SIDES.TOP;
        }



        return SIDES.NONE;
    }

    private boolean isTouchingAbove(@NotNull BoxCollider boxCollider){
        return ((getBottonRight().getY()>=boxCollider.getTopLeft().getY()
                  &&getBottonRight().getY()<=boxCollider.getBottomLeft().getY())
                  &&((getBottomLeft().getX()>=boxCollider.getTopLeft().getX()
                      &&getBottomLeft().getX()<=boxCollider.getTopRight().getX())
                ||(getBottomLeft().getX()>=boxCollider.getTopLeft().getX()&&
                   getBottomLeft().getX()<=boxCollider.getTopRight().getX())));
    }
    //done
    private boolean isTouchingBelow(@NotNull BoxCollider boxCollider){
        return (getTopLeft().getY()<=boxCollider.getBottonRight().getY()
                &&
                getTopLeft().getY() >=boxCollider.getTopRight().getY()

        )
        && ((boxCollider.getBottonRight().getX() > getTopLeft().getX()
        && boxCollider.getBottonRight().getX() < getTopRight().getX())
        || (boxCollider.getBottomLeft().getX() > getTopLeft().getX()
        &&boxCollider.getBottomLeft().getX() < getTopRight().getX()));


    }
    //done
    private boolean isTouchingRight(@NotNull BoxCollider boxCollider){
        return ((getTopLeft().getX()>=boxCollider.getTopLeft().getX()
                &&
                getTopLeft().getX()<= boxCollider.getTopRight().getX())
                &&
                ((getBottomLeft().getY()>=boxCollider.getTopRight().getY()
                   && getBottomLeft().getY() <=boxCollider.getBottonRight().getY())
                   ||
                        (getTopLeft().getY()>=boxCollider.getTopRight().getY()
                          &&getTopLeft().getY()<=boxCollider.getBottonRight().getY()
                         )
               ));

    }
    //done
    private boolean isTouchingLeft(@NotNull BoxCollider boxCollider){
         return (((getTopRight().getY() >= boxCollider.getTopLeft().getY()
                && getTopRight().getY() <=boxCollider.getBottomLeft().getY())
                 ||(getBottonRight().getY()>=boxCollider.getTopLeft().getY()
                 && getBottonRight().getY()<=boxCollider.getBottomLeft().getY()))

         && (getTopRight().getX()>=boxCollider.getTopLeft().getX())
             &&getTopRight().getX()<=boxCollider.getTopRight().getX());
    }




    //============================GETTERS AND SETTERS================================
    public Point getTopLeft() {
        return customSquare.getTopLeft();
    }

    public Point getTopRight() {
        return customSquare.getTopRight();
    }

    public Point getBottomLeft() {
        return customSquare.getBottomLeft();
    }

    public Point getBottonRight() {
        return customSquare.getBottomRight();
    }

    @Override
    public void causeEffect() {

    }



}
