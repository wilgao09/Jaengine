package jaengine.modules;

import jaengine.modules.graphics.*;
import jaengine.modules.input.*;
import jaengine.modules.messages.*;
import jaengine.modules.physics.*;

public class MockGameLogic {
    MessageHub central;
    // Graphics graphics;
    Input user;

    public void engineStart() {
        central = new MessageHub();
        Debug.log("Initialized MessageHub");
        // graphics = new Graphics(central);
        //graphics is hard to get, so we can do that later
        Graphics.startGraphics(central);
        Debug.log("Initialized Graphics");
        user = new Input(central);
        Debug.log("Initialized Input");
        Debug.log("Initialization Complete!");
        // new Thread(central, "MHUB").start();

    }
    
    public static void main(String[] args) {
        MockGameLogic mock = new MockGameLogic();
        mock.engineStart();
        Message start = new Message(1100, new Object[]{600d,600d});
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("wtf");
        }
        mock.central.append(start);
    }
}