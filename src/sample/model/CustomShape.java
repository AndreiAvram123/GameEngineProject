package sample.model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public abstract class CustomShape{

    private String name;
    GraphicsContext graphicsContext;
    int x;
    int y;
    int height =100;
    int width = 100;
    private ArrayList<Component> components;


    public CustomShape(GraphicsContext graphicsContext,String name,int x,int y){
        this.name = name;
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
        components = new ArrayList<>();
    }
    CustomShape(GraphicsContext graphicsContext, String name, int x, int y, int width, int height){
        this.name = name;
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        components = new ArrayList<>();
    }

    void addComponent(Component component){
      components.add(component);
    }

    public RigidBody getRigidBody(){
        for(Component component: components){
            if(component instanceof RigidBody){
                return (RigidBody)component;
            }
        }
        return null;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void update(){
          drawShape();
          produceComponentsEffect();
        //        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//               //todo
//                //implement gravity
//            }
//        },0,100);
    }

    private void produceComponentsEffect() {
        for(Component component: components){
            component.causeEffect();
        }
    }

    abstract void drawShape();

}
