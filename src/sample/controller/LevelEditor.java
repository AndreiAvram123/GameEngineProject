package sample.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import sample.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class LevelEditor {
    @FXML
    private Button playButton;
    @FXML
    private Rectangle squareObject;
    @FXML
    private TextField objectName;
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
    @FXML
    CheckBox colliderCheckbox;
    @FXML
    CheckBox playerCheckbox;

    private Shape currentShapeSelected;
    private GraphicsContext graphicsContext;
    private Inspector inspector;
    private CollisionDetection collisionDetection;
    private ArrayList<CustomShape> objectsOnCanvas;
    private CustomShape currentShape;
    private boolean playerExists = false;
    private static double offsetCanvasX;
    private static double offsetCanvasY;

    public void startEditor() {
        offsetCanvasX = canvas.getLayoutX();
        offsetCanvasY = canvas.getLayoutY();
        graphicsContext = canvas.getGraphicsContext2D();
        objectsOnCanvas = new ArrayList<>();
        collisionDetection = new CollisionDetection();
        initializeInspector();
        initializeObjectSelector();
        updateCanvas();
        attachListenerToPlayButton();
    }

    private void attachListenerToPlayButton() {

        playButton.setOnAction(event -> {
            try {
                startGameScene(event);

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    private void startGameScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("../view/game.fxml").openStream());
        Stage stage = new Stage();
        stage.setTitle("Andrei's game engine");
        stage.setResizable(false);
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
        ((Game) fxmlLoader.getController()).addElementsOnScreen(objectsOnCanvas);
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
            squareObject.setX(event.getX() - CustomShape.getDefaultWidth() / 2);
            squareObject.setY(event.getY() - CustomShape.getDefaultHeight() / 2);
        });

    }

    private void setActionOnMouseReleased() {
        squareObject.setOnMouseReleased(event -> {
            if (isObjectWithinCanvas(event.getSceneX(), event.getSceneY())) {
                CustomSquare customSquare = new CustomSquare(graphicsContext, "Square", event.getSceneX() - CustomSquare.getDefaultWidth() / 2 - offsetCanvasX,
                        event.getSceneY() - CustomSquare.getDefaultHeight() / 2 - offsetCanvasY);
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
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                collisionDetection.checkCollision();
                updateGraphicsContextBackground();
                for (CustomShape customShape : objectsOnCanvas) {
                    customShape.update();
                }

            }
        }, 0, 100);
    }

    private void initializeInspector() {
        inspector = new Inspector(canvas, this);
        inspector.start();
        attachListenerToFields();
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
            xTextField.setText(currentShape.getX() + "");
            yTextField.setText(currentShape.getY() + "");
            widthTextField.setText(currentShape.getWidth() + "");
            heightTextField.setText(currentShape.getHeight() + "");
            objectName.setText(currentShape.getName());
            gravityCheckbox.setSelected(((RigidBody)currentShape.getComponent(RigidBody.class.getSimpleName())).hasGravity());
            colliderCheckbox.setSelected(((Collider)currentShape.getComponent(Collider.class.getSimpleName())).isEnabled());
            playerCheckbox.setSelected(currentShape.hasComponent(Player.class.getSimpleName()));
        }
    }

    private void attachListenerToFields() {

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
        gravityCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShape == null) return;
            ((RigidBody)currentShape.getComponent(RigidBody.class.getSimpleName())).setHasGravity(newValue);
        }
        );
        colliderCheckbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (currentShape == null) return;
            ((Collider)currentShape.getComponent(Collider.class.getSimpleName())).setEnabled(newValue);
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


    private boolean isStringDouble(String string) {
        if (!string.trim().isEmpty()) {
            try {
                double x = Double.parseDouble(string);
                return true;
            } catch (NumberFormatException e) {
                System.out.println("NOT A DOUBLE");
            }
        }
        return false;
    }


}

