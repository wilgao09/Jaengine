package jaengine.modules.graphics;

import jaengine.modules.messages.*;
import jaengine.logic.Vector2D;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.paint.*;

import javafx.concurrent.Task;


//restrict this to one window
public class Graphics extends Application implements Messageable{
    private Stage window;
    private static MessageHub lastHub;
    private MessageHub hub;
    private ArrayList<Message> messages = new ArrayList<Message>();

    private jaengine.modules.graphics.Map objectMap;

    protected Group group;

    public Graphics(){ hub = Graphics.lastHub; hub.addMember(this); };//not for human use
    public static void startGraphics(MessageHub m) {
        Graphics.lastHub = m;
        new Thread(() -> {
            Application.launch();
        }, "GRAPHICS").start();
    }
    // public Graphics(MessageHub m) { //DEPRECATED
    //     this.hub = m;
    //     Graphics.lastHub = m;
    //     // hub.addMember(this); //THIS IS INTENTIONAL! THIS SHOULD NOT GO ON THE MAILING LIST

    //     new Thread(this,"GRAPHICS").start(); //THE THREAD GRAPH LOOKS REALY WEIRD RIGHT NOW, BUT BASICALLY 
    //     /**
    //      * Things im assuming based on my observations:
    //      *  1) javafx's fx application thread basically takes over main. It's a blocking thread that goes init, start, end, then continues on after Application.launch
    //      *  2) All javafx operations msut be done in the fx application thread
    //      *  3) Javafx's Application.launch works BY REFLECTION. This isnt an observation and mroelike reading documentation.
    //      *      This ultimately leads us to "we need another thread for the fx application to take over". That wopuld be the GRAPHICS thread.
    //      *      FX's reflection is the tricky part because it creates a copy of the class just to run the fx cycle. This measn we need to get that copy into the message hub and not our copy.
    //      *          This means the object we create is garbage
    //      */

    // } 

    //required by Messageable
    public void addMessage(Message m) {
        messages.add(m);
    }
    public Message getNextMessage() {
        return messages.remove(0);
    }
    public void run() {
        //DEPRECATED
        // System.out.println("IGRAPH SHOULD HAVE STARTED");
        // Application.launch(new String[0]); //the usual message breaking is in start
    }
    public void readMessage(Message m) {
        switch(m.code) {
            case(1100):
                // Box b = new Box(200,200,200);
                // PhongMaterial p = new PhongMaterial();
                // p.setSpecularColor(Color.RED);
                // p.setDiffuseColor(Color.BLUE);
                // b.setMaterial(p);
                this.group = new Group();
                Scene nScene = new Scene(this.group, (double)(Double)m.data[0], (double)(Double)m.data[1], Color.BLACK);
                window.setWidth((Double)m.data[0]);
                window.setHeight((Double)m.data[1]);
                window.setScene(nScene);
                // window.show();
                pushMessage(hub, new Message(101, new Object[]{nScene}));
                break;
                // new Thread(() ->{ //LAMBDAS! this refers to run based on parameters
                //     this.activate();
                // }, "THREADWITHTHEFXTHREAD" ).start();
                // Object someScene = Screen.setNewScene((double)m.data[0],(double)m.data[1]);
                // pushMessage(hub,new Message(101, new Object[]{someScene}));
                // break;
            case(1501):
                // Vector2D[] points = (Vector2D[])(m.data[1]);
                // double[] fxReadablePoints = new double[points.length * 2];
                // for (int n = 0; n != points.length; n++) {
                //     fxReadablePoints[n*2] = points[n].x();
                //     fxReadablePoints[n*2 + 1] = points[n].y();
                // }
                double[] points = (double[])(m.data[1]);
                if (points.length != 0)
                    objectMap.addObject(m.data[0], points);
                break;
            case (502):
                objectMap.apply(m.data[0],(Vector2D)m.data[1],(double)m.data[2]);
                break;
        }
    }

    //FX STUFF
    public void start(Stage mainStage) { 
        mainStage.setOnCloseRequest(event -> { //lambdaing like this works here ??
            MessageHub.endProgram = true;
        });
        window = mainStage;
        objectMap = new Map(this);
        mainStage.setTitle("Titleeee");
        // mainStage.setScene(new Scene(new Group(new Button("??")), 900.0, 100.0, Color.RED));
        // mainStage.setWidth(900.0);
        // mainStage.setHeight(300.0);
        mainStage.show();
        //platform dolater
        Task<Integer> messenger = new Task<Integer>() {
            @Override
            protected Integer call() {
                while (!MessageHub.endProgram) {
                    //jesus fking christ
                    int msgCount = messages.size();
                    for (int n = 0; n != Math.ceil(msgCount/3.0); n++) {
                        Platform.runLater(new Thread( () -> {readNextMessage();}));
                    } 
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Debug.log("FATAL ERROR: GRAPHICS THREAD INTERRUPTED");
                        break;
                    }
                }
                
                return 0;
            }
        };
        Thread checker = new Thread(messenger);
        checker.setDaemon(true);
        checker.start();

    }

}