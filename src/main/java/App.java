
import jaengine.core.Engine;
import jaengine.physics.*;
import jaengine.logic.*;
public class App 
{
    public static void main( String[] args )
    {
        Engine gEngine = new Engine();
        Block b = new Block("firstBlock",new Vector2D(0,0),20, 20);
        gEngine.startEngine();
        
        gEngine.addToEnvironment(b);
        gEngine.forceDisplace(b, new Vector2D(100,400));
        b.setVelocity(new Vector2D(20,-100));

        // Node<Double> dNode = new Node<Double>(12.0);
        // Node<String> sNode = new Node<String>("STRING");
        // dNode.getChildren().add(sNode);
    }
}
