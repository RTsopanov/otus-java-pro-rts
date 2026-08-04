package rts.service;

import rts.message.MessageStatus;

import java.util.List;

public class MessageService {
    ServiceFromJsonFile reader = new ServiceFromJsonFile();
    ServiceToJsonFile writer = new ServiceToJsonFile();
    ServiceUpdate update = new ServiceUpdate();

    public void process() {
        List<MessageStatus> message = reader.fromJson();
        update.updateStatus(message);
        update.updateErrorMessage(message);
        writer.toJson(message);
    }
}