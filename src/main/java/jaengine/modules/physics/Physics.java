package jaengine.modules.physics;

import jaengine.modules.messages.*;
import jaengine.math.*;
import java.util.ArrayList;

public class Physics implements Messageable{
    private MessageHub hub;
    private ArrayList<Message> messages = new ArrayList<Message>();

    private Environment objectTree = new Environment();
    public Physics(MessageHub m) {
        hub = m;
        hub.addMember(this);
    }

    //required by interface
    public void addMessage(Message m) {
        messages.add(m);
    }
    public Message getNextMessage() {
        return messages.remove(0);
    }
    public void run() {
        while (!MessageHub.endProgram) {
            if (this.messages.size() > 0) {
                readNextMessage();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("FATAL ERROR");
                break;
            }
        }
    }
    public void readMessage(Message m) {

    }

    public void addToEnvironment(GameObject o) {
        o.initialize(objectTree);
        objectTree.getChildren().add(o);
    }
    // public static ArrayList<GameObject> visible = new ArrayList<GameObject>();
    // public static ArrayList<GameObject.Hitbox> collidable = new ArrayList<GameObject.Hitbox>(); 
    // public static ArrayList<GameObject.RigidBody> moveable = new ArrayList<GameObject.RigidBody>();

}

