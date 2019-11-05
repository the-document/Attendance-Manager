package com.example.nguyenhongphuc98.checkmein.CreateActivity;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nguyenhongphuc98.checkmein.adapter.EventAdapter;
import com.example.nguyenhongphuc98.checkmein.R;
import com.example.nguyenhongphuc98.checkmein.QuestionManagementFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewListActivityFragment extends Fragment {

    ListView listView;
    EventAdapter adapter;
    String temp1[]={"a","b","c","d","e"};

    public ViewListActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_view_list_activity, container, false);
        listView=view.findViewById(R.id.lvEventOfOrganization);
        adapter=new EventAdapter(getContext(),temp1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), String.format("Position : %d", position), Toast.LENGTH_LONG);
                ReplaceFragment(new QuestionManagementFragment());
            }
        });
        return view;
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransition=getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransition.replace(R.id.fragment_container,fragment);
        fragmentTransition.commit();
    }
}
