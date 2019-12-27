package com.example.nguyenhongphuc98.checkmein.UI.event;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.Adapter.EventAdapter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.UI.event_ques.QuestionManagementFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewListActivityFragment extends Fragment implements IViewListActivity{

    ViewListActivityPresenter presenter;

    ListView listView;
    EventAdapter adapter;
    List<Event> lsEvent;

    public ViewListActivityFragment() {
        // Required empty public constructor

        presenter=new ViewListActivityPresenter(this);

        lsEvent=new ArrayList<>();
//        lsEvent.add(new Event("avt",
//                "10:20","11:50",
//                "15H32V",
//                "E Building",
//                "LvOrAdb2ZVh09ULAgS6",
//                "NULL",
//                "22/12/2019",
//                "Hoi thao lam giau"));
//
//        lsEvent.add(new Event("avt",
//                "10:20","11:50",
//                "15H32V",
//                "E Building",
//                "LvOrAdb2ZVh09ULAgS6",
//                "NULL",
//                "01/11/2022",
//                "Chia se tai chinh"));
//
//        lsEvent.add(new Event("avt",
//                "10:20","11:50",
//                "15H32V",
//                "E Building",
//                "LvOrAdb2ZVh09ULAgS6",
//                "NULL",
//                "22/10/2015",
//                "Lam dep khong kho"));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_view_list_activity, container, false);
        listView=view.findViewById(R.id.lvEventOfOrganization);
        adapter=new EventAdapter(getContext(),lsEvent);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), String.format("Position : %d", position), Toast.LENGTH_LONG);
                ReplaceFragment(new QuestionManagementFragment());
            }
        });

        OnLoadListEvent();
        return view;
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransition.replace(R.id.fragment_container,fragment);
        fragmentTransition.commit();
    }

    @Override
    public void OnLoadListEvent() {
        presenter.OnLoadListEvent();
    }


    //=========================================================



    @Override
    public void OnLoadListEventSuccess() {
        Toast.makeText(getContext(),"load done",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnLoadListEventFail() {
        Toast.makeText(getContext(),"load fail",Toast.LENGTH_SHORT).show();
    }
}
