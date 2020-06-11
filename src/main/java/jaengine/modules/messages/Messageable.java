package jaengine.modules.messages;
/**
 * The interface that ALL members atached to a messaghe hub MUST implement
 */
public interface Messageable extends Runnable{
    // private MessageHub hub;
    // public default ArrayList<Message> messages;
    // public Messageable(MessageHub m) {
    //     this.hub = m;
    //     m.addMember(this);
    // }
    /**
     * Send a message to the MessageHub
     * @param h A reference to the MessageHub 
     * @param m The message to send
     */
    public default void pushMessage(MessageHub h, Message m) {
        h.append(m);
    }
    /**
     * The procedure to read the next message
     */
    public default void readNextMessage() {
        Message m = getNextMessage();
        readMessage(m);
    }
    /**
     * Get a message
     * @return the Message received 
     */
    public abstract Message getNextMessage();
    /**
     * Attach a message to this member's list of messages; should be used only by the MessageHub
     * @param m the Message to aattach
     */
    public abstract void addMessage(Message m); //FOR A MESSAGEHUB TO USE
    /**
     * Defines the prodecure by which a message should be read
     * @param m the message to read
     */
    public abstract void readMessage(Message m); //must be defined
    /**
     * Must be defined by the implementor; usually contains the message reading loop; a part of the Runnable Interface
     */
    public abstract void run();
}
