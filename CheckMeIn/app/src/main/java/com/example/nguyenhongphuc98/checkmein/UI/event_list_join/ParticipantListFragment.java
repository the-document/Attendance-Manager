package com.example.nguyenhongphuc98.checkmein.UI.event_list_join;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Attendance;
import com.example.nguyenhongphuc98.checkmein.UI.event_list_join.extend_dialog.ExtendDialogFragment;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAdapter;

import java.util.ArrayList;
import java.util.List;


public class ParticipantListFragment extends Fragment implements IViewParticipantList{
    ParticipantAdapter adapter;
    ListView lv_participant_list;
    //String temp1[]={"a","b","c","d"};
    List<Attendance> lsAttendance;
    Button btnExtend;
    ParticipantListPresenter presenter;

    public ParticipantListFragment() {
        presenter = new ParticipantListPresenter(this);
        lsAttendance = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_participant, container, false);

        OnLoadListAttendance();
        lv_participant_list = view.findViewById(R.id.lv_participant_list);
        btnExtend = view.findViewById(R.id.button);

        adapter=new ParticipantAdapter(getContext(),lsAttendance);
        lv_participant_list.setAdapter(adapter);

        btnExtend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ExtendDialogFragment dtDialog = new ExtendDialogFragment();
                dtDialog.show(getFragmentManager(), "dialog");
            }
        });

        return view;
    }

    @Override
    public void OnLoadListAttendance() {
        presenter.OnLoadListAttendance();
    }

    @Override
    public void OnLoadListEventSuccess() {
        Toast.makeText(getContext(),"load done",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoadListEventFail() {
        Toast.makeText(getContext(),"load fail",Toast.LENGTH_SHORT).show();
    }
}
