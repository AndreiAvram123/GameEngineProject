package sample.model;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class CustomRectangle extends CustomShape  {


    public CustomRectangle(GraphicsContext graphicsContext, String name, int x, int y) {
        super(graphicsContext, name,x,y);
    }
    public CustomRectangle(GraphicsContext graphicsContext, String name,int x,int y,int width,int height){
        super(graphicsContext,name,x,y,width,height);

    }


    @Override
    public void drawShape() {
      graphicsContext.setLineWidth(2.0);
      graphicsContext.setFill(Color.RED);
      graphicsContext.fillRect(x,y,width,height);
    }


}
