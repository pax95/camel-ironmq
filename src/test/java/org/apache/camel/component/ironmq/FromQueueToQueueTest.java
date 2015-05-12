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

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.iron.ironmq.Client;
import io.iron.ironmq.EmptyQueueException;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class FromQueueToQueueTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:result")
    private MockEndpoint result;

    private IronMQEndpoint queue1;

    private IronMQEndpoint queue2;

    @Test
    public void shouldDeleteMessageFromQueue1() throws Exception {

        result.setExpectedMessageCount(1);

        template.send("direct:start", ExchangePattern.InOnly, new Processor() {
            public void process(Exchange exchange) throws Exception {
                exchange.getIn().setBody("This is my message text.");
            }
        });

        assertMockEndpointsSatisfied();

        try {
            queue1.getQueue().get();
            fail("Message was in the first queue!");
        } catch (IOException e) {
            if (!(e instanceof EmptyQueueException)) {
                // Unexpected exception.
                throw e;
            }
        }

        try {
            queue2.getQueue().get();
            fail("Message remained in second queue!");
        } catch (IOException e) {
            if (!(e instanceof EmptyQueueException)) {
                // Unexpected exception.
                throw e;
            }
        }
    }

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        IronMQComponent component = new IronMQComponent(context);
        queue1 = generateEndpoint(component, "testqueue");
        queue2 = generateEndpoint(component, "testqueue2");
        context.addComponent("ironmq", component);
        return context;
    }

    private IronMQEndpoint generateEndpoint(IronMQComponent component, String queueName) throws Exception {
        Client mockClient = new IronMQClientMock("dummy", "dummy");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("client", mockClient);
        IronMQEndpoint endpoint = (IronMQEndpoint)component.createEndpoint("ironmq", queueName, parameters);
        return endpoint;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:start").to(queue1);
                from(queue1).to(queue2);
                from(queue2).to("mock:result");
            }
        };
    }

}
