package sample.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class CustomSquare extends CustomShape  {


    public CustomSquare(GraphicsContext graphicsContext, String name, int x, int y) {
        super(graphicsContext, name,x,y);
        addComponent(new RigidBody(this));
    }
    public CustomSquare(GraphicsContext graphicsContext, String name, int x, int y, int width, int height){
        super(graphicsContext,name,x,y,width,height);
        addComponent(new RigidBody(this));
    }


    @Override
     void drawShape() {
      graphicsContext.setLineWidth(2.0);
      graphicsContext.setFill(Color.RED);
      graphicsContext.fillRect(x,y,width,height);
    }


}
