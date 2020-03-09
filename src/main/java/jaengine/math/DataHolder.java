package jaengine.math;

import java.util.ArrayList;

public interface DataHolder<N extends DataHolder, T> {
    public T getData();
    public ArrayList<N> getChildren();
    public N getParent();
    
    public void setData(T data);
    public void setParent(N paren);

    
}
