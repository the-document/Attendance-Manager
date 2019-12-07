package com.example.nguyenhongphuc98.checkmein.Data;

public class DataCenter {
    public static String OrganID;
    public static String EventID;
    public static String UserID="16520951";
    public static TypeAction typeAction= TypeAction.CREATE;


    //get infor create new event
    public static String eventName="";
    public static String eventDay="";
    public static String eventBegin="";
    public static String eventEnd="";
    public static String eventCode="";
    public static String eventLocation="";

    public enum TypeAction{
        EDIT,
        CREATE,
        DELETE
    }
}
