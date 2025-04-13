package com.example.provide_vaccine_services.dao.model;

public class Permissions {
    public static final int EXECUTE = 1;  // Quyền thực thi (1)
    public static final int WRITE = 2;    // Quyền ghi (2)
    public static final int READ = 4;     // Quyền đọc (4)

    private int id;
    private String module;
    private int permission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    //
    public boolean canExecute() {
        return (permission & EXECUTE) != 0;
    }

    public boolean canWrite() {
        return (permission & WRITE) != 0;
    }

    public boolean canRead() {
        return (permission & READ) != 0;
    }

}
