package com.example.nguyenhongphuc98.checkmein;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.transition.FragmentTransitionSupport;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment mHomeFragment;
    private InfoFragment mInfoFragment;
    private SendEmailFragment mSendEmailFragment;
    private ListParticipantFragment mListParticipant;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        mMainFrame =(FrameLayout) findViewById(R.id.fragment_container);
        mHomeFragment=new HomeFragment();
        mInfoFragment=new InfoFragment();
        mSendEmailFragment=new SendEmailFragment();
        mListParticipant=new ListParticipantFragment();


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_home:
                        ReplaceFragment(mSendEmailFragment);
                        return true;

                    case R.id.action_info:
                        ReplaceFragment(mListParticipant);
                        return true;
                }
                return true;
            }
        });
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransition=getSupportFragmentManager().beginTransaction();
        fragmentTransition.replace(R.id.fragment_container,fragment);
        fragmentTransition.commit();
    }
}
