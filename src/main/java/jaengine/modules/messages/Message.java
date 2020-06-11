package jaengine.modules.messages;

/**
 * The class for a Message. Messages can be read by any module
 */
public class Message {
    final public long timeReceived;
    final public int code;
    final public Object[] data;
    // public Message(short type, Object[] paramDat) {
    //     timeReceived = System.currentTimeMillis();
    //     code = type;
    //     data = paramDat;
    // }
    /**
     * Initialize a new Message
     * @param type The mesage code; see the supplementary docs for a lsit of message codes
     * @param paramDat Supplementary information to give depth to the message
     */
    public Message(int type, Object[] paramDat) {
        timeReceived = System.currentTimeMillis();
        code = type;
        data = paramDat;
    }
    /**
     * returns a string representation of the message
     * @return a string representation of the message
     */
    public String toString() {
        String nS = "" + this.code;
        for (Object q : data)
            nS += " " + q.toString();
        return nS;
    }
}
