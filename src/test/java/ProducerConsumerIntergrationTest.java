import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mnoe on 29.11.2016.
 */

@Test
public class ProducerConsumerIntergrationTest {

    public void testProducerConsumerIntegration() throws InterruptedException {
        List<Thread> producerPool = new ArrayList<Thread>();
        List<Thread> consumerPool = new ArrayList<Thread>();

        for(int i = 0; i < 2; i++){
            Thread producerThread = new Thread(new Producer());
            producerThread.start();
            producerPool.add(producerThread);
        }

        for(int i = 0; i < 5; i++){
            Thread consumerThread = new Thread(new ConsumerImpl());
            consumerThread.start();
            consumerPool.add(consumerThread);
        }

        producerPool.get(0).join();
    }
}
