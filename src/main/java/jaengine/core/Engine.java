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

    private Physics physics;
    private Input input;
    private MessageHub hub;

    private double screenX = 600;
    private double screenY = 600;

    private boolean isRunning = false;
    public Engine() {

    }
    public void setScreenWIdth(double x) {
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
        }
    }

    public void addToEnvironment(GameObject n) {
        physics.addToEnvironment(n);
    }
}
