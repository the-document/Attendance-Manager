package com.example.nguyenhongphuc98.checkmein.Data;

public class DataCenter {
    public static String OrganID;
    public static String EventID;
    public static String UserID="16520951";
    public static TypeAction typeAction= TypeAction.CREATE;



    public enum TypeAction{
        EDIT,
        CREATE,
        DELETE
    }
}
