import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mnoe on 29.11.2016.
 */
public class Producer implements Runnable {
    private static Object consumerLock = new Object() {
    };
    private static ArrayBlockingQueue<Consumable> consumables = new ArrayBlockingQueue<Consumable>(Producer.MAX_CONSUMABLES);

    public static final int MAX_CONSUMABLES = 50;

    private int maxProduce = 0;

    public int getProducedCounter() {
        return producedCounter;
    }

    private int producedCounter = 0;
    private boolean exitThread = false;

    public Producer(int maxProduce) {
        this.maxProduce = maxProduce;
    }

    static void queueForConsume(Consumer consumer) {
        processConsumer(consumer);
    }

    private void produce() {

        Consumable consumable = new Consumable() { };

        produceCriticalSection(consumable);
        synchronized (consumerLock) {
            consumerLock.notifyAll();
        }

        producedCounter++;
        if (producedCounter >= maxProduce) {
            exitThread = true;
        }
    }

    private void produceCriticalSection(Consumable consumable) {
        synchronized (consumables) {
            while (consumables.remainingCapacity() <= 0) {
                try {
                    consumables.wait();
                } catch (InterruptedException e) {
                    // Continue
                }
            }

            consumables.add(consumable);
        }
    }

    private static void processConsumer(Consumer consumer) {
        processConsumerCriticalSection(consumer);

        synchronized (consumables) {
            consumables.notifyAll();
        }
    }

    private static void processConsumerCriticalSection(Consumer consumer) {

        synchronized (consumerLock) {
            while (consumables.size() == 0) {
                try {
                    consumerLock.wait();
                } catch (InterruptedException e) {
                    // Continue
                }
            }

            Consumable consumable = null;

            while (consumable == null) {
                consumable = consumables.poll();
            }
            consumer.consume(consumable);
        }
    }

    public void run() {
        while (!exitThread) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                // Ignore
            }
            produce();
        }
    }

    public static int getConsumableCount(){
        return consumables.size();
    }
}
