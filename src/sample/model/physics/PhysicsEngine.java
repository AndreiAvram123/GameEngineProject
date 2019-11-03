package sample.model.physics;

import sample.model.enums.SIDES;
import sample.model.enums.TAGS;
import sample.model.shapes.CustomSquare;

import javax.print.attribute.standard.Sides;
import javax.swing.*;
import java.util.ArrayList;

public class PhysicsEngine {

   private ArrayList<CustomSquare> objectsOnCanvas;
   private ArrayList<BoxCollider> colliders;

   public  PhysicsEngine(ArrayList<CustomSquare> objectsOnCanvas){
       this.objectsOnCanvas = objectsOnCanvas;
       configurePhysicsEngine();
   }
   public void update(){
       
       checkObjectsCollision();
   }

    private void configurePhysicsEngine() {
       colliders = new ArrayList<>();
        for(CustomSquare customSquare : objectsOnCanvas){
            if(customSquare.hasComponent(BoxCollider.class.getName())){
                colliders.add((BoxCollider)customSquare.getComponent(BoxCollider.class.getName()));
            }
        }
    }

   public void checkObjectsCollision(){
       if(colliders.size()>1) {
           for (int i = 0; i < colliders.size() - 1; i++) {
               SIDES side = colliders.get(i).getCollisionSide(colliders.get(i+1));
               if(side!= SIDES.NONE){
                  processCollision(colliders.get(i),colliders.get(i+1));     
               }
           }
       }
   }
   private void processCollision(BoxCollider boxCollider1, BoxCollider boxCollider2){
       if(boxCollider1.getShape().getTag() == TAGS.PLAYER)
       {
           if(boxCollider2.getShape().getTag() == TAGS.COLLECTABLE){
               objectsOnCanvas.remove(boxCollider2.getShape());
           }

       }else {
           if (boxCollider2.getShape().getTag() == TAGS.PLAYER) {
               if (boxCollider1.getShape().getTag() == TAGS.COLLECTABLE) {
                   objectsOnCanvas.remove(boxCollider1.getShape());
               }
           }
       }
   }

}
