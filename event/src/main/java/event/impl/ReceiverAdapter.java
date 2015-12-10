package event.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import event.api.Topic;

public class ReceiverAdapter implements Consumer<Object> {
    private Object receiver;
    private Broker broker;
    Map<Class<?>, Method> receiverMethods = new HashMap<Class<?>, Method>();

    public ReceiverAdapter(Object receiver, Broker broker) {
        this.receiver = receiver;
        this.broker = broker;
    }
    
    public void start() {
        iterateReceiveMethods( (Method method) -> {
            Topic topic = method.getAnnotation(Topic.class);
            Class<?> dataClass = method.getParameterTypes()[0];
            receiverMethods.put(dataClass, method);
            broker.addListener(topic.value(), this);
        });
    }
    
    public void stop() {
        iterateReceiveMethods( (Method method) -> {
            Class<?> dataClass = method.getParameterTypes()[0];
            Topic topic = method.getAnnotation(Topic.class);
            receiverMethods.remove(dataClass, method);
            broker.removeListener(topic.value(), this);
        });
    }
    
    private void iterateReceiveMethods(Consumer<Method> methodCallback) {
        Method[] methods = receiver.getClass().getMethods();
        for (Method method : methods) {
            Topic topic = method.getAnnotation(Topic.class);
            if (topic != null && topic.value() != null) {
                methodCallback.accept(method);
            }
        }
    }

    public void accept(Object obj) {
        Class<? extends Object> dataClass = obj.getClass();
        Method method = receiverMethods.get(dataClass);
        if (method != null) {
            try {
                method.invoke(receiver, new Object[]{obj});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
