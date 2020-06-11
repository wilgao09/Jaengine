package jaengine.logic;

import java.util.ArrayList;

/**
 * Generic Node class that holds one data type. 
 * @param <T> The datatype to hold
 */
public class Node<T> {
    protected T data;
    protected Node<T> parent;
    protected ArrayList<Node<T>> children = new ArrayList<Node<T>>();
    /**
     * Initialize a node with no data
     */
    public Node(){};
    /**
     * Initialize a node with data
     * @param dat Data to initialize with; must match T
     */
    public Node(T dat) {
        this.data = dat;
    }
    /**
     * Return the data stored in this node
     * @return the data in this node
     */
    public T getData() {
        return this.data;
    }
    /**
     * Return the ArrayList of children.
     * @return the children attached to this node.
     */
    public ArrayList<Node<T>> getChildren() {
        return children;
    }
    /**
     * Return this node's parent. 
     * @return This node's parent. If there is no parent, return null.
     */
    public Node<T> getParent() {
        return this.parent;
    }

    /**
     * Replace this node's data
     * @param dat the new data for this ndoe to hold. 
     */
    public void setData(T dat) {
        this.data = dat;
    }
    /**
     * Reassign this node's parent. This DOES NOT change the old parent's children. Use addChild instead!
     * @param paren the new parent for this node.
     */
    public void setParent(Node<T> paren) {
        this.parent = paren;
    }
    /**
     * Add a node as a child on this node. Will replace the child node's parent.with this node
     * @param n The child node
     */
    public void addChild(Node<T> n) {
        this.children.add(n);
        n.setParent(this);
    }
    /**
     * A function that prints this node's information
     * @param i Unused and meant to be used by descendemnt classes
     * @return  a String representation of this node
     */
    public String toString(int i) {
        return this.toString();
    }

    // public String toString() {

    // }
}