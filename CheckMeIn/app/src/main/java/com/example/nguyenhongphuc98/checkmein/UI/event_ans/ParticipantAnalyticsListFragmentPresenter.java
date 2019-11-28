package com.example.nguyenhongphuc98.checkmein.UI.event_ans;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.ParticipantAnswerDetails;
import com.example.nguyenhongphuc98.checkmein.R;

import java.util.ArrayList;

public class ParticipantAnalyticsListFragmentPresenter implements ParticipantAnalyticsListFragmentContract.ParticipantAnalyticsListFragmentPresenter {

    ParticipantAnalyticsListFragment view;

    @Override
    public void setView(ParticipantAnalyticsListFragment view) {
        this.view = view;
    }

    @Override
    public void loadParticipantAnalytics() {
        ArrayList<ParticipantAnswerDetails> dsTest = new ArrayList<>();

        ParticipantAnswerDetails desc1 = new ParticipantAnswerDetails("Lưu Biêu Nghị", 1, 100, 100, 100, 500);
        ParticipantAnswerDetails desc2 = new ParticipantAnswerDetails("Nguyễn Hồng Phúc", 1, 50, 50, 50, 152035);
        ParticipantAnswerDetails desc3 = new ParticipantAnswerDetails("ABC", 1, 50, 50, 50, 152035);

        dsTest.add(desc1);
        dsTest.add(desc2);
        dsTest.add(desc3);

        com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAnswerDetailsCustomAdapter padCustomAdapter = new com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAnswerDetailsCustomAdapter(view.getActivity(), R.layout.fragment_list_of_participant_analytics, dsTest);

        //Xong xuôi hết rồi thì phải set adapter cho bên view nữa chứ.
        view.setParticipantAnalyticsAdapter(padCustomAdapter);
    }

}
