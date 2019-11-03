package sample.model;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class CustomShape extends Node{

    GraphicsContext graphicsContext;
    String colorCode= "#e9ff42";
    TAGS tag = TAGS.NONE;
    private String name;
    private Point point;
    private  double height =100;
    private double width = 100;
    private ArrayList<Component> components= new ArrayList<>();;


    private void addDefaultComponents(){
        addComponent(new RigidBody(this));
        addComponent(new Collider(this));
    }
    public void setNewCenter(double x, double y){
        point.setX(x - width/2);
        point.setY(y - height/2);
    }


    public CustomShape(GraphicsContext graphicsContext,String name,double x,double y){
        this.name = name;
        this.graphicsContext = graphicsContext;
        point = new Point(x,y);
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

    public void setX(double x){
        point.setX(x);
    }

    public void setY(double y){
        point.setY(y);
    }
    public double getY(){
        return point.getY();
    }
    public double getX(){
        return point.getX();
    }


   public void setTag(TAGS tag){
       this.tag = tag;
   }
   public TAGS getTag(){
       return tag;
   }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public boolean isShapeWithinRange(double x, double y) {
            return (point.getX() <= x && point.getY() <= y)
                    && (getHeight() + point.getY() >= y && getWidth() + point.getX() >= x);

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

}
