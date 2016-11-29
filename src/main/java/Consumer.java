/**
 * Created by mnoe on 29.11.2016.
 */
public class Consumer implements Runnable {

    private int consumeCounter = 0;
    private int maxConsume = 0;
    private boolean exitThread = false;

    public Consumer(int maxConsume) {
        this.maxConsume = maxConsume;
    }

    public int getConsumeCounter() {
        return consumeCounter;
    }

    public void consume(Consumable consumable) {
        consumeCounter++;
        if (consumeCounter >= maxConsume) {
            exitThread = true;
        }
        System.out.println("ConsumerThread" + Thread.currentThread().getId() + " - consumed consumable - " + consumable.toString() + " / Total: " + consumeCounter);
    }

    public void run() {
        System.out.println("Started consumer thread with ID: " + Thread.currentThread().getId());
        while (!exitThread) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignore
            }
            Producer.queueForConsume(this);
        }
        System.out.println("Exiting consumer thread with ID: " + Thread.currentThread().getId());
    }
}
