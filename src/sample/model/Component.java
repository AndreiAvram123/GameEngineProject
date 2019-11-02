package sample.model;

public abstract class Component {
    protected CustomShape customShape;

    public Component(CustomShape customShape){
        this.customShape = customShape;
    }


    public abstract void causeEffect();
}
