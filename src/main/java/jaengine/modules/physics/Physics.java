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
        (new Thread(this,"PHYSICS")).start();
    }

    //required by interface
    public void addMessage(Message m) {
        messages.add(m);
    }
    public Message getNextMessage() {
        return messages.remove(0);
    }
    public void run() {
        // pushMessage(hub, new Message(300, new Object[]{objectTree})); //initial send;
        while (!MessageHub.endProgram) {
            
            while (this.messages.size() > 0) {
                readNextMessage();
            }
            try {

                Physics.this.runPhysicsTick();

                Thread.sleep(50);
            } catch (InterruptedException e) {
                System.out.println("FATAL ERROR");
                break;
            }
        }
    }
    public void readMessage(Message m) {

    }

    public boolean addToEnvironment(GameObject o) {
        //determine if there are duplicate names; if there are, do nothing
        for (Node<GameObject> n : objectTree.getChildren()) {
            if (n.getData().name.equals(o.getName())) {
                return false;
            }
        }
        o.initialize(objectTree);
        objectTree.getChildren().add(o);
        return true;
        //all of o's meshes need to go to graphics


    }
    // public static ArrayList<GameObject> visible = new ArrayList<GameObject>();
    // public static ArrayList<GameObject.Hitbox> collidable = new ArrayList<GameObject.Hitbox>(); 
    // public static ArrayList<GameObject.RigidBody> moveable = new ArrayList<GameObject.RigidBody>();
    public void runPhysicsTick() {
        ArrayList<GameObject> needToBeRedrawn = new ArrayList<GameObject>();
        //resolve forces, combine to preexisting velocity
        //resolve new location
        //
        //correct collisions (this is quite the daunting physics problem)

        //send draw signal  RESOLVED code 501
        for (GameObject g : needToBeRedrawn) {
            pushMessage(hub, new Message(501, new Object[]{}));
        }
    }
}

