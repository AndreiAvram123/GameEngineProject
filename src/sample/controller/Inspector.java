package sample.controller;

import javafx.scene.canvas.Canvas;
import sample.model.shapes.CustomShape;
import sample.model.shapes.CustomSquare;

import java.util.ArrayList;

public class Inspector {

    private LevelEditor levelEditor;
    private Canvas canvas;
    private ArrayList<CustomSquare> objectsOnCanvas;

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
            CustomSquare objectClicked = getObjectClicked(event.getSceneX() -canvas.getLayoutX(),
                    event.getSceneY()-canvas.getLayoutY());
            levelEditor.inspectObject(objectClicked);
        });

    }

    private void addListenerToCanvas() {
        canvas.setOnMouseDragged(event -> {
            for(CustomSquare customSquare: objectsOnCanvas){
                if(customSquare.isShapeWithinRange(event.getX(),event.getY())){
                    customSquare.setNewCenter(event.getX(),event.getY());
                }
            }
        });
    }
    public CustomSquare getObjectClicked(double x, double y) {
        for(CustomSquare customSquare: objectsOnCanvas){
            if(customSquare.isShapeWithinRange(x,y)){
                return  customSquare;
            }
        }

        return null;
    }

}
