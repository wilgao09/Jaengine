package jaengine.modules.physics;

import jaengine.modules.messages.*;
import jaengine.logic.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Physics implements Messageable{
    private MessageHub hub;
    private ArrayList<Message> messages = new ArrayList<Message>();

    private Environment objectTree;

    private double timeScale = 1; //ratio of simulation time to real time
    public Physics(MessageHub m) {
        hub = m;
        hub.addMember(this);
        objectTree = new Environment();
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
        switch(m.code) {
            case(1501):
                addToEnvironment((GameObject)(m.data[0]));
                break;
            case(1503):
                displaceObject((GameObject)(m.data[0]), (Vector2D)(m.data[1]));
                break;
        }
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
    }


    
    // public static ArrayList<GameObject> visible = new ArrayList<GameObject>();
    // public static ArrayList<GameObject.Hitbox> collidable = new ArrayList<GameObject.Hitbox>(); 
    // public static ArrayList<GameObject.RigidBody> moveable = new ArrayList<GameObject.RigidBody>();
    public void runPhysicsTick() {
        // ArrayList<GameObject> needToBeRedrawn = new ArrayList<GameObject>();
        // ArrayList<Vector2D> translations = new ArrayList<Vector2D>();
        // ArrayList<Double> rotations = new ArrayList<Double>();

        HashMap<Node<GameObject>, Object[]> updates = new HashMap<Node<GameObject>, Object[]>();
        //resolve forces, combine to preexisting velocity
        //resolve new location

        Recursor<OneArgFuncWrapper> tick = new Recursor<OneArgFuncWrapper>();
        tick.func = (Object o) -> { //this is suuper convoluted here. it's a class holding a functional interface, then writing the function for that interface.
            GameObject focus;
            try {
                focus = ((Node<GameObject>)o).getData();
            } catch (ClassCastException e) {
                return;
            }
            ArrayList<Node<GameObject>> childs = focus.getChildren();

            for (Node<GameObject> n : childs) {
                if (n.getData().hasAttribute("RigidBody")) {
                    RigidBody rb = (RigidBody) n.getData().getAttribute("RigidBody");
                    //apply forces
                    rb.addForce(new Vector2D(0, rb.getMass() * 10));



                    //CODE TO ACTUALLY IMPLEMENT POSITION UPDATE

                    Vector2D velocityByForce = rb.getForce().scale(timeScale / (20.0 * rb.getMass())) ;
                    rb.zeroForce();
                    Vector2D rbVel = rb.getVelocity() ;
                    rbVel = rbVel.add(velocityByForce);
                    rb.setVelocity(rbVel);
                    if (rbVel.magnitude() > .1 ) {
                        
                        Vector2D displacement = rbVel.scale(timeScale/ 20.0);
                        if (n.getData().hasAttribute("Mesh"))
                            updates.put(n,new Object[]{displacement, 0.0});

                        n.getData().step(displacement);
                    }


                } else {
                    if (updates.containsKey(n.getParent())) {
                        updates.put(n, updates.get(n.getParent()));
                    }
                }
                tick.func.f(n); //call on its members
                
            }
        };

        tick.func.f(objectTree);

        //correct collisions (this is quite the daunting physics problem)

        //send draw signal  RESOLVED code 501
        for (HashMap.Entry<Node<GameObject>,Object[]> pair : updates.entrySet()) { //JACK OVERFLOW
            pushMessage(hub, new Message(
                502, 
                new Object[]{
                    pair.getKey(), //specify which object is moving (pray for no hash collisions)
                    pair.getValue()[0], //corresponds to translation vector
                    pair.getValue()[1] //will correspond to rotation
                }
                ));
            // pushMessage(hub, new Message(501, new Object[]{ needToBeRedrawn.get(n).getAttribute("mesh"), translations.get(n), rotations.get(n) }));
        }
    }

    //for now, this is meant to NOT BE USED BY THE PHYSICS TICK METHOD
    //THIS IS FOR PHYSICS BREAKING MOVEMENT
    public void displaceObject(GameObject g, Vector2D v) {
        //this is a mini physics tick
        if (g.hasAttribute("Hitbox")) {
            Hitbox hb = ((Hitbox)(g.getAttribute("Hitbox")));
            for (int n = 0 ;n != hb.verticies.length; n++) {
                hb.verticies[n] = hb.verticies[n].add(v);
            }
        }
        g.location = g.location.add(v);
        this.pushMessage(hub, new Message(502, new Object[]{g, v, 0.0}));
        for (Node<GameObject> c : g.getChildren()) {
            GameObject child = c.getData();
            if (!child.hasAttribute("RigidBody")) {
                displaceObject(child, v);
            }
        }
    }

}

