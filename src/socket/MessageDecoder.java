package socket;

import com.google.gson.Gson;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(final String jsonMessage) throws DecodeException {
        Gson gson = new Gson();
        return gson.fromJson(jsonMessage, Message.class);
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        Gson gson = new Gson();
        try {
            gson.fromJson(jsonMessage, Message.class);
            return true;
        } catch (Exception e) {
            System.out.println("MessageDecoder - message decode failed");
            return false;
        }
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}