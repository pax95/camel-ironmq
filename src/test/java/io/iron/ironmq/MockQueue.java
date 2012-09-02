package io.iron.ironmq;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class MockQueue extends Queue {
    Map<String, String> messages = new HashMap<String, String>();
    
	public MockQueue(Client client, String name) {
		super(client, name);
	}
	
	@Override
	public String push(String msg) throws IOException {
		String randint = new BigInteger(24 * 8, new Random()).toString(16);
		messages.put(randint, msg);
		return randint;
	}
	
	@Override
	public void deleteMessage(String id) throws IOException {
		if (messages.containsKey(id)) {
			messages.remove(id);
		}
		else throw new HTTPException(404, "not found");
	}
	
	@Override
	public Message get() throws IOException {
		if (messages.size() > 0) {
			Entry<String, String> next = messages.entrySet().iterator().next();
			Message message = new Message();
			message.setBody(next.getValue());
			message.setId(next.getKey());
			return message;
		}
		throw new EmptyQueueException();
	}
}
