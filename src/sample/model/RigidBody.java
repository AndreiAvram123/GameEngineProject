package sample.model;

public class RigidBody extends Component {

   private double gravityForce =9.81;
   private double mass = 1.0;
   private boolean hasGravity = false;

    public RigidBody(CustomShape customShape) {
        super(customShape);
    }

    public void setGravityForce(double gravityForce){
        this.gravityForce = gravityForce;
    }

    public void setMass(double mass){
        this.mass = mass;
    }

    public void setHasGravity(boolean hasGravity){
        this.hasGravity  = hasGravity;
    }

    @Override
    public void causeEffect() {
            causeGravity();
        }

    private void causeGravity() {
        if(hasGravity) {
            customShape.setY(customShape.getY() + (int)(gravityForce*mass));
        }
        }
}
