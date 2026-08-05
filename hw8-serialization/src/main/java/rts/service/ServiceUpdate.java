package rts.service;

import rts.message.MessageStatus;

import java.util.List;

public class ServiceUpdate {

    public void updateStatus(List<MessageStatus> message) {
        message.stream()
                .filter(v -> v.getStatus().equalsIgnoreCase("pending"))
                .forEach(v -> v.setStatus("SUCCESS"));
    }

    public void updateErrorMessage(List<MessageStatus> message) {
        message.stream()
                .filter(v -> v.getStatus().equalsIgnoreCase("FAILED"))
                .forEach(v -> v.setErrorMessage("Invalid phone number"));
    }
}