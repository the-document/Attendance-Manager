package com.example.nguyenhongphuc98.checkmein.Data.db.model;

import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;

public class Collaborator {
    String organ;
    String collaborator;
    String name;

    public Collaborator(String collaborator,String n,String organ) {

        this.collaborator = collaborator;
        this.name=n;
        this.organ=organ;
    }

    public String getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(String collaborator) {
        this.collaborator = collaborator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    //-------------------------------------
    public boolean Save(){
        if(collaborator.isEmpty())
            return  false;

        return DataManager.Instance().SaveCollaborator(this);
    }
}
