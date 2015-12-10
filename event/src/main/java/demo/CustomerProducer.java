package demo;

import java.util.function.Consumer;

import event.api.Topic;
import event.api.TopicSender;

public class CustomerProducer {

    @Topic("my")
    Consumer<Customer> sender;
    
    TopicSender topicSender;

    public void testSending() {
        sender.accept(new Customer());
        topicSender.send("my", new Customer());
    }
}
