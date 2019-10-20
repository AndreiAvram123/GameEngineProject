package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.model.CustomSquare;
import sample.model.CustomShape;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LevelEditor {
    @FXML
    private Rectangle rectangleObject;
    @FXML
    private Rectangle squareObject;

    @FXML
    private Pane objectSelector;
    @FXML
    private Label objectName;
    @FXML
    private Canvas canvas;
    @FXML
    private
    TextField xTextField;
    @FXML
    private
    TextField yTextField;
    @FXML
    private
    TextField widthTextField;
    @FXML
    private
    TextField heightTextField;
    @FXML
    CheckBox gravityCheckbox;

    private GraphicsContext graphicsContext;
    private Inspector inspector;
    private ArrayList<CustomShape> objectsOnCanvas;
   private CustomShape currentShape;

     public void startEditor(){
         graphicsContext =canvas.getGraphicsContext2D();
         objectsOnCanvas = new ArrayList<>();
         initializeInspector();
         initializeObjectSelector();
         updateCanvas();
     }

     ArrayList<CustomShape> getObjectsOnCanvas(){
         return objectsOnCanvas;
     }

    private void initializeObjectSelector() {
       squareObject.setOnMouseClicked(event -> {rectangleObject.setStrokeWidth(2.0);
          squareObject.setStroke(Color.YELLOW);
       });
       squareObject.setOnMouseDragged(event -> {
           squareObject.setLayoutX(event.getSceneX()-70);
           squareObject.setLayoutY(event.getSceneY()-70);
       });

       squareObject.setOnMouseReleased(event -> {
           squareObject.setLayoutX(20);
           squareObject.setLayoutY(20);
           CustomSquare customSquare = new CustomSquare(canvas.getGraphicsContext2D(),"Square",
                   (int)(event.getSceneX()-225-50),(int)(event.getSceneY()-50-50));
           objectsOnCanvas.add(customSquare);
       });


        rectangleObject.setOnMouseClicked(event -> {rectangleObject.setStrokeWidth(2.0);
            rectangleObject.setStroke(Color.YELLOW);
        });
        rectangleObject.setOnMouseDragged(event -> {
            rectangleObject.setLayoutX(event.getSceneX()-70);
            rectangleObject.setLayoutY(event.getSceneY()-70);
        });

        rectangleObject.setOnMouseReleased(event -> {
            rectangleObject.setLayoutX(20);
            rectangleObject.setLayoutY(220);
            System.out.println((event.getSceneX()-225) + "," + (event.getSceneY()-50));
            CustomSquare customSquare = new CustomSquare(canvas.getGraphicsContext2D(),"Rectangle",
                    (int)(event.getSceneX()-225-50),(int)(event.getSceneY()-50-50),150,100);
            objectsOnCanvas.add(customSquare);
        });

    }

    private void updateCanvas(){
       Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateGraphicsContextBackground();
                for(CustomShape customShape : objectsOnCanvas){
                    customShape.update();
                }
            }
        },0,100);
    }

    private void initializeInspector(){
        inspector = new Inspector(canvas,this);
        inspector.start();
        attachListenerToFields();
    }




    private void updateGraphicsContextBackground() {
        graphicsContext.setFill(Color.LIGHTCYAN);
        graphicsContext.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }


//    private void checkKeyboardInput() {
//        canvas.getScene().addEventHandler(KeyEvent.KEY_PRESSED,(key)->{
//            if(key.getCode()== KeyCode.A) {
//                ((Movable)getPlayer()).moveLeft();
//            }
//            if(key.getCode()== KeyCode.D) {
//                ((Movable)getPlayer()).moveRight();
//            }
//            if(key.getCode()== KeyCode.W) {
//                (getPlayer()).moveUp();
//            }
//            if(key.getCode()== KeyCode.S) {
//                ((Movable)getPlayer()).moveDown();
//            }
//        });
//    }
//

    void inspectObject(CustomShape customShape) {
         currentShape = customShape;
          emptyInspectorValues();
          updateFields();
    }


    private void updateFields() {
        if(currentShape!=null) {
            xTextField.setText(currentShape.getX() + "");
            yTextField.setText(currentShape.getY() + "");
            widthTextField.setText(currentShape.getWidth() + "");
            heightTextField.setText(currentShape.getHeight() + "");
            objectName.setText(currentShape.getName());
        }
    }

    private void attachListenerToFields() {
        xTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(isStringInteger(newValue)) {
                if(currentShape ==null) return;
                currentShape.setX(Integer.parseInt(newValue));
            }
        });

        yTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(currentShape ==null) return;
            if(isStringInteger(newValue)) {
                currentShape.setY(Integer.parseInt(newValue));
            }
       });
        widthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(currentShape ==null) return;
            if(isStringInteger(newValue)) {
                currentShape.setWidth(Integer.parseInt(newValue));
            }
        });
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(currentShape ==null) return;
            if(isStringInteger(newValue)) {
                currentShape.setHeight(Integer.parseInt(newValue));
            }
        });
        gravityCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> currentShape.getRigidBody().setHasGravity(newValue));


    }
    private boolean isStringInteger(String string){
         if(!string.trim().isEmpty()){
                  try{
                      int x = Integer.parseInt(string);
                      return true;
                  }catch (NumberFormatException e){
                      System.out.println("NOT AN INT");
                  }
         }
       return false;
    }

    private void emptyInspectorValues() {
        xTextField.setText("");
        yTextField.setText("");
        widthTextField.setText("");
        heightTextField.setText("");
        objectName.setText("");
        gravityCheckbox.setSelected(false);

    }









}

