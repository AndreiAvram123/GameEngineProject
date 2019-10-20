package sample.controller;

import javafx.scene.canvas.Canvas;
import sample.model.CustomSquare;
import sample.model.CustomShape;

import java.util.ArrayList;

public class Inspector {
    private LevelEditor levelEditor;
    private Canvas canvas;
    private ArrayList<CustomShape> objectsOnCanvas;

    public Inspector(Canvas canvas, LevelEditor levelEditor){
       this.canvas = canvas;
       this.levelEditor = levelEditor;
       this.objectsOnCanvas = levelEditor.getObjectsOnCanvas();
    }
    public void start(){
        setListener();
    }
    private void setListener(){
        canvas.setOnMouseClicked(event -> {
            CustomShape objectClicked = getObjectClicked(event.getSceneX() -225,
                    event.getSceneY()-50);
            levelEditor.inspectObject(objectClicked);
        });

    }

    private void test() {
        CustomSquare customSquare = new CustomSquare(canvas.getGraphicsContext2D(),"Rectangle",0,0);
        objectsOnCanvas.add(customSquare);
    }

    public CustomShape getObjectClicked(double x, double y) {
        for(CustomShape shape: objectsOnCanvas){
            if(isShapeWithinRange(shape,x,y)){
                return  shape;
            }
        }

        return null;
    }
    private boolean isShapeWithinRange(CustomShape rectangle, double x, double y) {
        if(rectangle instanceof CustomSquare){
            CustomSquare customSquare = (CustomSquare)rectangle;
            return (customSquare.getX() <= x && customSquare.getY() <= y)
                    && (customSquare.getHeight() + rectangle.getY() >= y && customSquare.getWidth() + rectangle.getX() >= x);

        }
        return true;
    }
    protected ArrayList<CustomShape> getObjectsOnCanvas(){
        return objectsOnCanvas;
    }
}
