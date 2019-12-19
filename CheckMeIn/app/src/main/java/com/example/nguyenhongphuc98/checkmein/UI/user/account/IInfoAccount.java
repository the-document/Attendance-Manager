package com.example.nguyenhongphuc98.checkmein.UI.user.account;

public interface IInfoAccount {
    public static int CODE_UPDATE_PHONE_FAIL=0;
    public static int CODE_UPDATE_PHONE_SUCCESS=1;

    void OnInitAccountInfo();
    void OnUpdatePhoneNumber();

    void OnUpdatePhoneResult(int code);
}
