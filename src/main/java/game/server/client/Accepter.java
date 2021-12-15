package game.server.client;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayDeque;

public class Accepter {
    final ArrayDeque<JSONObject> queue;

    public Accepter() {
        queue = new ArrayDeque<>();
    }


    public void submit(JSONObject message) {
        synchronized (queue) {
            message.put("timestamp", System.currentTimeMillis());
            queue.push(message);
        }
    }

    public JSONArray getMessage() {
        synchronized (queue) {
            JSONArray jsonArray = new JSONArray();
            jsonArray.addAll(queue);
            queue.clear();
            return jsonArray;
        }
    }
}
