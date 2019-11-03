package sample.model;

import sample.model.shapes.CustomShape;
import sample.model.shapes.CustomSquare;

public abstract class Component {
    protected CustomSquare customSquare;

    public Component(CustomSquare customSquare){
        this.customSquare = customSquare;
    }


    public abstract void causeEffect();
}
