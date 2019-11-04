package sample.model.shapes;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import sample.model.Component;
import sample.model.enums.TAGS;

import java.util.ArrayList;

public abstract class CustomShape extends Node {

    GraphicsContext graphicsContext;
    String colorCode = "#e9ff42";
    TAGS tag = TAGS.NONE;
    private String name;
    private double height = 50;
    private double width = 50;
    private ArrayList<Component> componentsEnabled = new ArrayList<>();
    private boolean isInEditor = true;
     boolean selectedInEditor = false;
     protected int lastSpeedX = 0;
     protected int lastSpeedY =0;


    public CustomShape(GraphicsContext graphicsContext, String name) {
        this.name = name;
        this.graphicsContext = graphicsContext;
    }

    public void addComponent(Component component) {
        componentsEnabled.add(component);
        System.out.println(componentsEnabled.size());
    }


    public void removeComponent(String className) {
        Component toRemove = null;
        for (Component component : componentsEnabled) {
            if (component.getClass().getName().equals(className)) {
                toRemove = component;
            }
        }
        componentsEnabled.remove(toRemove);
    }

        public Component getComponent (String className){
            for (Component component : componentsEnabled) {
                if (component.getClass().getName().equals(className)) {
                    return component;
                }
            }
            return null;
        }
        public boolean hasComponent (String className){
            for (Component component : componentsEnabled) {
                if (component.getClass().getName().equals(className)) {
                    return true;
                }
            }
            return false;
        }


        public void updateGraphicsContext (GraphicsContext graphicsContext){
            this.graphicsContext = graphicsContext;
        }


        public void update () {
            drawShape();
             if(!isInEditor) {
                 produceComponentsEffect();
             }
        }

        private void produceComponentsEffect () {
            for (Component component : componentsEnabled) {
                component.causeEffect();
            }
        }

        public  void setInEditor(boolean isInEditor){
          this.isInEditor = isInEditor;
        }

        abstract void drawShape ();
        abstract void setNewCenter ( double x, double y);
        abstract boolean isShapeWithinRange ( double x, double y);

        public void setTag (TAGS tag){
            this.tag = tag;
        }
        public TAGS getTag () {
            return tag;
        }

        public String getColorCode () {
            return colorCode;
        }

        public void setColorCode (String colorCode){
            this.colorCode = colorCode;
        }


        public double getHeight () {
            return height;
        }

        public double getWidth () {
            return width;
        }

    public void setSelectedInEditor(boolean selectedInEditor) {
        this.selectedInEditor = selectedInEditor;
    }

    public void setHeight (double height){
            this.height = height;
        }

        public void setWidth ( double width){
            this.width = width;
        }

        public String getName () {
            return name;
        }

        public void setName (String name){
            this.name = name;
        }

    public int getLastSpeedX() {
        return lastSpeedX;
    }

    public int getLastSpeedY() {
        return lastSpeedY;
    }

    public void setIsMovingOnX(int speed){
           lastSpeedX = speed;
           lastSpeedY =0;
    }
    public void setIsMovingOnY(int speed){
        lastSpeedY = speed;
        lastSpeedX =0;
    }



}
