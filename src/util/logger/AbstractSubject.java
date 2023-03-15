package util.logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Each class that needs to be logged will implement this class
 * Calling the "setState()" method will result in a loggin operation being performed,
 * according to the implementation in the Observer instance
 */
public class AbstractSubject {
    private List<Observer> observers = new ArrayList<Observer>();
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
        notifyAllObservers();
    }

    public void attach(Observer observer){
        observers.add(observer);
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
