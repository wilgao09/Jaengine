

import jaengine.core.Engine;
import jaengine.physics.*;
import jaengine.logic.*;
public class App 
{
    public static void main( String[] args )
    {
        Engine gEngine = new Engine();

        //THIS IS A CHECK ON TWO OBJECT SPIN
        Block left = new Block("left", 40, 20);
        Block right = new Block("right", 20, 40);
        Block central = new Block("center", 10, 10);
        left.deactivateAttribute("RigidBody");
        right.deactivateAttribute("RigidBody");
        central.addChild(left);
        central.addChild(right);
        gEngine.startEngine();

        // try {
        //     Thread.sleep(1000);
        // } catch(InterruptedException e) {

        // }
        gEngine.addToEnvironment(central);

        gEngine.addToEnvironment(central);
        gEngine.forceDisplace(central, new Vector2D(100, 400) );
        gEngine.forceDisplace(left, new Vector2D(-100, 0) );
        gEngine.forceDisplace(right, new Vector2D(100, 0));

        central.setPhysics("angular_velocity", new Object[]{new Vector2D(0,3.14)});
        central.setPhysics("velocity", new Object[]{new Vector2D(10,-70)});

        central.checkSelf();


        Block handle = new Block("handle", 80, 20);
        Block head = new Block("head", 20, 60);
        head.setMass(10);
        handle.deactivateAttribute("RigidBody");
        head.addChild(handle);
        gEngine.addToEnvironment(head);
        gEngine.forceDisplace(head, new Vector2D(100,100));
        gEngine.forceDisplace(handle, new Vector2D(50,0));

        head.checkSelf();
        head.setPhysics("angular_velocity", new Object[]{new Vector2D(0,3.14)});

        //THIS IS A CHECK ON SINGLE OBJECT SPIN

        // Block b = new Block("firstBlock",20, 20);
        // Block c = new Block("attach",10,50);
        // c.deactivateAttribute("RigidBody");
        // b.addChild(c);
        // gEngine.startEngine();
        
        // gEngine.addToEnvironment(new Block[]{b});
        // gEngine.forceDisplace(c, new Vector2D(25,5));
       
        // gEngine.forceDisplace(b, new Vector2D(100,400));
        
        // b.setPhysics("velocity",new Object[]{new Vector2D(10,-70)});
        // b.setPhysics("angular_velocity", new Object[]{new Vector2D(0,3.1415)});

        // b.checkSelf();


        // Block collider = new Block("interceptor",40,40);
        // gEngine.addToEnvironment(collider);
        // gEngine.forceDisplace(collider, new Vector2D(230, 0));

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
