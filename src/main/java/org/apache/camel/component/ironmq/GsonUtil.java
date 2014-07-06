package org.apache.camel.component.ironmq;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import org.apache.camel.Message;

public class GsonUtil {
    private static final Gson GSON = new Gson();

    static class IronMqMessage {
        private Map<String, Object> headers = new HashMap<String, Object>();
        private String body;

        public IronMqMessage(String body, Map<String, Object> headers) {
            super();
            this.headers = headers;
            this.body = body;
        }

        public Map<String, Object> getHeaders() {
            return headers;
        }

        public String getBody() {
            return body;
        }
    }

    static String getBodyFromMessage(Message message) {
        IronMqMessage ironMessage = new IronMqMessage(message.getBody(String.class), message.getHeaders());
        return GSON.toJson(ironMessage);
    }

    static void copyFrom(io.iron.ironmq.Message source, Message target) {
        IronMqMessage ironMqMessage = GSON.fromJson(source.getBody(), IronMqMessage.class);
        target.setBody(ironMqMessage.getBody());
        if (ironMqMessage.getHeaders() != null) {
            for (Map.Entry<String, Object> entry : ironMqMessage.getHeaders().entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                if (target.getHeader(key) == null) {
                    target.setHeader(key, value);
                }
            }
        }
    }
}
