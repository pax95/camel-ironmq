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

import io.iron.ironmq.EmptyQueueException;
import io.iron.ironmq.Message;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;
import org.apache.camel.spi.Synchronization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The IronMQ consumer.
 */
public class IronMQConsumer extends ScheduledPollConsumer {
	private static final transient Logger LOG = LoggerFactory.getLogger(IronMQConsumer.class);
	private final IronMQEndpoint endpoint;

	public IronMQConsumer(IronMQEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = endpoint;
	}

	@Override
	protected int poll() throws Exception {
		Exchange exchange = endpoint.createExchange();
		try {
			Message message = endpoint.getQueue().get();
			// create a message body
			exchange.getIn().setBody(message.getBody());
			exchange.getIn().setHeader(IronMQConstants.MESSAGE_ID, message.getId());
			// add on completion to handle after work when the exchange is done
			exchange.addOnCompletion(new Synchronization() {
				public void onComplete(Exchange exchange) {
					processCommit(exchange);
				}

				public void onFailure(Exchange exchange) {
					processRollback(exchange);
				}

				@Override
				public String toString() {
					return "IronMQConsumerOnCompletion";
				}
			});
			// send message to next processor in the route
			getProcessor().process(exchange);
			return 1; // number of messages polled
		} catch (EmptyQueueException e) {
			return 0;
		} finally {
			// log exception if an exception occurred and was not handled
			if (exchange.getException() != null) {
				getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
			}
		}
	}

	/**
	 * Strategy to delete the message after being processed.
	 * 
	 * @param exchange
	 *            the exchange
	 */
	protected void processCommit(Exchange exchange) {
		try {
			String messageid = exchange.getIn().getHeader(IronMQConstants.MESSAGE_ID, String.class);
			LOG.trace("Deleting message with id {}...", messageid);
			endpoint.getQueue().deleteMessage(messageid);
			LOG.trace("Message deleted");
		} catch (Exception e) {
			LOG.warn("Error occurred during deleting message", e);
			exchange.setException(e);
		}
	}

	/**
	 * Strategy when processing the exchange failed.
	 * 
	 * @param exchange
	 *            the exchange
	 */
	protected void processRollback(Exchange exchange) {
		Exception cause = exchange.getException();
		if (cause != null) {
			LOG.warn("Exchange failed, so rolling back message status: " + exchange, cause);
		} else {
			LOG.warn("Exchange failed, so rolling back message status: {}", exchange);
		}
	}

}
