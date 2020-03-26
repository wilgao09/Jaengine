package jaengine.modules.physics;


import jaengine.logic.*;

//exists to be a root
public class Environment extends GameObject{
    public Environment(){
        super("Environment",new Vector2D(0,0));
        environment = this;
    }
    public void addObject(GameObject g) {
        super.getChildren().add(g);
        g.initialize(this);
    }

}
