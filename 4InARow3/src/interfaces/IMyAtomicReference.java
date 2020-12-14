package interfaces;

public interface IMyAtomicReference<T> {
    
    void update(IFunction<T> updater);
    
}
