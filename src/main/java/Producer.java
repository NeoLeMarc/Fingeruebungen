import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by mnoe on 29.11.2016.
 */
public abstract class Producer implements Runnable {

    static Queue<Consumer> consumers = new ArrayBlockingQueue<Consumer>(ProducerImpl.MAX__CONSUMERS);
    static Queue<Consumable> consumables = new ArrayBlockingQueue<Consumable>(ProducerImpl.MAX_CONSUMABLES);

    static void queueForConsume(Consumer consumer) {
        consumers.add(consumer);
        System.out.println("Consumer " + consumer + " queued for consume!");
    }
}
