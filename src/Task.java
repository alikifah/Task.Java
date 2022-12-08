package Task.java;

/**
 *
 * @author Ali Al-khafaji
 */
import java.util.concurrent.*;
import java.util.function.*;

public class Task {

    public static <T> T wait(Supplier<T> s) {
        stateMachine<T> t = new stateMachine<>();
        return t.wait(s);
    }

    public static void wait(Runnable s) {
        stateMachineVoid t = new stateMachineVoid();
        t.await(s);
    }

    public static void wait(Runnable s, int delay) {
        delay(delay);
        stateMachineVoid t = new stateMachineVoid();
        t.await(s);
    }

    public static void run(Runnable s) {
        CompletableFuture.runAsync(s);
    }

    public static void run(Runnable s, int delay) {
        CompletableFuture.runAsync(() -> {
            delay(delay);
        }).thenRunAsync(s);
    }

    public static void delay(int ms) {
        try {
            Thread.sleep(ms); // we block main thread
        } catch (InterruptedException x) {
        }
    }
}

class stateMachine<T> {

    T v;
    Thread m = Thread.currentThread(); // main thread

    public T wait(Supplier<T> s) {
        CompletableFuture.supplyAsync(s).
                thenAccept(value -> {
                    v = value;
                    m.interrupt(); // we interrupt blocking
                });
        try {
            Thread.sleep(Long.MAX_VALUE); // we block main thread
        } catch (InterruptedException x) {
            return v;
        }
        return v;
    }

    public void await(Runnable s) {
        CompletableFuture.runAsync(s).
                thenRun(() -> {
                    m.interrupt(); // we interrupt blocking
                });
        try {
            Thread.sleep(Long.MAX_VALUE); // we block main thread
        } catch (InterruptedException x) {
        }
    }
}

class stateMachineVoid {

    Thread m = Thread.currentThread(); // main thread

    public void await(Runnable s) {
        CompletableFuture.runAsync(s).
                thenRun(() -> {
                    m.interrupt(); // we interrupt blocking
                });
        try {
            Thread.sleep(Long.MAX_VALUE); // we block main thread
        } catch (InterruptedException x) {
        }
    }

}
