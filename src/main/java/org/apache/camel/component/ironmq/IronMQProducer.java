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

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The IronMQ producer.
 */
public class IronMQProducer extends DefaultProducer {
    private static final transient Logger LOG = LoggerFactory.getLogger(IronMQProducer.class);
    private IronMQEndpoint endpoint;

    public IronMQProducer(IronMQEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
    	String body = exchange.getIn().getBody(String.class);
        LOG.trace("Sending request [{}] from exchange [{}]...", body, exchange);
        IronMQConfiguration configuration = endpoint.getConfiguration();
        if (IronMQConstants.CLEARQUEUE.equals(exchange.getIn().getHeader(IronMQConstants.OPERATION))) {
        	endpoint.getQueue().clearQueue();
        }
        else {
        	String id = endpoint.getQueue().push(body, configuration.getTimeout(), configuration.getVisibilityDelay(), configuration.getExpiresIn());
        	LOG.trace("Received id [{}]", id);
        	Message message = getMessageForResponse(exchange);
        	message.setHeader(IronMQConstants.MESSAGE_ID, id);
        }
    }
    
    private Message getMessageForResponse(Exchange exchange) {
        if (exchange.getPattern().isOutCapable()) {
            Message out = exchange.getOut();
            out.copyFrom(exchange.getIn());
            return out;
        }
        
        return exchange.getIn();
    }

}
