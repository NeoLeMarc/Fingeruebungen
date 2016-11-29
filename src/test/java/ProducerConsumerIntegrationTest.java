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
public class ProducerConsumerIntegrationTest {

    public void testProducerConsumer() throws InterruptedException {
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();
        runThreads(producers, consumers, 10, 4, 2, 5);
    }

    public void testThatAllMessagesAreConsumed() throws InterruptedException {
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();
        runThreads(producers, consumers, 10, 4, 2, 5);
        assertThat(Producer.getConsumableCount(), is(0));

        int sumOfConsumedMessages = getSumOfConsumedMessages(consumers);
        int sumOfProducedMessages = getSumOfProducedMessages(producers);

        assertThat(sumOfConsumedMessages, is(sumOfProducedMessages));
    }

    private int getSumOfProducedMessages(List<Producer> producers) {
        int sumOfProducedMessages = 0;
        for(Producer producer: producers){
            sumOfProducedMessages += producer.getProducedCounter();
        }
        return sumOfProducedMessages;
    }

    private int getSumOfConsumedMessages(List<Consumer> consumers) {
        int sumOfConsumedMessages = 0;
        for (Consumer consumer: consumers ) {
            sumOfConsumedMessages += consumer.getConsumedCounter();
        }
        return sumOfConsumedMessages;
    }

    public void testThatHavingTooManyProducersLeadsToLeftOverMessages() throws InterruptedException {
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();
        runThreads(producers, consumers, 10, 4, 3, 5);
        assertThat(Producer.getConsumableCount(), greaterThan(0));
    }
    public void testThatHavingNotEnoughConsumersLeadsToLeftOverMessages() throws InterruptedException {
        List<Producer> producers = new ArrayList<>();
        List<Consumer> consumers = new ArrayList<>();
        runThreads(producers, consumers, 10, 4, 2, 4);
        assertThat(Producer.getConsumableCount(), greaterThan(0));
    }


    private void runThreads(List<Producer> producers, List<Consumer> consumers, int produceCount, int consumeCount, int numberOfProducers, int numberOfConsumers) throws InterruptedException {
        List<Thread> producerPool = new ArrayList<>();
        List<Thread> consumerPool = new ArrayList<>();

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
