package org.apache.camel.component.ironmq;

import io.iron.ironmq.Client;
import io.iron.ironmq.Queue;

public class IronMQClientMock extends Client {
	private Queue queue;

	public IronMQClientMock(String projectId, String token) {
		super(projectId, token);
	}
	
	@Override
	public Queue queue(String name) {
		if (queue == null) {
			queue = new MockQueue(this, name);
		}
		return queue;
	}
	
	
}
