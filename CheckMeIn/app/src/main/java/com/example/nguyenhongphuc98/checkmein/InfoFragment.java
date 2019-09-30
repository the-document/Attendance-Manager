package com.example.nguyenhongphuc98.checkmein;


import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nguyenhongphuc98.checkmein.Adapter.PageAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    PageAdapter mPageAdapter;
    ViewPager mViewPaper;

    TabLayout mTabLayout;
    TabItem mTiActivity;
    TabItem mTiAccount;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        mTabLayout=view.findViewById(R.id.tlInfo);
        mTiActivity=view.findViewById(R.id.tiAccount);
        mTiAccount=view.findViewById(R.id.tiAccount);

        mViewPaper=view.findViewById(R.id.vpInfor);
        mPageAdapter=new PageAdapter(getFragmentManager(),mTabLayout.getTabCount());
        mViewPaper.setAdapter(mPageAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPaper.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        return view;
    }

}
