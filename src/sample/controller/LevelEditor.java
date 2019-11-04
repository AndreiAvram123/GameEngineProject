package sample.controller;

import com.google.gson.Gson;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.database.DataManager;
import sample.model.*;
import sample.model.enums.TAGS;
import sample.model.physics.BoxCollider;
import sample.model.physics.PhysicsEngine;
import sample.model.shapes.CustomShape;
import sample.model.shapes.CustomSquare;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class LevelEditor {

    @FXML private Button playButton;
    @FXML private Rectangle squareObject;
    @FXML private TextField objectName;
    @FXML private Canvas canvas;
    @FXML private TextField xTextField;
    @FXML private TextField yTextField;
    @FXML private TextField widthTextField;
    @FXML private TextField heightTextField;
    @FXML TextField colorTextField;
    @FXML CheckBox gravityCheckbox;
    @FXML CheckBox colliderCheckbox;
    @FXML Button browseImageButton;
    @FXML Button deleteObjectButton;
    @FXML ComboBox tagComboBox;

    private GraphicsContext graphicsContext;
    private ArrayList<CustomSquare> objectsOnCanvas = new ArrayList<>();
    private CustomSquare currentShapeSelected;
    private boolean playerExists = false;
    private Point canvasInitialPoint;
    private Point squareInitialPoint;
    private Stage stage;
    DataManager dataManager;


    public void startEditor(Stage stage) {
        this.stage = stage;
        dataManager = new DataManager(stage);
        getCanvasInitialPoint();
        getSquareInitialPoint();
        graphicsContext = canvas.getGraphicsContext2D();
        initializeInspector();
        update();

    }

    private void getCanvasInitialPoint() {
        canvasInitialPoint = new Point(canvas.getLayoutX(),canvas.getLayoutY());
    }
    private void getSquareInitialPoint(){
        squareInitialPoint = new Point(squareObject.getX(),squareObject.getY());
    }

    private void startGameScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../view/game.fxml").openStream());
        Stage stage = new Stage();
        stage.setTitle("Andrei's game engine");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 750, 750));
        stage.show();
        toggleObjectSelected(false);
        ((Game) fxmlLoader.getController()).startGame(objectsOnCanvas);
        ((Node) event.getSource()).getScene().getWindow().hide();


    }

    ArrayList<CustomSquare> getObjectsOnCanvas() {
         return objectsOnCanvas;

    }


    private void initializeObjectSelector() {
        setListenerOnMouseDragged();
        setActionOnMouseReleased();


    }

    private void setListenerOnMouseDragged() {
        squareObject.setOnMouseDragged(event -> {
            squareObject.setX(event.getX() - squareObject.getWidth()/ 2);
            squareObject.setY(event.getY() - squareObject.getHeight()/ 2);
        });

    }

    private void setActionOnMouseReleased() {
        squareObject.setOnMouseReleased(event -> {
            if (isObjectWithinCanvas(event.getSceneX(), event.getSceneY())) {
                CustomSquare customSquare = new CustomSquare(graphicsContext, "Square", 0,0);
                customSquare.setNewCenter( event.getSceneX() - canvasInitialPoint.getX(),event.getSceneY() - canvasInitialPoint.getY());
                objectsOnCanvas.add(customSquare);
            }
            //return to default position
            squareObject.setX(squareInitialPoint.getX());
            squareObject.setY(squareInitialPoint.getY());

        });
    }

    private boolean isObjectWithinCanvas(double x, double y) {
        return x > canvasInitialPoint.getX() && y < canvasInitialPoint.getY() + canvas.getHeight()
                && x < canvasInitialPoint.getX() + canvas.getWidth();
    }

    private void update() {
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateGraphicsContextBackground();
                for (CustomShape customShape : objectsOnCanvas) {
                    customShape.update();
                }
            }
        };
        animationTimer.start();
    }

    private void initializeInspector() {
        Inspector inspector = new Inspector(canvas, this);
        inspector.start();
        attachListenerToFields();
        toggleButtons(true);
        initializeObjectSelector();
        addItemsToComboBox();

    }

    private void addItemsToComboBox() {
       tagComboBox.getItems().addAll(TAGS.NONE.getName(),
               TAGS.PLAYER.getName(),
               TAGS.COLLECTABLE.getName(),
               TAGS.ENEMY.getName(),
               TAGS.CHECKPOINT.getName());

        tagComboBox.setValue(TAGS.NONE.getName());
    }

    public void changeTag(){
        if(currentShapeSelected !=null){
            if(TAGS.getObjectByName(tagComboBox.getValue().toString())== TAGS.PLAYER){
                setPlayerTag();
            }else{
                currentShapeSelected.setTag(TAGS.getObjectByName(tagComboBox.getValue().toString()));
            }
        }
    }

    private void setPlayerTag() {
        if(!playerExists){
            currentShapeSelected.setTag(TAGS.PLAYER);
            playerExists = true;
        }
    }

    private void updateGraphicsContextBackground() {
        graphicsContext.setFill(Color.LIGHTCYAN);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    void inspectObject(CustomSquare customSquare) {
        toggleObjectSelected(false);
        currentShapeSelected = customSquare;
        toggleObjectSelected(true);
        updateFields();
    }

    private void toggleObjectSelected(boolean value) {
        if(currentShapeSelected !=null){
            currentShapeSelected.setSelectedInEditor(value);
        }
    }

    private void updateFields() {
        if (currentShapeSelected != null) {
            getShapeValues();
        }else{
            emptyFields();
        }
    }

    private void emptyFields()  {
        xTextField.setText("");
        yTextField.setText("");
        widthTextField.setText("");
        heightTextField.setText("");
        colorTextField.setText("");
        objectName.setText("");
        gravityCheckbox.setSelected(false);
        colliderCheckbox.setSelected(false);
        tagComboBox.setValue(TAGS.NONE.getName());
        toggleButtons(true);
    }

    private void getShapeValues() {
        xTextField.setText(currentShapeSelected.getTopLeft().getX()+ "");
        yTextField.setText(currentShapeSelected.getTopLeft().getY() + "");
        widthTextField.setText(currentShapeSelected.getWidth() + "");
        heightTextField.setText(currentShapeSelected.getHeight() + "");
        colorTextField.setText(currentShapeSelected.getColorCode());
        objectName.setText(currentShapeSelected.getName());
        gravityCheckbox.setSelected(currentShapeSelected.hasComponent(RigidBody.class.getName()));
        colliderCheckbox.setSelected(currentShapeSelected.hasComponent(BoxCollider.class.getName()));
        tagComboBox.setValue(currentShapeSelected.getTag().toString());
        toggleButtons(false);
    }

    private void toggleButtons(boolean b) {
        browseImageButton.setDisable(b);
        deleteObjectButton.setDisable(b);
    }

    private void attachListenerToFields() {
        playButton.setOnAction(event -> {
            try {
                startGameScene(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        objectName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShapeSelected == null) return;
            currentShapeSelected.setName(newValue);

        });

        xTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isStringDouble(newValue)) {
                if (currentShapeSelected == null) return;
                currentShapeSelected.getTopLeft().setX(Double.parseDouble(newValue));
            }
        });

        yTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShapeSelected == null) return;
            if (isStringDouble(newValue)) {
                currentShapeSelected.getTopLeft().setY(Double.parseDouble(newValue));
            }
        });
        widthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShapeSelected == null) return;
            if (isStringDouble(newValue)) {
                currentShapeSelected.setWidth(Double.parseDouble(newValue));
            }
        });
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShapeSelected == null) return;
            if (isStringDouble(newValue)) {
                currentShapeSelected.setHeight(Double.parseDouble(newValue));
            }
        });
        colorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShapeSelected == null) return;
            try {
                Color color = Color.web(newValue);
                currentShapeSelected.setColorCode(newValue);
            } catch (Exception e) {
                System.out.println("Not a color hex code");
            }
        });


        gravityCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (currentShapeSelected == null) return;
                    if(newValue){
                        if(!currentShapeSelected.hasComponent(RigidBody.class.getName())) {
                            currentShapeSelected.addComponent(new RigidBody(currentShapeSelected));
                        }
                    }else{
                        currentShapeSelected.removeComponent(RigidBody.class.getName());
                    }
                }
        );
        colliderCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShapeSelected == null) return;
            if(newValue){
                if(!currentShapeSelected.hasComponent(BoxCollider.class.getName())) {
                    currentShapeSelected.addComponent(new BoxCollider(currentShapeSelected));
                }
            }else{
                currentShapeSelected.removeComponent(BoxCollider.class.getName());
            }
        });

    }

    public void openImageExplorer() {
           File file = dataManager.getFileFromOpenDialog();
            if(file!=null){
                Image image = new Image(file.toURI().toString());
                currentShapeSelected.setImage(image);
            }


    }

    public void deleteCurrentObject(){
       objectsOnCanvas.remove(currentShapeSelected);
       currentShapeSelected = null;
       updateFields();
    }

    private boolean isStringDouble(String string) {
        if (!string.trim().isEmpty()) {
            try {
                double x = Double.parseDouble(string);
                return true;
            } catch (NumberFormatException ignored) {
            }
        }
        return false;
    }

    @FXML
    private void saveLevel(){
        dataManager.saveObjects(objectsOnCanvas);

    }
    @FXML
    private void loadSaveFile(){
        dataManager.getSaveData();
    }


}

