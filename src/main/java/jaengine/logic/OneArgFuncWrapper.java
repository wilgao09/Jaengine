package jaengine.logic;


public interface OneArgFuncWrapper extends ZeroArgFuncWrapper {
    
    public abstract void f(Object n);
    @Override
    public default void f() {
        f(new Object());
    }
}