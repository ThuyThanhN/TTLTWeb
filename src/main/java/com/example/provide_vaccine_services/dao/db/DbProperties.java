package com.example.provide_vaccine_services.dao.db;

import java.io.IOException;
import java.util.Properties;

public class DbProperties {
    private static Properties prop = new Properties();
    static {
        try {
            prop.load((DbProperties.class).getClassLoader().getResourceAsStream("db.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String host(){
        return prop.get("db.host").toString();
    };
    public static int port(){
        try {
            return Integer.parseInt(prop.get("db.port").toString());
        } catch (NumberFormatException e) {
            return 3306;
        }
    };
    public static String username(){
        return prop.get("db.user").toString();
    };
    public static String password(){
        return prop.get("db.password").toString();
    };
    public static String dbname(){
        return prop.get("db.dbname").toString();
    };
    public static String option(){
        return prop.get("db.option").toString();
    };

    public static void main(String[] args) {
        System.out.println(DbProperties.dbname());
    }
}
