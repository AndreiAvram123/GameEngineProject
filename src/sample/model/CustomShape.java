package sample.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Timer;
import java.util.TimerTask;

public abstract class CustomShape{

    private String name;
    protected GraphicsContext graphicsContext;
    protected int x;
    protected int y;
    protected int height =100;
    protected int width = 100;

    public CustomShape(GraphicsContext graphicsContext,String name,int x,int y){
        this.name = name;
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
    }
    public CustomShape(GraphicsContext graphicsContext,String name,int x,int y,int width,int height){
        this.name = name;
        this.graphicsContext = graphicsContext;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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


    private void update(){
          drawShape();
        //        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//               //todo
//                //implement gravity
//            }
//        },0,100);
    }

    public abstract void drawShape();

}
