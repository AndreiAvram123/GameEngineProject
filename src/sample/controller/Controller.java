package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import sample.model.CustomRectangle;
import sample.model.CustomShape;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

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

    private ArrayList<CustomShape> objectsOnCanvas = new ArrayList<>();

     public void startInterface(){
         graphicsContext = canvas.getGraphicsContext2D();
         initializeInspector();
         updateCanvas();
     }

    private void updateCanvas(){
       Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateGraphicsContextBackground();
                for(CustomShape customShape : objectsOnCanvas){
                    customShape.drawShape();
                }
            }
        },0,500);
    }

    private void initializeInspector(){
        setListener();
        test();

    }

    private void test() {
        CustomRectangle customRectangle = new CustomRectangle(graphicsContext,"Rectangle",0,0);
        customRectangle.drawShape();
        objectsOnCanvas.add(customRectangle);
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

    private void inspectObject(CustomShape customShape) {
        if(customShape!=null) {
            xTextField.setText(customShape.getX() + "");
            yTextField.setText(customShape.getY() + "");
            widthTextField.setText(customShape.getWidth()+"");
            heightTextField.setText(customShape.getHeight()+"");
            objectName.setText(customShape.getName());
            attachListenerToFields(customShape);
        }else{
            emptyInspectorValues();
        }

//
//
//
//        gravityCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                customShape.activateGravity();
//            } else {
//                customShape.disableGravity();
//            }
//        });
    }

    private void attachListenerToFields(CustomShape customShape) {
        xTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(isStringInteger(newValue)) {
                customShape.setX(Integer.parseInt(newValue));
            }
        });

        yTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(isStringInteger(newValue)) {
                customShape.setY(Integer.parseInt(newValue));
            }
       });
        widthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(isStringInteger(newValue)) {
                customShape.setWidth(Integer.parseInt(newValue));
            }
        });
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(isStringInteger(newValue)) {
                customShape.setHeight(Integer.parseInt(newValue));
            }
        });


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
        objectName.setText("");
    }

    private void setListener(){
            canvas.setOnMouseClicked(event -> {
                CustomShape objectClicked = getObjectClicked(event.getSceneX() -225,
                        event.getSceneY()-50);
                    inspectObject(objectClicked);
            });
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
        if(rectangle instanceof CustomRectangle){
            CustomRectangle customRectangle = (CustomRectangle)rectangle;
            return (customRectangle.getX() <= x && customRectangle.getY() <= y)
                   && (customRectangle.getHeight() + rectangle.getY() >= y && customRectangle.getWidth() + rectangle.getX() >= x);

        }
        return true;
       }




}

