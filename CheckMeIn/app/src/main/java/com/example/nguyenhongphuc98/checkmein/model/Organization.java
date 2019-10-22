package com.example.nguyenhongphuc98.checkmein.model;

import java.util.ArrayList;

public class Organization {
    private String name;
    private int id;
    private String avatar;
    private String description;
    private int userId;
    private ArrayList<Integer> collaborator;

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

    public int getUserId() {
        return userId;
    }

    public ArrayList getCollaborator() {
        return collaborator;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCollaborator(ArrayList<Integer> collaborator) {
        this.collaborator = collaborator;
    }
}
