package com.itcr.chat;
/**
 * Created by Jason on 19/8/2017.
 */

public class ContactItem {

    private String MemberName;
    private String ProfilePicUrl;
    private String LastMessage;
    private String LastMessageTime;
    private String PhoneNumber;

    public ContactItem(String member_name, String profile_pic_id, String status,
                       String contactType, String phoneNumber) {

        this.MemberName = member_name;
        this.ProfilePicUrl = profile_pic_id;
        this.LastMessage = status;
        this.LastMessageTime = contactType;
        this.PhoneNumber = phoneNumber;
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


    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }

    public String getLastMessage() {
        return "";
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
