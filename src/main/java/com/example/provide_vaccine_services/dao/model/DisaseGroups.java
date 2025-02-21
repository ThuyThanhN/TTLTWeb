package com.example.provide_vaccine_services.dao.model;

import java.io.Serializable;

public class DisaseGroups implements Serializable {
    private int id;
    private String name;

    public DisaseGroups() {
    }

    public DisaseGroups(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DisaseGroups(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
