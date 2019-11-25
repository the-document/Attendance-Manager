package com.example.nguyenhongphuc98.checkmein.UI.event_ans;

public interface ParticipantAnalyticsListFragmentContract {
    interface ParticipantAnalyticsListFragmentPresenter{
        void setView(ParticipantAnalyticsListFragment view);
        void loadParticipantAnalytics();

    }
    interface ParticipantAnalyticsListFragmentView{
        void setParticipantAnalyticsAdapter(com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAnswerDetailsCustomAdapter adapter);
    }
}
