import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mnoe on 29.11.2016.
 */
public class Producer implements Runnable {

    private static Object consumerLock = new Object() {
    };
    private static ArrayBlockingQueue<Consumable> consumables = new ArrayBlockingQueue<Consumable>(Producer.MAX_CONSUMABLES);
    public static final int MAX_CONSUMABLES = 50;
    public static final int MAX__CONSUMERS = 50;

    static void queueForConsume(Consumer consumer) {
        System.out.println("Consumer " + consumer + " queued for consume!");
        processConsumer(consumer);
    }


    private void produce() {
        Consumable consumable = new Consumable() {};

        produceCriticalSection(consumable);
        synchronized (consumerLock) {
            consumerLock.notifyAll();
        }

        System.out.println("Producer thread: " + Thread.currentThread().getId() + " just produced one consumable");
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

    private static void processConsumer(Consumer consumer){
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
        System.out.println("Started producer thread with ID: " + Thread.currentThread().getId());
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignore
            }
            produce();
        }
    }
}
