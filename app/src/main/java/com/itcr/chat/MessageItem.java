package com.itcr.chat;
/**
 * Created by Jason on 19/8/2017.
 */

public class MessageItem {

    private String MemberName;
    private String ProfilePicUrl;
    private String LastMessage;
    private String LastMessageTime;

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    private String PhoneNumber;

    public MessageItem(String member_name, String profile_pic_id, String last_message,
                   String last_message_time, String phone_number) {

        this.MemberName = member_name;
        this.ProfilePicUrl = profile_pic_id;
        this.LastMessage = last_message;
        this.LastMessageTime = last_message_time;
        this.PhoneNumber = phone_number;
    }

    public String getMemberName() {
        return MemberName;
    }

    public void setMemberName(String member_name) {
        this.MemberName = member_name;
    }

    public String getProfilePicUrl() {
        return ProfilePicUrl;
    }

    public void setProfilePicUrl(String profile_pic_id) {
        this.ProfilePicUrl = profile_pic_id;
    }

    public String getLastMessage() {
        return LastMessage;
    }

    public void setLastMessage(String status) {
        this.LastMessage = status;
    }

    public String getLastMessageTime() {
        return LastMessageTime;
    }

    public void setLastMessageTime(String lastMessageTime) {
        this.LastMessageTime = lastMessageTime;
    }
}
