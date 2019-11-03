package sample.model.physics;

import sample.model.Component;
import sample.model.Point;
import sample.model.enums.SIDES;
import sample.model.shapes.CustomSquare;

public class BoxCollider extends Component {


//todo
    //add offset to modify collder
    public BoxCollider(CustomSquare customSquare) {
        super(customSquare);

    }
    public CustomSquare getShape(){
        return customSquare;
    }


    public SIDES getCollisionSide(BoxCollider boxCollider){
         //if it is touching above then the hit side side is bottom

        if(isTouchingAbove(boxCollider)){
            return SIDES.BOTTOM;
        }
        if(isTouchingBelow(boxCollider)){
            return SIDES.TOP;
        }
        if(isTouchingRight(boxCollider)){
            return SIDES.LEFT;

        }
        if(isTouchingLeft(boxCollider)){
            return SIDES.RIGHT;
        }
        return SIDES.NONE;
    }

    public boolean isTouchingAbove(BoxCollider boxCollider){
        return getBottonRight().getX()> boxCollider.getTopLeft().getX() &&
                getBottonRight().getX() < boxCollider.getTopRight().getX()
                && getTopLeft().getY() <=boxCollider.getBottonRight().getY();
    }
    public boolean isTouchingBelow(BoxCollider boxCollider){
        return  boxCollider.getBottomLeft().getY() >= getTopRight().getY()
                && boxCollider.getBottomLeft().getY() <getBottomLeft().getY();
    }
    public boolean isTouchingRight(BoxCollider boxCollider){
        return boxCollider.getTopRight().getX() >= getBottomLeft().getX()
                && boxCollider.getTopRight().getX() <getTopRight().getX();
    }
    public boolean isTouchingLeft(BoxCollider boxCollider){
        return boxCollider.getBottomLeft().getX() <= getTopRight().getX()
                && boxCollider.getBottomLeft().getX() > getBottomLeft().getX();
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
