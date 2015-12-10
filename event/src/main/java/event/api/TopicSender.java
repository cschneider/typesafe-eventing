package event.api;

public interface TopicSender {
    void send(String topic, Object data);
}
