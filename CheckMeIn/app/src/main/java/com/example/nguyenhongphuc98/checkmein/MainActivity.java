package com.example.nguyenhongphuc98.checkmein;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nguyenhongphuc98.checkmein.Data.network.DataManager;
import com.example.nguyenhongphuc98.checkmein.UI.home.HomeFragment;
import com.example.nguyenhongphuc98.checkmein.UI.scan.CardScannerFragment;
import com.example.nguyenhongphuc98.checkmein.UI.user.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private HomeFragment mHomeFragment;
    private InfoFragment mInfoFragment;
    private CardScannerFragment mCardScannerFragment;

    private static boolean cameraPermissionGranted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mMainFrame =(FrameLayout) findViewById(R.id.fragment_container);
        DataManager.Instance(getApplication());

        mHomeFragment=new HomeFragment();
        mInfoFragment=new InfoFragment();

        mCardScannerFragment = new CardScannerFragment();

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.action_home:
                        ReplaceFragment(mHomeFragment);
                        return true;
                    case R.id.action_info:
                        ReplaceFragment(mInfoFragment);
                        return true;
                    case R.id.action_scan:
                        //Lấy quyền truy cập camera từ đây.
                        GetCameraPermission();
                        //Kiểm tra xem đã lấy được quyền chưa.
                        if (cameraPermissionGranted)
                            ReplaceFragment(mCardScannerFragment);
                        return true;
                }
                return true;
            }
        });

        // Write a message to the database
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("message");

        //myRef.setValue("Hello, World!");

        ReplaceFragment(mHomeFragment);
    }

    private void ReplaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransition=getSupportFragmentManager().beginTransaction();
        fragmentTransition.replace(R.id.fragment_container,fragment);
        fragmentTransition.commit();
    }

    private void ShowPermissionNotGrantedError()
    {
        //Nếu chưa lấy được quyền sử dụng camera thì phải hiển thị hộp thoại thông báo cho người dùng biết.
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Permission not granted !")
                .setMessage("Permission not granted ! Therefore we cannot proceed your request.")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Hành động xảy ra khi người dùng nhấn OK.
                    }
                });
    }

    private void GetCameraPermission()
    {

        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.CAMERA)
                .subscribe(granted->{
                    if (granted){
                        cameraPermissionGranted = true;
                        ReplaceFragment(mCardScannerFragment);
                    }
                    else{
                        cameraPermissionGranted = false;
                        ShowPermissionNotGrantedError();
                    }
                });
    }
}
