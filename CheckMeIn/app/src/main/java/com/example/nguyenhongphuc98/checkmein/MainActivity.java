package com.example.nguyenhongphuc98.checkmein;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.transition.FragmentTransitionSupport;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransition=getSupportFragmentManager().beginTransaction();
        fragmentTransition.replace(R.id.fragment_container,fragment);
        fragmentTransition.commit();
    }
}
