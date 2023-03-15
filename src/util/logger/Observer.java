package util.logger;

/**
 * Loggers
 */
public abstract class Observer {
    protected AbstractSubject subject;
    public abstract void update();
}