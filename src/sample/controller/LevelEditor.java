package sample.controller;

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
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.model.*;

import java.io.File;
import java.io.IOException;
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
    @FXML CheckBox playerCheckbox;
    @FXML Button browseImageButton;
    @FXML Button deleteObjectButton;
    @FXML ComboBox tagComboBox;

    private Shape currentShapeSelected;
    private GraphicsContext graphicsContext;
    // private CollisionDetection collisionDetection;
    private ArrayList<CustomShape> objectsOnCanvas = new ArrayList<>();
    private CustomShape currentShape;
    private boolean playerExists = false;
    private static double offsetCanvasX;
    private static double offsetCanvasY;
    private Stage stage;

    public void startEditor(Stage stage) {
        this.stage = stage;
        getCanvasOffset();
        graphicsContext = canvas.getGraphicsContext2D();
        initializeInspector();
        updateCanvas();

    }

    private void getCanvasOffset() {
        offsetCanvasX = canvas.getLayoutX();
        offsetCanvasY = canvas.getLayoutY();
    }

    private void startGameScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../view/game.fxml").openStream());
        Stage stage = new Stage();
        stage.setTitle("Andrei's game engine");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 750, 750));
        stage.show();
        ((Game) fxmlLoader.getController()).startGame(objectsOnCanvas);
        ((Node) event.getSource()).getScene().getWindow().hide();


    }

    ArrayList<CustomShape> getObjectsOnCanvas() {
        return objectsOnCanvas;
    }


    private void initializeObjectSelector() {
        setListenerOnObjectSelected(squareObject);
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
                customSquare.setNewCenter( event.getSceneX() - offsetCanvasX,event.getSceneY() - offsetCanvasY);
                objectsOnCanvas.add(customSquare);
            }
            //return to default position
            squareObject.setX(20);
            squareObject.setY(20);

        });
    }



    private boolean isObjectWithinCanvas(double x, double y) {
        return x > offsetCanvasX && y < offsetCanvasY + canvas.getHeight()
                && x < offsetCanvasX + canvas.getWidth();
    }

    private void setListenerOnObjectSelected(Shape shape) {
        shape.setOnMouseClicked(event -> {
            if (currentShapeSelected != null) {
                currentShapeSelected.setStrokeWidth(0);
            }
            currentShapeSelected = shape;
            currentShapeSelected.setStrokeWidth(2.0);
            shape.setStroke(Color.YELLOW);
        });
    }

    private void updateCanvas() {
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
       tagComboBox.getItems().add("None");
       tagComboBox.getItems().add("Collectable");
       tagComboBox.getItems().add("Enemy");
       tagComboBox.getItems().add("Checkpoint");
       tagComboBox.setValue("None");

    }

    public void changeTag(){
        if(currentShape!=null){
            currentShape.setTag(tagComboBox.getValue().toString());
        }
    }

    private void updateGraphicsContextBackground() {
        graphicsContext.setFill(Color.LIGHTCYAN);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    void inspectObject(CustomShape customShape) {
        currentShape = customShape;
        updateFields();
    }

    private void updateFields() {
        if (currentShape != null) {
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
        playerCheckbox.setSelected(false);
        tagComboBox.setValue("None");
        toggleButtons(true);
    }

    private void getShapeValues() {
        xTextField.setText(currentShape.getX() + "");
        yTextField.setText(currentShape.getY() + "");
        widthTextField.setText(currentShape.getWidth() + "");
        heightTextField.setText(currentShape.getHeight() + "");
        colorTextField.setText(currentShape.getColorCode());
        objectName.setText(currentShape.getName());
        gravityCheckbox.setSelected(((RigidBody) currentShape.getComponent(RigidBody.class.getSimpleName())).hasGravity());
        colliderCheckbox.setSelected(((Collider) currentShape.getComponent(Collider.class.getSimpleName())).isEnabled());
        playerCheckbox.setSelected(currentShape.hasComponent(Player.class.getSimpleName()));
        tagComboBox.setValue(currentShape.getTag());
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
            if (currentShape == null) return;
            currentShape.setName(newValue);

        });

        xTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isStringDouble(newValue)) {
                if (currentShape == null) return;
                currentShape.setX(Double.parseDouble(newValue));
            }
        });

        yTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShape == null) return;
            if (isStringDouble(newValue)) {
                currentShape.setY(Double.parseDouble(newValue));
            }
        });
        widthTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShape == null) return;
            if (isStringDouble(newValue)) {
                currentShape.setWidth(Double.parseDouble(newValue));
            }
        });
        heightTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShape == null) return;
            if (isStringDouble(newValue)) {
                currentShape.setHeight(Double.parseDouble(newValue));
            }
        });
        colorTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShape == null) return;
            try {
                Color color = Color.web(newValue);
                currentShape.setColorCode(newValue);
            } catch (Exception e) {
                System.out.println("Not a color hex code");
            }
        });


        gravityCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    if (currentShape == null) return;
                    ((RigidBody) currentShape.getComponent(RigidBody.class.getSimpleName())).setHasGravity(newValue);
                }
        );
        colliderCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShape == null) return;
            ((Collider) currentShape.getComponent(Collider.class.getSimpleName())).setEnabled(newValue);
        });

        playerCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShape == null) return;
            if (newValue && !playerExists) {
                currentShape.addComponent(new Player(currentShape));
            } else {
                if (!newValue && !playerExists) {
                    currentShape.removeComponent(Player.class.getSimpleName());
                } else {
                    System.out.println("Mate you already have a player");
                }
            }
        });

    }

    public void openImageExplorer() {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter= new FileChooser.ExtensionFilter("jpg files", "*.jpg");
            fileChooser.getExtensionFilters().add(extensionFilter);
            fileChooser.setTitle("Select an image");
            File  file = fileChooser.showOpenDialog(stage);
            if(file!=null){
                Image image = new Image(file.toURI().toString());
                ((CustomSquare) currentShape).setImage(image);
            }


    }

    public void deleteCurrentObject(){
       objectsOnCanvas.remove(currentShape);
       currentShape = null;
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


}

