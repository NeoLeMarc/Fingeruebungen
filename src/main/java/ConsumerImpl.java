/**
 * Created by mnoe on 29.11.2016.
 */
public class ConsumerImpl implements Consumer {

    public void consume(Consumable consumable) {
        System.out.println("ConsumerThread" + Thread.currentThread().getId() + " - consumed consumable - " + consumable.toString());
    }

    public void run() {
        System.out.println("Started consumer thread with ID: " + Thread.currentThread().getId());
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignore
            }
            Producer.queueForConsume(this);
        }
    }
}
