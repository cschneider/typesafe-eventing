package event.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import event.api.TopicSender;

public class Broker implements TopicSender{
    Map<String, Set<Consumer<Object>>> listeners = new HashMap<String, Set<Consumer<Object>>>();

    public void addListener(String topic, Consumer<Object> listener) {
        Set<Consumer<Object>> listenerList = listeners.get(topic);
        if (listenerList == null) {
            listenerList = new HashSet<>();
        }
        listenerList.add(listener);
        listeners.put(topic, listenerList);
    }
    
    public void removeListener(String topic, Consumer<?> listener) {
        listeners.remove(listener);
    }

    @Override
    public void send(String topic, Object data) {
        Set<Consumer<Object>> listenerList = listeners.get(topic);
        if (listenerList == null) {
            return;
        }    
        for (Consumer<Object> consumer : listenerList) {
            consumer.accept(data);
        }
    }

	
}
