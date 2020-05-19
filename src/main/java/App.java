

import jaengine.core.Engine;
import jaengine.physics.*;
import jaengine.logic.*;
public class App 
{
    public static void main( String[] args )
    {
        Engine gEngine = new Engine();
        Block b = new Block("firstBlock",20, 20);
        Block c = new Block("attach",10,50);
        c.deactivateAttribute("RigidBody");
        b.addChild(c);
        gEngine.startEngine();
        
        gEngine.addToEnvironment(new Block[]{b});
        gEngine.forceDisplace(c, new Vector2D(25,5));
       
        gEngine.forceDisplace(b, new Vector2D(100,400));
        
        b.setPhysics("velocity",new Object[]{new Vector2D(20,-70)});
        b.setPhysics("angular_velocity", new Object[]{new Vector2D(0,3.1415)});

        Block collider = new Block("interceptor",40,40);
        gEngine.addToEnvironment(collider);
        gEngine.forceDisplace(collider, new Vector2D(230, 0));

        try {
            Thread.sleep(2000);
            gEngine.printTree();
        } catch (InterruptedException e) {
            System.out.println("failed");
        }
        // Node<Double> dNode = new Node<Double>(12.0);
        // Node<String> sNode = new Node<String>("STRING");
        // dNode.getChildren().add(sNode);
    }
}
