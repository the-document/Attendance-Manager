package com.example.nguyenhongphuc98.checkmein.Data.network;

import android.content.Intent;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.nguyenhongphuc98.checkmein.Adapter.EventAdapter;
import com.example.nguyenhongphuc98.checkmein.Adapter.OrganAdaptor;
import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Collaborator;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Organization;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Person;
import com.example.nguyenhongphuc98.checkmein.UI.home.IEventCallBack;
import com.example.nguyenhongphuc98.checkmein.UI.login.LoginCallback;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.storage.StorageReference;

import java.util.concurrent.atomic.AtomicBoolean;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public final FirebaseDatabase database;

    IEventCallBack eventCallBack;

    private static LoginCallback loginCallback;


    Context mContext;

    private StorageReference mStorageRef;

    private static final String TAG = "EmailPassword";

    //public static final String EXTRA_USERNAME = ".LOGIN.USERNAME";
    //public static final String EXTRA_PASSWORD = ".LOGIN.PASSWORD";
    //public static final int REGISTER_SUCCESS = 1;
    //public static final int REGISTER_NOT = 0;

    private static DataManager _instance;
    public static DataManager Instance(){
        if(_instance==null){
            _instance=new DataManager();
            Log.e("DATAMANAGER","new instance");
        }

        return _instance;
    }
    public static DataManager Instance(Context c){
        if(_instance==null){
            _instance=new DataManager(c);
            Log.e("DATAMANAGER","new instance with context");
        }

        return _instance;
    }


    //fire base implement here
    public DataManager() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }
    public DataManager(Context c) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        mContext=c;
    }

    public void setEventCallBacks(IEventCallBack eventCallBack) {
        if(this.eventCallBack==null)
            this.eventCallBack = eventCallBack;
    }

    public void setLoginCallback(LoginCallback cb) {
        if(cb==null)
        {
            Log.d("DATAMANAGER","cb null");
            return;
        }


        if(this.loginCallback==null)
            this.loginCallback = cb;
        if(this.loginCallback!=null)
        Log.d("DATAMANAGER","seted login");

    }

    public boolean checkLoginStatus() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // User is signed in
            return true;
        } else {
            // No user is signed in
            return false;
        }
    }


    public boolean checkEmailVerify() {
        return mAuth.getCurrentUser().isEmailVerified();
    }

    public void ProcessLogin(String email, String password) {
        if(this.loginCallback==null)
            Log.e("DATAMANAGER","login callback is null");

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        if(this.loginCallback!=null)
                        {
                            loginCallback.OnLoginComplete(LoginCallback.CODE_LOGIN_SUCCESS);
                            Log.d("DATAMANAGER","callback login");
                        }
                        else {
                            Log.d("DATAMANAGER","errcallback login" );
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        if(loginCallback!=null)
                        {
                            loginCallback.OnLoginComplete(LoginCallback.CODE_LOGIN_INCORRECT);
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                       else {
                            Log.d(TAG, "signInWithEmail:failure");
                        }


                    }
                });
    }

    public boolean registerProcess(String userName, String passWord) {

        Log.d(TAG, "createAccount:" + userName);

        // [START create_user_with_userName]
        mAuth.createUserWithEmailAndPassword(userName, passWord)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        Log.d(TAG, "createUserWithEmail:success");
                        // [START send_email_verification]
                        mAuth.getCurrentUser().sendEmailVerification()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "Verificate mail sent.");
                                    }
                                    else Log.w(TAG, "Send verificate mail failed.");
                                });
                        // [END send_email_verification]
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    }
                });
        // [END create_user_with_email]

        if (mAuth.getCurrentUser() != null) return true;
        else return false;
    }

    public Task sendMailResetPassword(String emailAddress) {
        return mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Reset email sent.");
                    }
                    else Log.w(TAG, "Send mail rest failed.");
                });
    }




    //THIS PART MAKE BY NGUYEN HONG PHUC
    //organ
    public Boolean SaveOrgan(Organization organization){

        try{
            //create new node organ
            String key=mDatabase.child("Organization").push().getKey();
            organization.setId(key);
            //save to firebase
            Task task= mDatabase.child("Organization").child(key).setValue(organization);
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DATAMANAGER",e.toString());

                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("DATAMANAGER","save success");
                }
            });

            return true;
        }
        catch (Exception e){
            Log.d("DATAMANAGER",e.toString());
        }

       return false;

    }

    public Boolean EditOrgan(Organization organization){

        try{
            //create new node organ
           // String key=mDatabase.child("Organization").push().getKey();
           // organization.setId(key);
            //save to firebase
            Task task= mDatabase.child("Organization").child(organization.getId()).setValue(organization);
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DATAMANAGER",e.toString());

                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("DATAMANAGER","edit success");
                }
            });

            return true;
        }
        catch (Exception e){
            Log.d("DATAMANAGER",e.toString());
        }

        return false;

    }

    public Boolean DeleteOrgan(String organID){
        try
        {
            Task task= mDatabase.child("Organization").child(organID).removeValue();
            Log.e("DTM","delete organ:"+organID);
            return true;
        }
        catch (Exception e){
            Log.e("DTM","delete organ err");
            return false;
        }
    }

    public void LoadOrgan(OrganAdaptor adaptor,List<String> lsID,HashMap<String,String> lsImage,String host){

        final DatabaseReference organs_Reference = FirebaseDatabase.getInstance().getReference("Organization");
        Query query=organs_Reference.orderByChild("userId").equalTo(host);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lsID.clear();
                lsImage.clear();

                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Organization o = snapshot.getValue(Organization.class);
                        lsID.add(o.getId());



                        mStorageRef.child("organ/"+o.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.e("DTM","get url avt a organ:"+uri.getPath());

                                lsImage.put (o.getId(),uri.toString());
                                adaptor.notifyDataSetChanged();


                                Log.e("DTM","downloaded a organ:"+uri.getPath());

                            }
                        });
                    }

                    //adaptor.SetOrganAdaptor(lsImage,lsID);
                    //adaptor.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public Boolean LoadOrganByID(String organId, ImageView img, EditText name, EditText des,TextView avtid){

        try {
            final DatabaseReference organ_Reference = FirebaseDatabase.getInstance().getReference("Organization");
            Query query=organ_Reference.orderByChild("id").equalTo(organId);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Organization o = snapshot.getValue(Organization.class);
                            Log.e("DTM","load organ: "+o.getId());

                            name.setText(o.getName());
                            des.setText(o.getDescription());
                            avtid.setText(o.getAvatar());

                            mStorageRef.child("organ/"+o.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(mContext)
                                            .load(uri)
                                            .into(img);
                                    Log.e("DTM","downloaded a organ:"+uri.getPath());

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get organ: "+e.getMessage());
            return false;
        }

        return true;
    }

    public void LoadImageCollorator(String imageName,
                                    List<String> lsColla, com.example.nguyenhongphuc98.checkmein.adapter.CollaborationAdapter adapter){

        mStorageRef.child("organ/"+imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("DTM","get url avt collabortor:"+uri.getPath());

                lsColla.add(uri.toString());
                adapter.notifyDataSetChanged();
            }
        });
    }

    public Boolean SaveCollaborator(Collaborator c){

        try{

            //save to firebase
            Task task= mDatabase.child("Collaborator").child(c.getOrgan()).child(c.getCollaborator()).setValue(c.getName());
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DATAMANAGER",e.toString());

                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("DATAMANAGER","save success");
                }
            });

            return true;
        }
        catch (Exception e){
            Log.d("DATAMANAGER",e.toString());
        }

        return false;
    }



    public Boolean LoadActivitys(List<Event> lsEvent, String organID, EventAdapter adapter){

        try {
            final DatabaseReference events_Reference = FirebaseDatabase.getInstance().getReference("Event");
            Query query=events_Reference.orderByChild("organ").equalTo(organID);
            Log.e("DTM","loading event of organ: "+organID);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lsEvent.clear();

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Event o = snapshot.getValue(Event.class);
                            lsEvent.add(o);
                            Log.e("DTM","load event: "+o.getEvent_name());

//                        mStorageRef.child("organ/"+o.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                                Log.e("DTM","get url avt a organ:"+uri.getPath());
//
//                                lsImage.add(uri.toString());
//                                adaptor.notifyDataSetChanged();
//
//
//                                Log.e("DTM","downloaded a organ:"+uri.getPath());
//
//                            }
//                        });
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get list event: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean LoadActivitysOfUser(List<Event> lsEvent, String userID, EventAdapter adapter){

        try {
            final DatabaseReference events_Reference = FirebaseDatabase.getInstance().getReference("Attendance");
            Query query=events_Reference.orderByChild(userID);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lsEvent.clear();

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            //{16520951=Hồng Phúc, 16520713=Phúc đẹp trai}
                            Log.e("DTMA","load event : "+snapshot.getValue());
                            String rawData;
                            rawData=snapshot.getValue().toString().split("\\}")[0];
                            rawData=rawData.split("\\{")[1];
                            //Log.e("DTMA","raw : "+rawData);
                            String pair[]=rawData.split(",");
                            //Log.e("DTMA","pair: "+pair);

                            for (String str: pair) {
                                String mssvs[] =(str.split("=")[0]).split(" ");
                                String mssv;
                                if(mssvs[0].length()>1)
                                    mssv=mssvs[0];
                                else mssv=mssvs[1];

                               // Log.e("DTMA","mssv : "+mssv);
                                if (mssv.equals(userID)) {
                                    String eventKey = snapshot.getKey();
                                    LoadActivitysOfByID(lsEvent, eventKey, adapter);
                                    Log.e("DTMA", "load event : " + eventKey);
                                }
                            }

                        }


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get list event: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean LoadActivitysOfByID(List<Event> lsEvent, String eventID,EventAdapter adapter){

        try {
            final DatabaseReference events_Reference = FirebaseDatabase.getInstance().getReference("Event");
            Query query=events_Reference.orderByChild("event_id").equalTo(eventID);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lsEvent.clear();

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Event o = snapshot.getValue(Event.class);
                            lsEvent.add(o);
                            Log.e("DTM","added event: "+o.getEvent_name());
                            adapter.notifyDataSetChanged();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get list event: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean SaveEvent(Event event){
        try{
            //create new node event
            String key=mDatabase.child("Event").push().getKey();
            event.setEvent_id(key);

            //save to firebase
            Task task= mDatabase.child("Event").child(key).setValue(event);
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DATAMANAGER",e.toString());

                }
            }).addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("DATAMANAGER","save event success");
                }
            });

            return true;
        }
        catch (Exception e){
            Log.d("DATAMANAGER",e.toString());
        }

        return false;
    }

    public Boolean LoadEventByCode(String eventCode){

        try {
            final DatabaseReference events_Reference = FirebaseDatabase.getInstance().getReference("Event");
            Query query=events_Reference.orderByChild("event_code").equalTo(eventCode);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        boolean isSuccess=false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Event o = snapshot.getValue(Event.class);
                            if(eventCallBack!=null){

                                try {
                                    Date day= new SimpleDateFormat("dd/MM/yy").parse(o.getEvent_day());
                                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                                    Date date = new Date();
                                    Log.e("DTM","date: "+date.getDate());
                                    Log.e("DTM","day: "+day.getDate());
                                    if(date.getDate()==day.getDate()
                                        &&date.getMonth()==day.getMonth()
                                        &&date.getYear()==day.getYear())
                                    {
                                        eventCallBack.OnLoadEventComplete(o);
                                        isSuccess=true;
                                        Log.e("DTM","got event: "+o.getEvent_code());
                                        break;
                                    }

                                } catch (ParseException e) {
                                    eventCallBack.OnLoadEventComplete(null);
                                    e.printStackTrace();
                                }

                            }

                        }

                        if(!isSuccess)
                            eventCallBack.OnLoadEventComplete(null);
                    }
                    else
                        eventCallBack.OnLoadEventComplete(null);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get account: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean SaveAttendance(String userID, String eventID, String nameOfUser){
        try{
            //save to firebase
//            Map<String,String> record=new HashMap<>();
//            record.put(userID,nameOfUser);
            mDatabase.child("Attendance").child(eventID).child(userID).setValue(nameOfUser);
            Log.e("DTM","added attendance: "+eventID);
            return true;
        }
        catch (Exception e){
            Log.d("DATAMANAGER",e.toString());
        }
        return false;
    }


    public Boolean LoadUserByID(String userID,EditText email, EditText phone, TextView mssv){

        //load person

        try {
            final DatabaseReference person_Reference = FirebaseDatabase.getInstance().getReference("Person");
            Query query=person_Reference.orderByChild("mssv").equalTo(userID);
            Log.e("AAAA",query.toString());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Person o = snapshot.getValue(Person.class);
                            mssv.setText(o.getMssv());
                            phone.setText(o.getPhone());


                            String personKey=snapshot.getKey();
                            Log.e("DTM","got person: "+o.getDisplayName());
                            LoadAccountByPerson(personKey,email);
                        }
                    }
                    else
                        Log.e("AAAA","4");
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get person: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean LoadAccountByPerson(String personKey,EditText email){

        //load account

        try {
            final DatabaseReference accounts_Reference = FirebaseDatabase.getInstance().getReference("Account");
            Query query=accounts_Reference.orderByChild("person").equalTo(personKey);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Account o = snapshot.getValue(Account.class);
                            email.setText(o.getUserName());

                            Log.e("DTM","got account: "+o.getUserName());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get account: "+e.getMessage());
            return false;
        }

        return true;
    }


    public Boolean LoadPersonByEmail(String email){

        try {
            final DatabaseReference account_Reference = FirebaseDatabase.getInstance().getReference("Account");
            Query query=account_Reference.orderByChild("userName").equalTo(email);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Account o = snapshot.getValue(Account.class);
                            Log.e("DTM","get object: "+o.getUserName());

                            //load person by id
                            final DatabaseReference person_Reference = FirebaseDatabase.getInstance().getReference("Person");
                            Query query=person_Reference.orderByKey().equalTo(o.getPerson());

                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    if (dataSnapshot.exists()) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Person p = snapshot.getValue(Person.class);

                                            Log.e("DTM","get display name: "+p.getDisplayName());

                                            DataCenter.UserID=p.getMssv();
                                            DataCenter.UserDisplayName=p.getDisplayName();

                                            loginCallback.OnLoadPersonToDatacenterComplete();
                                        }
                                    }
                                    else
                                        Log.e("DTM","not found: "+o.getUserName());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                    else
                        Log.e("DTM","not found"+email);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get account: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean LoadGenarelInfoPersonByID(String userID, EditText displayName, CircularImageView avatar){

        //load person

        try {
            final DatabaseReference person_Reference = FirebaseDatabase.getInstance().getReference("Person");
            Query query=person_Reference.orderByChild("mssv").equalTo(userID);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Person o = snapshot.getValue(Person.class);
                            displayName.setText(o.getDisplayName());
                            Log.e("DTM","get name user:"+o.getDisplayName());

                            mStorageRef.child("person/"+o.getAvatar()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Log.e("DTM","get url avt user:"+uri.getPath());

                                    Glide.with(mContext)
                                            .load(uri)
                                            .into(avatar);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        catch (Exception e){
            Log.e("DTM","err get person: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean UpdatePersonDisplayNameByID(String userID,String newName){

        //load person

        try {
            final DatabaseReference person_Reference = FirebaseDatabase.getInstance().getReference("Person");
            Query query=person_Reference.orderByChild("mssv").equalTo(userID);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Person o = snapshot.getValue(Person.class);
                            o.setDisplayName(newName);
                            person_Reference.child(snapshot.getKey()).setValue(o);
                            Log.e("DTM","updated displayname");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        catch (Exception e){
            Log.e("DTM","err update name person: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean UpdatePersonPhoneNumberByID(String userID,String newPhoneNumber){

        //load person

        try {
            final DatabaseReference person_Reference = FirebaseDatabase.getInstance().getReference("Person");
            Query query=person_Reference.orderByChild("mssv").equalTo(userID);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Person o = snapshot.getValue(Person.class);
                            o.setPhone(newPhoneNumber);
                            person_Reference.child(snapshot.getKey()).setValue(o);
                            Log.e("DTM","updated phoneNumber");
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {}
            });
        }
        catch (Exception e){
            Log.e("DTM","err update phone: "+e.getMessage());
            return false;
        }

        return true;
    }

    //---------------------------------------------------------------------
    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    public String SaveImageToDatastore(Uri uriToImage){

        String result="";
        Long localDateTime=System.currentTimeMillis();
        StorageReference riversRef = mStorageRef.child("organ/"+localDateTime.toString());

        UploadTask uploadTask = riversRef.putFile(uriToImage);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });

        return localDateTime.toString();
    }

    public void LoadImageFromStorage(String imageName,ImageView imageView){
        mStorageRef.child("organ/"+imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("DTM","downloaded image uri:"+uri.getPath());

                Glide.with(mContext).load(uri).into(imageView);
            }
        });
    }

}
