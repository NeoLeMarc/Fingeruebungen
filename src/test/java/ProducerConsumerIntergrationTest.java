import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.*;

/**
 * Created by mnoe on 29.11.2016.
 */

@Test
public class ProducerConsumerIntergrationTest {

    public void testProducerConsumer() throws InterruptedException {
        List<Producer> producers = new ArrayList<Producer>();
        List<Consumer> consumers = new ArrayList<Consumer>();
        runThreads(producers, consumers, 10, 4, 2, 5);
        System.out.println("All threads terminated");
    }

    public void testThatAllMessagesAreConsumed() throws InterruptedException {
        List<Producer> producers = new ArrayList<Producer>();
        List<Consumer> consumers = new ArrayList<Consumer>();
        runThreads(producers, consumers, 10, 4, 2, 5);
        System.out.println("All threads terminated");

        assertThat(Producer.getConsumableCount(), is(0));
    }

    public void testThatHavingTooManyProducersLeadsToLeftOverMessages() throws InterruptedException {
        List<Producer> producers = new ArrayList<Producer>();
        List<Consumer> consumers = new ArrayList<Consumer>();
        runThreads(producers, consumers, 10, 4, 3, 5);
        System.out.println("All threads terminated");

        assertThat(Producer.getConsumableCount(), greaterThan(0));
    }
    public void testThatHavingNotEnoughConsumersLeadsToLeftOverMessages() throws InterruptedException {
        List<Producer> producers = new ArrayList<Producer>();
        List<Consumer> consumers = new ArrayList<Consumer>();
        runThreads(producers, consumers, 10, 4, 2, 4);
        System.out.println("All threads terminated");

        assertThat(Producer.getConsumableCount(), greaterThan(0));
    }


    private void runThreads(List<Producer> producers, List<Consumer> consumers, int produceCount, int consumeCount, int numberOfProducers, int numberOfConsumers) throws InterruptedException {
        List<Thread> producerPool = new ArrayList<Thread>();
        List<Thread> consumerPool = new ArrayList<Thread>();

        for(int i = 0; i < numberOfProducers; i++){
            Producer producer = new Producer(produceCount);
            Thread producerThread = new Thread(producer);
            producerThread.start();
            producers.add(producer);
            producerPool.add(producerThread);
        }

        for(int i = 0; i < numberOfConsumers; i++){
            Consumer consumer = new Consumer(consumeCount);
            Thread consumerThread = new Thread(consumer);
            consumerThread.start();
            consumers.add(consumer);
            consumerPool.add(consumerThread);
        }

        for(Thread thread : producerPool){
            thread.join();
        }

        for(Thread thread : consumerPool){
            thread.join();
        }
    }
}
