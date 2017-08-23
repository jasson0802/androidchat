package com.itcr.chat;

import java.util.Date;

/**
 * Created by Jason on 22/8/2017.
 */

public class Message {
    private String SenderPhone;
    private Contact ReceiverPhone;
    private String Message;
    private Date SavedHour;

    public Message(){


    }

    public String getSenderPhone() {
        return SenderPhone;
    }

    public Contact getReceiverPhone() {
        return ReceiverPhone;
    }

    public void setReceiverPhone(Contact receiverPhone) {
        ReceiverPhone = receiverPhone;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Date getSavedHour() {
        return SavedHour;
    }

    public void setSavedHour(Date savedHour) {
        SavedHour = savedHour;
    }

    public void setSenderPhone(String senderPhone) {
        SenderPhone = senderPhone;
    }
}
