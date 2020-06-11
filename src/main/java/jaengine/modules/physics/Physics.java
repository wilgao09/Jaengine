package jaengine.modules.physics;

import jaengine.modules.messages.*;
import jaengine.logic.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Runs physics oeprations and communicates dispalcements and rotations to MessageHub
 */
public class Physics implements Messageable{
    private MessageHub hub;
    private ArrayList<Message> messages = new ArrayList<Message>();

    private Environment objectTree;

    private double timeScale = 1; //ratio of simulation time to real time
    /**
     * Create a new Physics module
     * @param m the MessageHub to attach to
     */
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
    /**
     * contains the message reading loop; not for use
     */
    public void run() {
        // pushMessage(hub, new Message(300, new Object[]{objectTree})); //initial send;
        while (!MessageHub.endProgram) {
            
            while (this.messages.size() > 0) {
                try {
                    readNextMessage();
                } catch (NullPointerException e) {
                    System.out.println("Physics tried to read a nonexistent message");
                }
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
    /**
     * Reads messages; currently only supports codes 1501 and 1503
     * @param m the message to read
     */
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
    /**
     * Puts an object as a child to this Physics module's Environment
     * @param o the object to enter the envionment
     * @return a boolena describing its success in entering
     */
    public boolean addToEnvironment(GameObject o) {
        //if it already has a parent, ignore it because it was intended for graphics
        if (o.getParent() != null) return false;
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
    /**
     * To put very very very very very very simply: applies a physics tick.
     * It starts by creating a HashMap of Node<GameObject> and Vector2D[], where the Vector2D[] refers to the dispalcement and rotation of the Node<GameObject>
     * It applies natural forces (like gravity), resolves all forces, then applies collision forces for the next tick to resolve
     * Resolved forces are computed into displacements and rotations and added to the HashMap with their respective Node<GameObject>
     * at the end, the HashMap is looped through and everything is sent to the MesageHub
     * 
     */
    public void runPhysicsTick() {
        // ArrayList<GameObject> needToBeRedrawn = new ArrayList<GameObject>();
        // ArrayList<Vector2D> translations = new ArrayList<Vector2D>();
        // ArrayList<Double> rotations = new ArrayList<Double>();

        HashMap<Node<GameObject>, Vector2D[]> updates = new HashMap<Node<GameObject>, Vector2D[]>();
        //resolve forces, combine to preexisting velocity
        //resolve new location

        Recursor<OneArgFuncWrapper> tick = new Recursor<OneArgFuncWrapper>();
        /**
         * Apply gravity to all rigidbodied children on GameObject o; this is a RECURSIVE application of GRAVITY
         * @param o the object to observe
         */
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
                    // System.out.println("BEFORE: " + rb.getNetForce());
                    rb.addCOMForce(new Vector2D(0, n.getData().getSysMass() * 10));


                    Vector2D[] result = updateObject(n.getData());  
                    if (n.getData().hasAttribute("Mesh") && result != null)
                        updates.put(n,result);



                } else {
                    GameObject ancestor = n.getData().getRBAncestor();
                    if (updates.containsKey(ancestor)) {
                        
                        Vector2D[] pUpdates = updates.get(n.getParent());
                        Vector2D dispFromCenter = n.getData().location.add(ancestor.location.reverse()); //check here if slow
                        Vector2D dispByAngDisp = dispFromCenter.rotate(pUpdates[1].y()).add(dispFromCenter.reverse());
                        updates.put(n, new Vector2D[]{pUpdates[0].add(dispByAngDisp),pUpdates[1]});

                        // n.getData().step(pUpdates[0].add(dispByAngDisp));
                        // n.getData().spin(pUpdates[1]);
                    } 

                }
                tick.func.f(n); //call on its members
                
            }
        };

        tick.func.f(objectTree);

        //correct collisions (this is quite the daunting physics problem)
        /**
         * Fix collisions. I have no idea why or how this works, but it does
         */
        tick.func = (Object o) -> { //note that this should ONLY be run on the environment and NEVER recursively 
            
            GameObject focus;
            try {
                focus = ((GameObject)o);
            } catch(ClassCastException e) {
                return;
            }
            ArrayList<Node<GameObject>> childs = focus.getChildren();
            for (Node<GameObject> n : childs) {
                for (Node<GameObject> c : childs) {
                    if (n != c && n.getData().isOverlapping(c.getData())) {
                        //two objects in the environment are near each other (surface level 1)
                        //at least one of two parties is of type rigidbofy

                        GameObject s1 = n.getData();
                        GameObject s2 = c.getData();
                        if ( (s1.hasAttribute("Hitbox") && s2.hasAttribute("Hitbox") )) {
                            Object[] data = Intersections.findCritPoints(s1.getPoints(), s2.getPoints());
                            if (data != null) {
                                Vector2D velA = new Vector2D(0,0);
                                if (s1.hasAttribute("Rigidbody")){
                                    velA = ((RigidBody)s1.getAttribute("RigidBody")).getVelocity();
                                }
                                Vector2D velB = new Vector2D(0,0);
                                if (s2.hasAttribute("RigidBody")){
                                    velB = ((RigidBody)s1.getAttribute("RigidBody")).getVelocity();
                                }
                                //Vector2D impulse = Collisions.findImpulse(s1.getMass(), velA, s2.getMass(), velB, 1);
                                

                                Vector2D s1Direction = Collisions.findDirection(((HashMap<Integer,Integer>)data[1]), ((Hitbox)s1.getAttribute("Hitbox"))).rotate(s1.rotation.y());
                                //Vector2D s2Direction = Collisions.findDirection(((HashMap<Integer,Integer>)data[2]), ((Hitbox)s2.getAttribute("Hitbox"))).rotate(s2.rotation.y());
                                Vector2D s2Direction = s1Direction.reverse();
                                //Vector2D s1Direction = new Vector2D(1,0);
                                //Vector2D s2Direction = new Vector2D(-1,0);
                                double impulseMag = Collisions.findImpulse(s1.getMass(), velA, s2.getMass(), velB,s1Direction, 1);
                                System.out.println("IMPUSLE MAG " + impulseMag);
                                Vector2D intoS2 = s2Direction.scale(impulseMag*20);
                                Vector2D intoS1 = s1Direction.scale(impulseMag*20);
                                System.out.println("s1 is " + s1 + "; s2 is " + s2);
                                System.out.println("into s1 " + intoS1 + " into s2 " + intoS2);

                                Vector2D worldPoint = (Vector2D)data[0];
                                //System.out.println("s1Direc " + intoS1 + " s2Direc " + intoS2 + " wp " + worldPoint);
                                //pushMessage(hub, new Message(503,new Object[]{worldPoint.x(), worldPoint.y(), 3}));
                                s1.applyForce(intoS2, worldPoint);
                                s2.applyForce(intoS1, worldPoint);
                                // System.out.println("applying force: " + impulseMag);
                            } else {
                                System.out.println("NULLED INFO");
                            }
                        }
                    }
                }
            }
        };

        tick.func.f(objectTree);

        //send draw signal  RESOLVED code 501
        for (HashMap.Entry<Node<GameObject>,Vector2D[]> pair : updates.entrySet()) { //JACK OVERFLOW
            pair.getKey().getData().step(pair.getValue()[0]);
            pair.getKey().getData().spin(pair.getValue()[1]);
            pushMessage(hub, new Message(
                502, 
                new Object[]{
                    pair.getKey(), //specify which object is moving (pray for no hash collisions)
                    pair.getValue()[0], //corresponds to translation vector
                    pair.getValue()[1] //will correspond to rotation "vector"
                }
                ));
            // pushMessage(hub, new Message(501, new Object[]{ needToBeRedrawn.get(n).getAttribute("mesh"), translations.get(n), rotations.get(n) }));
        }
    }

    //for now, this is meant to NOT BE USED BY THE PHYSICS TICK METHOD
    //THIS IS FOR PHYSICS BREAKING MOVEMENT
    /**
     * Dispalce an object and go agaisnt the laws fo physics
     * @param g teh Object to move
     * @param v the amount to displace by
     */
    public void displaceObject(GameObject g, Vector2D v) {
        //this is a mini physics tick
        // if (g.hasAttribute("Hitbox")) {
        //     Hitbox hb = ((Hitbox)(g.getAttribute("Hitbox")));
        //     for (int n = 0 ;n != hb.verticies.length; n++) {
        //         hb.verticies[n] = hb.verticies[n].add(v);
        //     }
        // }
        g.step(v);
        this.pushMessage(hub, new Message(502, new Object[]{g, v, g.rotation}));
        for (Node<GameObject> c : g.getChildren()) {
            GameObject child = c.getData();
            if (!child.hasAttribute("RigidBody") ) {
                displaceObject(child, v);
            }
        }
    }


    /**
     * Return this Environment
     * @return the Environment
     */
    public Environment getEnviron() {
        return this.objectTree;
    }

    /**
     * Precondition: the object is a rigidbody
     * @param g the object with unresolved forces
     * @return a length 2 Vector2D[] that describes [0]: the translation vector and [1]: the rotation vector
     */
    public Vector2D[] updateObject(GameObject g){
        RigidBody rb = ((RigidBody)g.getAttribute("RigidBody"));
        System.out.println("working on force " + rb.getNetForce());

        //UPDATE POSITIONS BASED ON NET FORCE
        Vector2D velocityByForce = rb.getNetForce().scale(timeScale / (20.0 * g.getSysMass())) ;

        Vector2D rbVel = rb.getVelocity() ;
        // if (Double.isNaN(velocityByForce.magnitude())) {
        //     Vector2D test = rb.getNetForce();
        //     System.out.println("what the fuck");
        // } else {
        //     System.out.println(velocityByForce.magnitude());
        // }
        rbVel = rbVel.add(velocityByForce);
        rb.setVelocity(rbVel);
        rb.zeroForce();

        // //UPDATE ANGPOSITION BASED ON NET TORQUES
        Vector2D angVelByTorque = rb.getNetTorque().scale(timeScale/ (20 * g.getSysRotInert()));
        rb.zeroTorque();
        Vector2D rbAngVel = rb.getAngVel();
        rbAngVel = rbAngVel.add(angVelByTorque);
        rb.setAngVel(rbAngVel);

        //theres a lot of interesting stuff happening here
        //new rule: the rigidbody applies to itself AND the system of NONRBS that are its children and not of any other rigidbodies


        if (rbVel.magnitude() > .1 || rbAngVel.magnitude() > .1) {
            // System.out.println ("velocity " + rbVel);
            Vector2D angDisp = rbAngVel.scale(timeScale/20.0);
            Vector2D cCom = g.checkSysCOM();
            pushMessage(hub, new Message(503,new Object[]{cCom.x(), cCom.y(),6}));
            Vector2D dispFromCOM = g.location.add(cCom.reverse());
            Vector2D dispByRotation = dispFromCOM.rotate(angDisp.y()).add(dispFromCOM.reverse());
            Vector2D displacement = rbVel.scale(timeScale/ 20.0).add(dispByRotation);

            return new Vector2D[]{displacement,angDisp};
        }
        return null;
    }
}

