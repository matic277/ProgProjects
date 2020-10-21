package interfaces;


public interface IInputHandler extends IObserver {
    
    void processTask(Runnable task);
    void processMouseClick();
}
