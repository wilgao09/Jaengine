package jaengine.core;


 import jaengine.logic.*;
import jaengine.modules.graphics.*;
import jaengine.modules.physics.*;
import jaengine.modules.input.*;
import jaengine.modules.messages.*;

import javafx.event.EventHandler;


/**
 * THIS MUST BE CHANGED FOR IT IS NOT FLEXIBLE ENOUGH
 */
public class Engine {

    private Physics physics;
    private Input input;
    private MessageHub hub;
    private Graphics graphics;

    private double screenX = 600;
    private double screenY = 600;

    private boolean isRunning = false;
    public Engine() {

    }
    public void setScreenWidth(double x) {
        screenX = x;
    }
    public void setScreenHeight(double y) {
        screenY = y;
    }
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

    //these dont really belong here; i will make a physics upper odule later for these
    public void addToEnvironment(GameObject n) {
        if (n.hasAttribute("Mesh")) {
            
            hub.append(new Message(1501, new Object[]{n, ((Mesh)(n.getAttribute("Mesh"))).getPoints()}));
        } else {
            hub.append(new Message(1501, new Object[]{n, new double[0]}));
        }
    }

    public void forceDisplace(GameObject n, Vector2D v) {
        hub.append(new Message(1503, new Object[]{n, v}));
    }
}
