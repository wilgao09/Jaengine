package jaengine.logic;

import java.util.ArrayList;

/**
 * Unused interface; was ocne part of the Node class.
 * @param <N> A class that implements this interface (DataHolder)
 * @param <T> Datatype for this interface
 */
public interface DataHolder<N extends DataHolder, T> {
    public T getData();
    public ArrayList<N> getChildren();
    public N getParent();
    
    public void setData(T data);
    public void setParent(N paren);

    
}
