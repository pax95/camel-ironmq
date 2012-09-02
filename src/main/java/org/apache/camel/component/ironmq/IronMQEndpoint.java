/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.ironmq;

import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;
import io.iron.ironmq.Queue;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.ScheduledPollEndpoint;

/**
 * Represents a IronMQ endpoint.
 */
public class IronMQEndpoint extends ScheduledPollEndpoint {
	private Client client;
	private IronMQConfiguration configuration;
	private Queue queue;

    public IronMQEndpoint(String uri, IronMQComponent component, IronMQConfiguration ironMQConfiguration) {
        super(uri, component);
        this.configuration = ironMQConfiguration;
        this.queue = getClient().queue(configuration.getQueueName());
    }

    public Producer createProducer() throws Exception {
        return new IronMQProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        return new IronMQConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
    
    public Queue getQueue() {
		return queue;
	}
    
    @Override
    protected void doStart() throws Exception {
    	super.doStart();
    	client = getClient();
    }
    
    @Override
    protected void doStop() throws Exception {
    	client = null;
    }
    
    public Client getClient() {
        if (client == null) {
            client = createClient();
        }
        return client;
    }
    
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Provide the possibility to override this method for an mock implementation
     * @return Client
     */
    Client createClient() {
    	client = new Client(configuration.getProjectId(), configuration.getToken(), Cloud.ironAWSUSEast);
    	return client;
    }

}
