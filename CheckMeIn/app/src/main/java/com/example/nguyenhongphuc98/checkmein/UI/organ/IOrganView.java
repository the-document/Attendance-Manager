package com.example.nguyenhongphuc98.checkmein.UI.organ;

public interface IOrganView {

    final int CODE_CHANGE_PHOTO_FAIL= 1;
    final int CODE_CHANGE_PHOTO_SUCCESS= 2;
    final int CODE_SAVE_ORGAN_FAIL=3;
    final int CODE_SAVE_ORGAN_SUCCESS=4;
    final int CODE_INVALID_PARAMETER=5;

    void onChangePhotoClick();
    void onAddCollaboratorClick();
    void onSaveOrganClick();

    void showResult(String message);
    void onChangePhotoResult(int code);
    void onSaveOrganResult(int code);
}
