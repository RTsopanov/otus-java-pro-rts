package rts.service;

import rts.message.MessageStatus;
import rts.util.JsonUtil;

import java.io.File;
import java.util.List;

public class ServiceToJsonFile {
    JsonUtil jsonUtil = new JsonUtil();

    public void toJson(List<MessageStatus> message) {
        jsonUtil.toJson(new File("hw8-serialization/f2.json"), message);
    }
}