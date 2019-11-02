package sample.model;

import javafx.scene.canvas.Canvas;

public class Collider extends Component {

    private int heightOffset = 0;
    private int widthOffset = 0;
    private boolean isEnabled = false;
    public Collider(CustomShape customShape){
      super(customShape);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public CustomShape getCustomShape(){
        return customShape;
    }

    public double getX(){
        return customShape.getX();
    }
    public double getY(){
        return customShape.getY();
    }
    public double getHeight(){
        return customShape.getHeight();
    }
    public double getWidth(){
        return customShape.getWidth();
    }

    @Override
    public void causeEffect() {
        //todo
        //nothing to write?
    }

    public Collider(CustomShape customShape,int heightOffset,int widthOffset){
        super(customShape);
        this.heightOffset = heightOffset;
        this.widthOffset = widthOffset;
    }

}
