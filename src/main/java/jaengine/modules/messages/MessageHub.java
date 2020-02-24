package jaengine.modules.messages;

import java.util.ArrayList;

public class MessageHub implements Runnable{
    private ArrayList<Messageable> callingList = new ArrayList<Messageable>();
    private ArrayList<Message> messageStack = new ArrayList<Message> ();

    public static boolean endProgram = false;

    public MessageHub(){
        Debug.init(); //i dont trust it to target my debug, prob shouldve renamed it
        (new Thread(this,"MHUB")).start();
    }

    public void addMember(Messageable n) {
        callingList.add(n);
    }
    public void append(Message m) {
        this.messageStack.add(m);
    }
    public void mail() {
        Message msg = messageStack.remove(0);
        Debug.log( "Processing message: " + msg);
        for (Messageable m : callingList) {
            m.addMessage(msg);
            Debug.log("Mailing " + msg + " to " + m);
        }
        Debug.log("Processed!");
    }
    public void run() {
        while (!MessageHub.endProgram) {
            if (messageStack.size() > 0) {
                mail();
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                break;
            }
        }
        Debug.close();
    }

//    public void setNewScene(Scene n) {
//        currentScene = n;
//    }
//    public Scene getScene() {
//        return currentScene;
//    }
}