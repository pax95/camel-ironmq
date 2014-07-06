package org.apache.camel.component.ironmq;

import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;

import org.apache.camel.spi.UriParam;

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
    private Cloud ironMQCloud = Cloud.ironAWSUSEast;
    @UriParam
    private int timeout = 60;
    @UriParam
    private int maxMessagesPerPoll = 1;
    @UriParam
    private int expiresIn = 604800;
    @UriParam
    private int visibilityDelay = 0;

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

    public void setIronMQCloud(Cloud ironMQCloud) {
        this.ironMQCloud = ironMQCloud;
    }

    public Cloud getIronMQCloud() {
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
}
