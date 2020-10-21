package implementation.listeners;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class takes care of user inputs.
 * Panel thread (and its listeners) dispatch
 * events that should be handled in this thread,
 * so that the Panel thread is only responsible
 * for painting, not calculating (processing events).
 *
 * Reusable singleton Thread.
 * Runnable as strategy.
 *
 */
public class EventHandler extends Thread {
    
    Queue<Runnable> taskQueue;
    String threadName;
    AtomicBoolean isWaiting;
    
    private static final Object lock = new Object();
    
    public EventHandler(Runnable firstTask) {
        this.taskQueue = new ArrayDeque<>();
        this.taskQueue.add(firstTask);
        this.isWaiting = new AtomicBoolean(false);
        this.threadName = this.getClass().getSimpleName() + "[" + Thread.currentThread().getId() + "]";
    }
    
    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        System.out.println(" + Thread " + threadName + " started.");
    
        while (true) {
            Runnable task = taskQueue.poll();
            
            // TODO: this should never be null? otherwise why have we been woken up?
            // execute given task
            if (task != null) {
                task.run();
            }
            
            // task finished, signal sleep/waiting mode
            isWaiting.set(true);
  
            // self-pause after task is completed
            System.out.println("   ~ " + threadName + " pausing.");
            synchronized(lock) {
                try { lock.wait(); }
                catch (Exception e) { /* Do nothing */ e.printStackTrace(); }
            }
            System.out.println("   ~ " + threadName + " woken-up.");
            
            // thread woken-up
            isWaiting.set(false);
        }
        // unreachable
    }
    
    public void wakeThreadAndExecuteTask(Runnable task) {
        synchronized (lock) {
            // notify is enough in comparison to notifyAll(),
            // since only THIS thread is waiting on this lock
            lock.notify();
        }
        taskQueue.add(task);
    }
}
