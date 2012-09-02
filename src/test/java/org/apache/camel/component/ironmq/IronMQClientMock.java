package org.apache.camel.component.ironmq;

import io.iron.ironmq.Client;
import io.iron.ironmq.MockQueue;
import io.iron.ironmq.Queue;

public class IronMQClientMock extends Client {

	public IronMQClientMock(String projectId, String token) {
		super(projectId, token);
	}
	
	@Override
	public Queue queue(String name) {
		return new MockQueue(this, name);
	}
	
	
}
