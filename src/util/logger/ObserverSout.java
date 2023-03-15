package util.logger;

public class ObserverSout extends Observer{


    public ObserverSout(AbstractSubject subject){
        this.subject = subject;
        this.subject.attach(this);
    }

    @Override
    public void update() {
        System.out.print(subject.getState());
    }
}
