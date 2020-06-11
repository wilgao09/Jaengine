package jaengine.core;


 import jaengine.logic.*;
import jaengine.modules.graphics.*;
import jaengine.modules.physics.*;
import jaengine.modules.input.*;
import jaengine.modules.messages.*;

import javafx.event.EventHandler;


/**
 * The Engine class simplifies actions that would normally require calling an sending multiple messages to multiple modules. It's primary purpose is to aadd objects to the Environment and force displace them.
 */
public class Engine {

    private Physics physics;
    private Input input;
    private MessageHub hub;
    private Graphics graphics;

    private double screenX = 600;
    private double screenY = 600;

    private boolean isRunning = false;
    /**
     * Construct a new Engine. Only one should be created per session.
     */
    public Engine() {

         
    }
    /**
     * Set the screen width to some pixel width
     * @param x the width of the desired screen
     */
    public void setScreenWidth(double x) {
        screenX = x;
    }
    /**
     * Set the screen height to some pixel width
     * @param y the height of the desired screen
     */
    public void setScreenHeight(double y) {
        screenY = y;
    }
    /**
     * Start the engine by initializing all modules; takes at least 1.5s to start all threads and attach them to teh MessageHub.
     */
    public void startEngine() {
        if (!isRunning) {
            isRunning = true;
            hub = new MessageHub();
            physics = new Physics(hub);
            input = new Input(hub);

            Graphics.startGraphics(hub); //because javafx dumb

            Message start = new Message(1100, new Object[]{screenX, screenY});
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("wtf");
            }
            
            hub.append(start);
            System.out.println("Please give the engine a few seconds to warm up!");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                System.out.println("wtf");
            }
            System.out.println("WARM!");
            // try {
            //     Thread.sleep(1000);
            // } catch (InterruptedException e) {
            //     System.out.println("wtf");
            // }

            // graphics = (Graphics)hub.getMembers().get(3);
        }
    }

    /**
     * Add a user defined object to the envionment that this engine created.
     * @param n A GameObject, or some descendent of a GameObject. Must have all desired children and attributes before entering Envionment.
     */
    public void addToEnvironment(GameObject n) {
        
        Recursor<OneArgFuncWrapper> init = new Recursor<OneArgFuncWrapper>();
        init.func = (Object o) -> {
            GameObject q = ((GameObject)o);
            if (q.hasAttribute("Mesh")) {
                
                hub.append(new Message(1501, new Object[]{q, ((Mesh)(q.getAttribute("Mesh"))).getPoints()}));
            } else {
                hub.append(new Message(1501, new Object[]{q, new double[0]}));
            }
            for (Node<GameObject> ob : q.getChildren()) {
                init.func.f(ob.getData());
            }
        };
        init.func.f(n);
    }

    /**
     * Add an array of Gameobjects and all of their descendents to the Environment
     * @param go The GameObject to add
     */
    public void addToEnvironment(GameObject[] go) {
        for (GameObject g :go ) {
            addToEnvironment(g);
        }
    }
    /**
     * Displace a GameObject and all of its descendents with a displacement of v
     * @param n The GameoBject to displace
     * @param v Displacement vector to dispalce by
     */
    public void forceDisplace(GameObject n, Vector2D v) {
        hub.append(new Message(1503, new Object[]{n, v}));
    }




    /**
     * Debug the Envionment by printing the tree.
     */
    public void printTree() {
        System.out.println(physics.getEnviron().toString());
    }
}
