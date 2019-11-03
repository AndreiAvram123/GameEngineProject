package sample.model;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class CustomShape extends Node{

    GraphicsContext graphicsContext;
    String colorCode= "#e9ff42";
    String tag = "None";
    private String name;
    private double x;
    private double y;
    private  double height =100;
    private double width = 100;
    private ArrayList<Component> components= new ArrayList<>();;


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
        Component toRemove = null;
        for(Component component : components) {
            if(component.getClass().getSimpleName().equals(className)) {
               toRemove = component;
            }
        }
        if(toRemove !=null){
            components.remove(toRemove);
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


   public void setTag(String tag){
       this.tag = tag;
   }
   public String getTag(){
       return tag;
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


}
