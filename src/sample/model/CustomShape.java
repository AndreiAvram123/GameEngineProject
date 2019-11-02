package sample.model;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class CustomShape extends Node{

    private String name;
    GraphicsContext graphicsContext;
    double x;
    double y;
    double height =100;
    double width = 100;
    private ArrayList<Component> components;
    protected String colorCode;

    private void addDefaultComponents(){
        addComponent(new RigidBody(this));
        addComponent(new Collider(this));
    }
    public void setNewCenter(double x, double y){
        this.x = x - width/2;
        this.y = y - height/2;
    }


    public CustomShape(GraphicsContext graphicsContext,String name,double x,double y){
        this.name = name;
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
        components = new ArrayList<>();
        addDefaultComponents();
        colorCode = "#0000ff";
    }

    public boolean hasComponent(String className){
        for(Component component : components) {
            if(component.getClass().getSimpleName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    public void  removeComponent(String className){
        for(Component component : components) {
            if(component.getClass().getSimpleName().equals(className)) {
                components.remove(component);
            }
        }
    }

    public void addComponent(Component component){
      components.add(component);
    }

    public Component getComponent(String className){
        for(Component component : components) {
            if(component.getClass().getSimpleName().equals(className)) {
                return component;
            }
        }
        return null;
    }


    public void updateGraphicsContext(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public boolean isShapeWithinRange(double x, double y) {
            return (getX() <= x && getY() <= y)
                    && (getHeight() + getY() >= y && getWidth() + getX() >= x);

    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }


    public void update(){
          drawShape();
          produceComponentsEffect();

    }

    private void produceComponentsEffect() {
        for(Component component: components){
            component.causeEffect();
        }
    }

    abstract void drawShape();

}
