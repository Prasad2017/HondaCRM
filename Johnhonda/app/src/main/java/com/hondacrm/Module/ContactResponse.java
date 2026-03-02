package com.hondacrm.Module;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContactResponse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ContactType")
    @Expose
    private String contactType;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Phone")
    @Expose
    private String phone;
    @SerializedName("MailId")
    @Expose
    private String mailId;
    @SerializedName("Address")
    @Expose
    private String address;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
