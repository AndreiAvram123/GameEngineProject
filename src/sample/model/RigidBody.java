package sample.model;

import sample.model.shapes.CustomSquare;

public class RigidBody extends Component {

   private double gravityForce =9.81;
   private double mass = 1.0;


    public RigidBody(CustomSquare customSquare) {
        super(customSquare);
    }

    @Override
    public void causeEffect() {
            causeGravity();
        }

    private void causeGravity() {
      customSquare.getTopLeft().setY(customSquare.getTopLeft().getY() + gravityForce*mass);
   }
}
