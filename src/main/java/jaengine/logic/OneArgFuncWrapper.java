package jaengine.logic;

/**
 * A function interface. The function accepts an Object argument.
 */
public interface OneArgFuncWrapper extends ZeroArgFuncWrapper {
    /**
     * The function to overwrite
     * @param n some object parameter
     */
    public abstract void f(Object n);
    /**
     * Overloaded f, tried to run f(Object), wher ethe Object is a new Object(). Not intended to be used
     */
    @Override
    public default void f() {
        f(new Object());
    }
}