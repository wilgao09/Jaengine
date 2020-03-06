package jaengine.core;


import jaengine.math.*;
import jaengine.modules.graphics.*;
import jaengine.modules.physics.*;
import jaengine.modules.input.*;
import jaengine.modules.messages.*;


/**
 * THIS MUST BE CHANGED FOR IT IS NOT FLEXIBLE ENOUGH
 */
public class Engine {
    private Graphics graphics;
    private Physics physics;
    private Input input;
    private MessageHub hub;
    public Engine() {
        hub = new MessageHub();
        graphics = new Graphics(hub);
        physics = new Physics(hub);
        input = new Input(hub);
    }
}
