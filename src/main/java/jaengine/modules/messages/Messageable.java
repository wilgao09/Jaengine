package jaengine.modules.messages;

public interface Messageable extends Runnable{
    // private MessageHub hub;
    // public default ArrayList<Message> messages;
    // public Messageable(MessageHub m) {
    //     this.hub = m;
    //     m.addMember(this);
    // }
    
    public default void pushMessage(MessageHub h, Message m) {
        h.append(m);
    }
    public default void readNextMessage() {
        Message m = getNextMessage();
        readMessage(m);
    }
    public abstract Message getNextMessage();
    public abstract void addMessage(Message m); //FOR A MESSAGEHUB TO USE
    public abstract void readMessage(Message m); //must be defined
    public abstract void run();
}
