package Services;

import java.util.Observable;

public class QueueObserver extends Observable {
    private static QueueObserver instance = new QueueObserver();

    private QueueObserver() {}

    public static QueueObserver getInstance() {
        return instance;
    }

    public void notifyQueueChanged() {
        setChanged();
        notifyObservers();
    }
}