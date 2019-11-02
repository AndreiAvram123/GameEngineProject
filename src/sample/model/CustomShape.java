package sample.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class CustomShape{

    private String name;
    GraphicsContext graphicsContext;
    double x;
    double y;
    double height =100;
    double width = 100;
    private ArrayList<Component> components;

    public static double getDefaultWidth(){
        return 100;
    }
    public static double getDefaultHeight(){
        return 100;
    }
    private void addDefaultComponents(){
        addComponent(new RigidBody(this));
        addComponent(new Collider(this));
    }

    public CustomShape(GraphicsContext graphicsContext,String name,double x,double y){
        this.name = name;
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
        components = new ArrayList<>();
        addDefaultComponents();
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
