package org.apache.camel.component.ironmq;

public class IronMQConfiguration {
	//consumer and producer
	private String projectId;
	//consumer and producer
	private String token;
	//consumer and producer
	private String queueName;
	//consumer and producer
	private String ironMQEndpoint;
	//consumer and producer
	private int timeout = 60;
	//consumer
	private int maxMessagesPerPoll = 1;
	//producer expires_in
	private int expiresIn = 604800;
	//producer delay
	private int visibilityDelay = 0;
	
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
	
	public void setIronMQEndpoint(String ironMQEndpoint) {
		this.ironMQEndpoint = ironMQEndpoint;
	}
	
	public String getIronMQEndpoint() {
		return ironMQEndpoint;
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
