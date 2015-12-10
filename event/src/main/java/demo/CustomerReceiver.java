package demo;

import java.io.IOException;
import java.util.EventListener;

import javax.transaction.Transactional;

import event.api.Topic;

public class CustomerReceiver implements EventListener {

    @Topic("my")
    @Transactional(dontRollbackOn = IOException.class)
    public void receive(Customer customer) {
        System.out.println(customer);
    }
}
