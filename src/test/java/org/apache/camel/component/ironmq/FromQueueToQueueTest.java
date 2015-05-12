package org.apache.camel.component.ironmq;

import io.iron.ironmq.Client;
import io.iron.ironmq.EmptyQueueException;
import org.apache.camel.*;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DKolb on 5/12/15.
 */
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
            if(!(e instanceof EmptyQueueException)) {
                // Unexpected exception.
                throw e;
            }
        }

        try {
            queue2.getQueue().get();
            fail("Message remained in second queue!");
        } catch (IOException e) {
            if(!(e instanceof EmptyQueueException)) {
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
