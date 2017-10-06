package com.mph.chatcontrol.network;
/* Created by macmini on 29/09/2017. */

import com.google.firebase.database.PropertyName;
import com.mph.chatcontrol.data.Guest;
import com.mph.chatcontrol.utils.DateUtils;

public class RestGuest {

    @PropertyName("id")
    public String id;

    @PropertyName("email")
    public String email;

    @PropertyName("end_date")
    public String endDate;

    @PropertyName("name")
    public String name;

    @PropertyName("phone")
    public String phone;

    @PropertyName("related_room_id")
    public String relatedRoomID;

    @PropertyName("start_date")
    public String startDate;

    public RestGuest() {
    }

    public RestGuest(Guest guest) {
        id = guest.getId();
        email = guest.getEmail();
        endDate = guest.getEndDate().toString();
        startDate = guest.getStartDate().toString();
        name = guest.getName();
        phone = guest.getPhone();
        relatedRoomID = guest.getRelatedRoomID();
    }

    public Guest toGuest() {
        Guest guest = new Guest();
        guest.setId(getId());
        guest.setEmail(getEmail());
        guest.setName(name);
        guest.setPhone(phone);
        guest.setEndDate(DateUtils.stringToDateISO8601(getEndDate()));
        guest.setStartDate(DateUtils.stringToDateISO8601(getStartDate()));
        guest.setRelatedRoomID(getRelatedRoomID());

        return guest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getRelatedRoomID() {
        return relatedRoomID;
    }

    public void setRelatedRoomID(String relatedRoomID) {
        this.relatedRoomID = relatedRoomID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
