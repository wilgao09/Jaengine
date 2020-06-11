package jaengine.modules.input;

import java.util.HashMap;
import java.util.ArrayList;

import jaengine.modules.messages.*;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
// import javafx.scene.input.KeyCode;

/**
 * The Input class keeps track of the keypresses and keyreleases made by the user while the javaFX application is in focus. 
 */
public class Input implements Messageable {
    private MessageHub hub;
    private ArrayList<Message> messages = new ArrayList<Message>();
    private HashMap<String, Long> keyPresses = new HashMap<String, Long>();
    /**
     * Initialize a new Input module
     * @param m the MessageHub to attach to
     */
    public Input(MessageHub m) {
        hub = m;
        hub.addMember(this);
        (new Thread(this,"INPUT")).start();
    }


    //THESE ARE PART OF THE INTERFACE
    public void addMessage(Message m) {
        messages.add(m);
    }
    public Message getNextMessage() {
        return messages.remove(0);
    }
    /**
     * Runs the message reading loop. A part of the Runnable interface. 
     */
    public void run() {
        while (!MessageHub.endProgram) {
            while (this.messages.size() > 0) {
                try {
                    readNextMessage();
                } catch (NullPointerException e) {
                    System.out.println("Input tried to read something that didnt exist");
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Debug.log("FATAL ERROR: INPUT THREAD INTERRUPTED");
                break;
            }
        }
    }
    /**
     * Reads messages. Currently accepts 101
     * @param m the message to be read
     */
    public void readMessage(Message m) {
        switch (m.code) {
            case (101):
                sceneListen((Scene)m.data[0]);
                break;
        }
    }

    /**
     * Attach listeners to a scene
     * @param nScene the scene to attach listeners to
     */
    public void sceneListen(Scene nScene) {
        nScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                long n =  System.currentTimeMillis();
                keyPresses.put(e.getCode().getName(),n);
                pushMessage(hub, new Message(201, new Object[]{e.getCode(),n}));
            }
        });
        nScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                keyPresses.remove(e.getCode().getName());
                pushMessage(hub, new Message(202, new Object[]{e.getCode(),System.currentTimeMillis()}));
            }
        });
        
    }
    
}