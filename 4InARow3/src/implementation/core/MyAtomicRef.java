package implementation.core;

import interfaces.IMyAtomicReference;
import interfaces.IFunction;

public class MyAtomicRef <T> implements IMyAtomicReference<T> {
    
    private final T obj;
    private final Object lock = new Object();
    
    public MyAtomicRef(T obj) { this.obj = obj; }
    
    @Override
    public void update(IFunction<T> function) {
        synchronized (lock) {
            function.apply(obj);
        }
    }
    
//    @Override
//    public T get() { return obj; }
}
