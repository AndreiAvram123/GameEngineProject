package sample.model.shapes;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import sample.model.Point;
import sample.model.enums.TAGS;
import sample.model.shapes.CustomShape;

public class CustomSquare extends CustomShape {
    private Image image;
    private Point topLeft;
    private Point topRight;
    private Point bottomLeft;
    private Point bottomRight;

    public CustomSquare(GraphicsContext graphicsContext, String name, double x, double y) {
        super(graphicsContext, name);
        topLeft = new Point(x,y);
        getPoints();
    }
    private void getPoints() {
        topRight = new Point(topLeft.getX()+getWidth(),
                topLeft.getY());
        bottomLeft = new Point(topLeft.getX(),topLeft.getY()
                +getHeight());
        bottomRight = new Point(topLeft.getX()+getWidth(),topLeft.getY()
                +getHeight());
    }
    @Override
    public void setNewCenter(double x, double y){
        topLeft.setX(x - getWidth()/2);
        topLeft.setY(y - getHeight()/2);
    }
    @Override
    public boolean isShapeWithinRange(double x, double y) {
        return (topLeft.getX() <= x && topLeft.getY() <= y)
                && (getHeight() + topLeft.getY() >= y && getWidth() + topLeft.getX() >= x);

    }
    @Override
     void drawShape() {
        getPoints();
      graphicsContext.setLineWidth(2.0);
      if(image!=null){
          graphicsContext.drawImage(image,topLeft.getX(),topLeft.getY(),getWidth(),getHeight());
      }else{
          graphicsContext.setFill(Color.web(colorCode));
          graphicsContext.fillRect(topLeft.getX(),topLeft.getY(),getWidth(),getHeight());
      }

    }


    public void setImage(Image image){
        this.image = image;
    }

    public Point getTopLeft(){
        return topLeft;
    }
    public Point getTopRight() {
        return topRight;
    }

    public Point getBottomLeft() {
        return bottomLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    //===============================IGNORE ===============================
    @Override
    protected NGNode impl_createPeer() {
        return null;
    }

    @Override
    public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
        return null;
    }

    @Override
    protected boolean impl_computeContains(double localX, double localY) {
        return false;
    }

    @Override
    public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
        return null;
    }
}
