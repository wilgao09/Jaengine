package jaengine.modules.physics;

import jaengine.math.*;

//exists to be a root
public class Environment extends GameObject{
    public Environment(){
        super(new Vector2D(0,0));
        environment = this;
    }


}
