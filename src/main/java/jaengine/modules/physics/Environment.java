package jaengine.modules.physics;


import jaengine.logic.*;

//exists to be a root
/**
 * The root of an environment. Does nothing except be root. SHould not be used by anyone except for the physics module
 */
public class Environment extends GameObject{
    /**
     * Create a new envionment
     */
    public Environment(){
        super("Environment");
        environment = this;
    }
    /**
     * Add an object to the envionment 
     * @param g the object to add
     * */
    public void addObject(GameObject g) {
        super.getChildren().add(g);
        g.initialize(this);
    }

}
