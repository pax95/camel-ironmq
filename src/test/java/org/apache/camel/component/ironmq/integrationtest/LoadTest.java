package org.apache.camel.component.ironmq.integrationtest;

import io.iron.ironmq.Cloud;

import java.util.concurrent.TimeUnit;

import javax.naming.Context;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.DataSetSupport;
import org.apache.camel.component.ironmq.IronMQConstants;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore("Integration test that requires ironmq account.")
public class LoadTest extends CamelTestSupport {
    // replace with your project id
    private String projectId = "replace-this";
    // replace with your token
    private String token = "replace-this";
    // ironmq cloud to run test on
    private Cloud cloud = Cloud.ironAWSEUWest;
    private final static String CLOUD_KEY = "iron-cloud";
    private final String ironMQEndpoint = "ironmq:testqueue?preserveHeaders=true&projectId=" + projectId + "&token=" + token + "&maxMessagesPerPoll=100&delay=3000&ironMQCloud=#"
                                          + CLOUD_KEY;
    private final String datasetEndpoint = "dataset:foo?produceDelay=5";
    protected InputDataset dataSet = new InputDataset(1000);

    @Before
    public void clearQueue() {
        // make sure the queue is empty before test
        template.sendBodyAndHeader(ironMQEndpoint, null, IronMQConstants.OPERATION, IronMQConstants.CLEARQUEUE);
    }

    @Test
    public void testDataSet() throws Exception {
        MockEndpoint endpoint = getMockEndpoint(datasetEndpoint);
        endpoint.expectedMessageCount((int)dataSet.getSize());

        assertMockEndpointsSatisfied(4, TimeUnit.MINUTES);
    }

    @Override
    protected Context createJndiContext() throws Exception {
        Context context = super.createJndiContext();
        context.bind("foo", dataSet);
        context.bind(CLOUD_KEY, cloud);
        return context;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() throws Exception {
                from(datasetEndpoint).to(ironMQEndpoint);
                from(ironMQEndpoint).to(datasetEndpoint);
            }
        };
    }

    public class InputDataset extends DataSetSupport {

        public InputDataset(int size) {
            super(size);
        }

        @Override
        protected Object createMessageBody(long messageIndex) {
            return "<hello>" + messageIndex;
        }

    }
}
