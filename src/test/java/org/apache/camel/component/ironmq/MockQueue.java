package org.apache.camel.component.ironmq;

import io.iron.ironmq.Client;
import io.iron.ironmq.EmptyQueueException;
import io.iron.ironmq.HTTPException;
import io.iron.ironmq.Message;
import io.iron.ironmq.MessageOptions;
import io.iron.ironmq.Messages;
import io.iron.ironmq.Queue;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

public class MockQueue extends Queue {
    Map<String, Message> messages = new LinkedHashMap<String, Message>();

    public MockQueue(Client client, String name) {
        super(client, name);
    }

    @Override
    public String push(String msg, long delay, long expiresIn) throws IOException {
        String randint = new BigInteger(24 * 8, new Random()).toString(16);
        Message message = new Message();
        message.setBody(msg);
        message.setDelay(delay);
        message.setExpiresIn(expiresIn);
        message.setId(randint);
        message.setReservationId(UUID.randomUUID().toString());
        messages.put(randint, message);
        return randint;
    }

    @Override
    public void deleteMessage(String id) throws IOException {
        if (messages.containsKey(id)) {
            messages.remove(id);
        } else
            throw new HTTPException(404, "not found");
    }

    @Override
    public void deleteMessages(Messages messages) throws IOException {
        MessageOptions[] messageOptions = messages.toMessageOptions();
        for (int i = 0; i < messageOptions.length; i++) {
            deleteMessage(messageOptions[i].getId());
        }
    }

    @Override
    public Message peek() throws IOException {
        if (messages.size() > 0) {
            return messages.entrySet().iterator().next().getValue();
        }
        throw new EmptyQueueException();
    }

    @Override
    public Message reserve() throws IOException {
        if (messages.size() > 0) {
            Entry<String, Message> next = messages.entrySet().iterator().next();
            return next.getValue();
        }
        throw new EmptyQueueException();
    }

    @Override
    public Messages reserve(int numberOfMessages) throws IOException {
        return reserve(numberOfMessages, 120);
    }

    @Override
    public Messages reserve(int numberOfMessages, int timeout, int wait) throws IOException {
        if (messages.size() > 0) {

            Iterator<Entry<String, Message>> iterator = messages.entrySet().iterator();
            int i = 0;
            List<Message> list = new ArrayList<Message>();
            while (iterator.hasNext() && i < numberOfMessages) {
                Entry<String, Message> next = iterator.next();
                list.add(next.getValue());
                i++;
            }
            Messages messages = new Messages(list.toArray(new Message[list.size()]));
            return messages;
        }
        throw new EmptyQueueException();
    }

    void add(Message message) {
        messages.put(message.getId(), message);
    }
}
