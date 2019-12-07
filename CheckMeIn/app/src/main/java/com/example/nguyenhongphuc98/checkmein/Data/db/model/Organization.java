package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String name;
    private String id;
    private String avatar;
    private String description;
    private String userId;
    private ArrayList<String> collaborator;

    //to access db
    public Boolean Save(){
        return DataManager.Instance().SaveOrgan(this);
    }


    //geter and seter
    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

    public String getUserId() {
        return userId;
    }

    public ArrayList getCollaborator() {
        return collaborator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCollaborator(ArrayList<String> collaborator) {
        this.collaborator = collaborator;
    }
}
