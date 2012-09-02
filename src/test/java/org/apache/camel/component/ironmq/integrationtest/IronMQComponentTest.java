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
package org.apache.camel.component.ironmq.integrationtest;

import junit.framework.Assert;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class IronMQComponentTest extends CamelTestSupport {

    @Test
    public void testIronMQ() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);       
        template.sendBody("direct:test", "some payload");
        
        assertMockEndpointsSatisfied();
        String id = mock.getExchanges().get(0).getIn().getHeader("MESSAGE_ID", String.class);
        Assert.assertNotNull(id);
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:test")
                  .to("ironmq:testqueue?projectId=504320cad9a68d7f2b001108&token=-Yrzcp1qgDtv9EmzkXQIaB6Vh40")
                  .to("mock:result");
            }
        };
    }
}
