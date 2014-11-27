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

import org.apache.camel.spi.UriParam;
import org.apache.camel.spi.UriParams;

@UriParams
public class IronMQConfiguration {
    // consumer and producer
    private Client client;
    @UriParam
    private String projectId;
    @UriParam
    private String token;
    @UriParam
    private String queueName;
    @UriParam
    private String ironMQCloud = "https://mq-aws-us-east-1.iron.io";
    @UriParam
    private int timeout = 60;
    @UriParam
    private int maxMessagesPerPoll = 1;
    @UriParam
    private int expiresIn = 604800;
    @UriParam
    private int visibilityDelay;
    @UriParam
    private boolean preserveHeaders;
    @UriParam
    private boolean batchDelete;
    @UriParam
    private int wait;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setIronMQCloud(String ironMQCloud) {
        this.ironMQCloud = ironMQCloud;
    }

    public String getIronMQCloud() {
        return ironMQCloud;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getMaxMessagesPerPoll() {
        return maxMessagesPerPoll;
    }

    public void setMaxMessagesPerPoll(int maxMessagesPerPoll) {
        this.maxMessagesPerPoll = maxMessagesPerPoll;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public int getVisibilityDelay() {
        return visibilityDelay;
    }

    public void setVisibilityDelay(int visibilityDelay) {
        this.visibilityDelay = visibilityDelay;
    }

    public boolean isPreserveHeaders() {
        return preserveHeaders;
    }

    public void setPreserveHeaders(boolean preserveHeaders) {
        this.preserveHeaders = preserveHeaders;
    }

    public boolean isBatchDelete() {
        return batchDelete;
    }

    public void setBatchDelete(boolean batchDelete) {
        this.batchDelete = batchDelete;
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }
}
