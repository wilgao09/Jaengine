package jaengine.modules.messages;

import java.util.Date;
import java.text.DateFormat;

public class Message {
    final public long timeReceived;
    final public int code;
    final public Object[] data;
    // public Message(short type, Object[] paramDat) {
    //     timeReceived = System.currentTimeMillis();
    //     code = type;
    //     data = paramDat;
    // }
    public Message(int type, Object[] paramDat) {
        timeReceived = System.currentTimeMillis();
        code = type;
        data = paramDat;
    }
    public String toString() {
        String nS = "" + this.code;
        for (Object q : data)
            nS += " " + q.toString();
        return nS;
    }
}
