package demo;

import event.impl.Broker;
import event.impl.EventSenderImpl;
import event.impl.ReceiverAdapter;

public class Starter {

    public static void main(String[] args) {
        Broker broker = new Broker();
        CustomerProducer producer = new CustomerProducer();
        producer.sender = new EventSenderImpl<Customer>(broker, "my");
        producer.topicSender = broker;
        CustomerReceiver receiver = new CustomerReceiver();
        ReceiverAdapter adapter = new ReceiverAdapter(receiver, broker);
        adapter.start();
     
        producer.testSending();
    }

}
