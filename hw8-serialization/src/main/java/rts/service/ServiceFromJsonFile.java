package rts.service;

import rts.message.MessageStatus;
import rts.util.JsonUtil;

import java.io.File;
import java.util.List;

public class ServiceFromJsonFile {
    JsonUtil jsonUtil = new JsonUtil();

    public List<MessageStatus> fromJson() {
        return jsonUtil.fromJson(new File("hw8-serialization/f1.json"), MessageStatus.class);
    }

}