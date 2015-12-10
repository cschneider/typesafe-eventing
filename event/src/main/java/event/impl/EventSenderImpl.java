package event.impl;

import java.util.function.Consumer;

import event.api.TopicSender;

public class EventSenderImpl<T> implements Consumer<T> {
    private TopicSender sender;
    private String topic;

    public EventSenderImpl(TopicSender sender, String topic) {
        this.sender = sender;
        this.topic = topic;
    }

    @Override
    public void accept(T data) {
        sender.send(topic, data);
    }

}
