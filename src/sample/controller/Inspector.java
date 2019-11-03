package sample.controller;

import javafx.scene.canvas.Canvas;
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
        addListenerToCanvas();
    }

    private void setListener(){
        canvas.setOnMouseClicked(event -> {
            CustomShape objectClicked = getObjectClicked(event.getSceneX() -225,
                    event.getSceneY()-50);
            levelEditor.inspectObject(objectClicked);
        });

    }

    private void addListenerToCanvas() {
        canvas.setOnMouseDragged(event -> {
            for(CustomShape customShape: objectsOnCanvas){
                if(customShape.isShapeWithinRange(event.getX(),event.getY())){
                    customShape.setNewCenter(event.getX(),event.getY());
                }
            }
        });
    }
    public CustomShape getObjectClicked(double x, double y) {
        for(CustomShape shape: objectsOnCanvas){
            if(shape.isShapeWithinRange(x,y)){
                return  shape;
            }
        }

        return null;
    }

}
