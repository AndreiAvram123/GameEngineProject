package sample.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class CustomSquare extends CustomShape  {


    public CustomSquare(GraphicsContext graphicsContext, String name, double x, double y) {
        super(graphicsContext, name,x,y);

    }


    @Override
     void drawShape() {
      graphicsContext.setLineWidth(2.0);
      graphicsContext.setFill(Color.BLUE);
      graphicsContext.fillRect(x,y,width,height);
    }



}
