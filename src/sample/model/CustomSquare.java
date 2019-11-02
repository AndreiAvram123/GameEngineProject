package sample.model;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CustomSquare extends CustomShape  {


    public CustomSquare(GraphicsContext graphicsContext, String name, double x, double y) {
        super(graphicsContext, name,x,y);

    }


    @Override
     void drawShape() {
      graphicsContext.setLineWidth(2.0);
      graphicsContext.setFill(Color.web(colorCode));
      graphicsContext.fillRect(x,y,width,height);
    }


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
