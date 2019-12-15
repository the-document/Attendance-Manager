package com.example.nguyenhongphuc98.checkmein.UI.user;

public interface IInforFragmentView {

    final int CODE_NOT_FOUND=0;
    final int CODE_SUCCESS=1;

    void OnInitInfo();

    void OnSaveDisplayName(String _displayName);
    void OnQueryData();

    void OnShowEvent(int code);
}