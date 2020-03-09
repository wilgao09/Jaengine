package jaengine.math;

import java.util.ArrayList;

public class Node<T> {
    protected T data;
    protected Node<T> parent;
    protected ArrayList<Node<T>> children;
    public Node(){};
    public Node(T dat) {
        this.data = dat;
    }

    public T getData() {
        return this.data;
    }
    public ArrayList<Node<T>> getChildren() {
        return children;
    }
    public Node<T> getParent() {
        return this.parent;
    }


    public void setData(T dat) {
        this.data = dat;
    }
    public void setParent(Node<T> paren) {
        this.parent = paren;
    }

    // public String toString() {

    // }
}