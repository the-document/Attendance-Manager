package com.example.nguyenhongphuc98.checkmein;


import android.os.Bundle;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nguyenhongphuc98.checkmein.adapter.PageAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {

    EditText mEtName;
    EditText mEtMSSV;
    Button mBtnEditName;
    String mOldName;
    Boolean mNameEditing;

    PageAdapter mPageAdapter;
    ViewPager mViewPaper;

    TabLayout mTabLayout;
    TabItem mTiActivity;
    TabItem mTiAccount;

    public InfoFragment() {
        // Required empty public constructor
        mNameEditing=false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        mTabLayout=view.findViewById(R.id.tlInfo);
        mTiActivity=view.findViewById(R.id.tiAccount);
        mTiAccount=view.findViewById(R.id.tiAccount);
        mEtName=view.findViewById(R.id.etName);
        mBtnEditName=view.findViewById(R.id.btnEditName);
        mEtMSSV=view.findViewById(R.id.etMSSV);

        mViewPaper=view.findViewById(R.id.vpInfor);
        mPageAdapter=new PageAdapter(getChildFragmentManager(),mTabLayout.getTabCount());
        mViewPaper.setAdapter(mPageAdapter);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPaper.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        mViewPaper.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });

        mEtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnEditName.setBackgroundResource(R.drawable.icon_edit);
                mBtnEditName.setVisibility(View.VISIBLE);
            }
        });

        mBtnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mNameEditing){

                    mEtName.setFocusableInTouchMode(true);
                    mEtName.setFocusable(true);
                    mEtName.requestFocus();
                    mEtName.setSelection(mEtName.getText().length());

                    mBtnEditName.setBackgroundResource(R.drawable.icon_check);
                    mNameEditing=true;
                    mOldName=mEtName.getText().toString();
                }
                else {
                    mEtName.setFocusableInTouchMode(false);
                    mEtName.setFocusable(false);

                    mBtnEditName.setVisibility(View.INVISIBLE);
                    mNameEditing=false;

                    //check save data
                    String newName=mEtName.getText().toString();
                    if(newName.equals(mOldName))
                        return;

                    //save new name here
                    //========================
                }
            }
        });

        mEtMSSV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (mEtMSSV.getRight() - mEtMSSV.getCompoundDrawables()[2].getBounds().width())) {
                        mEtMSSV.setText("");

                        return true;
                    }
                }
                return false;
            }
        });

        return view;
    }

}
