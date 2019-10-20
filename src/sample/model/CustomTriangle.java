package sample.model;

import javafx.scene.canvas.GraphicsContext;

public class CustomTriangle extends CustomShape {

    public CustomTriangle(GraphicsContext graphicsContext, String name, int x, int y) {
        super(graphicsContext, name, x, y);
    }

    CustomTriangle(GraphicsContext graphicsContext, String name, int x, int y, int width, int height) {
        super(graphicsContext, name, x, y, width, height);
    }

    @Override
    void drawShape() {

    }
}
