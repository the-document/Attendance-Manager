package com.example.nguyenhongphuc98.checkmein.UI.login;

public interface LoginCallback {
    public static int CODE_LOGIN_INCORRECT=0;
    public static int CODE_LOGIN_SUCCESS=1;

    public void OnLoginComplete(int code);
    public void OnLoadPersonToDatacenterComplete();
}
