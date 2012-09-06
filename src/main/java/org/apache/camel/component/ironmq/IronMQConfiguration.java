package org.apache.camel.component.ironmq;

public class IronMQConfiguration {
	private String projectId;
	private String token;
	private String queueName;
	private int timeout = 0;
	private int maxMessagesPerPoll = 1;
	
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
}
