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

    public int getConsumedCounter() {
        return consumeCounter;
    }

    public void consume(Consumable consumable) {
        consumeCounter++;
        if (consumeCounter >= maxConsume) {
            exitThread = true;
        }
    }

    public void run() {
        while (!exitThread) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // Ignore
            }
            Producer.queueForConsume(this);
        }
    }
}
