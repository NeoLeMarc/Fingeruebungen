/**
 * Created by mnoe on 29.11.2016.
 */
public class ProducerImpl extends Producer {
    public static final int MAX__CONSUMERS = 50;
    public static final int MAX_CONSUMABLES = 50;

    private void produce(){
        Consumable consumable = new Consumable() {};
        System.out.println("Producer thread: " + Thread.currentThread().getId() + " just produced one consumable");
        processConsumers();
    }

    void processConsumers(){
        if(consumers.size() == 0 || consumables.size() == 0){
            return;
        }

        else {
            processConsumersCriticalSection();
        }
    }

    synchronized private void processConsumersCriticalSection(){
        Consumable consumable;
        while( (consumers.size() > 0) && (consumable = consumables.poll()) != null){
            Consumer consumer = consumers.poll();
            consumer.consume(consumable);
        }
    }

    public void run() {
        System.out.println("Started producer thread with ID: " + Thread.currentThread().getId());
        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Ignore
            }
            produce();
            processConsumers();
        }
    }
}
