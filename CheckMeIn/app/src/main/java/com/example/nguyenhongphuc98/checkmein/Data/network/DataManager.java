package com.example.nguyenhongphuc98.checkmein.Data.network;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.nguyenhongphuc98.checkmein.Adapter.EventAdapter;
import com.example.nguyenhongphuc98.checkmein.Adapter.OrganAdaptor;
import com.example.nguyenhongphuc98.checkmein.Data.DataCenter;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Attendance;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Collaborator;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Organization;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.ParticipantAnswerDetails;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.ParticipantAnswerDetailsDAL;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Person;
import com.example.nguyenhongphuc98.checkmein.UI.home.IEventCallBack;
import com.example.nguyenhongphuc98.checkmein.UI.login.LoginCallback;

import com.bumptech.glide.Glide;
import com.example.nguyenhongphuc98.checkmein.adapter.QuestionListCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;

import com.example.nguyenhongphuc98.checkmein.Data.db.model.Answer;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;

import com.example.nguyenhongphuc98.checkmein.UI.organ.organCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAdapter;
import com.example.nguyenhongphuc98.checkmein.adapter.QuestionListCustomAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.ChildEventListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import com.google.firebase.storage.StorageReference;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import java.util.HashMap;
import java.util.List;

public class DataManager {
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public final FirebaseDatabase database;

    IEventCallBack eventCallBack;
    organCallback _organCallback;

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

    public void set_organCallback(organCallback _organCallback) {
        if(this._organCallback == null)
            this._organCallback = _organCallback;
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
                    _organCallback.SaveCollborator(key);
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
                else
                    adaptor.notifyDataSetChanged();
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

        mStorageRef.child("person/"+imageName).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
                        {
                            eventCallBack.OnLoadEventComplete(null);
                            Log.e("DTM","check event err");
                        }

                    }
                    else {
                        eventCallBack.OnLoadEventComplete(null);
                        Log.d("AAAA","not get snapsot");
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

    public Boolean SaveAttendance(String userID, String eventID, String nameOfUser){
        try{
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

    public Boolean UpdatePersonAvatarByID(String userID,String newAvatar){

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
                            o.setAvatar(newAvatar);
                            person_Reference.child(snapshot.getKey()).setValue(o);
                            Log.e("DTM","updated avatar");
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


    private void pushAnswerToDatabase(Answer answer){
        String key = mDatabase.child("Answer").push().getKey();
        Task task = mDatabase.child("Answer").child(key).setValue(answer);
        task.addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void SaveAnswersForQuestion(Question question, ArrayList<Answer> answers){
        //Đầu tiên là ta phải Remove hết tất cả các Answer trùng với Question.
        String questionID = question.getId();
        final DatabaseReference answers_Ref = FirebaseDatabase.getInstance().getReference("Answer");
        Query query = answers_Ref.orderByChild("question").equalTo(question.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<String> keys = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        //Xoá tất cả các câu trả lời.
                        answers_Ref.child("Answer").child(snapshot.getKey()).removeValue();
                    }
                }
                for (Answer answer : answers){
                    pushAnswerToDatabase(answer);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void SaveAnswer(Question question, Answer answer){

    }

    public void LoadAnswersForQuestion(QuestionListCustomAdapter questionAdapter,
                                       List<Question> qsList, Question question){

        final DatabaseReference answers_Ref = FirebaseDatabase.getInstance().getReference("Answer");
        Query query = answers_Ref.orderByChild("question").equalTo(question.getId());
        ArrayList<Answer> answerOfQuestion = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists())
                    return;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Answer ans = snapshot.getValue(Answer.class);
                    answerOfQuestion.add(ans);
                }

                //Load xong câu trả lời rồi thì gán câu trả lời cho câu hỏi.
                question.setmAnswers(answerOfQuestion);

                //Sau khi load câu trả lời xong xuôi rồi thì mới thêm câu trả lời vào bộ câu hỏi.
                synchronized (this){
                    qsList.add(question);
                    questionAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean SaveQuestion(Question question){
        try{
            //Xem thử key có tồn tại chưa.
            //Nếu có thì là update, nếu chưa thì phải tạo mới.
            String key = "";
            if (question.getId() == null){
                key = mDatabase.child("MultipleChoiceQuestion").push().getKey();
                question.setId(key);
            }

            else
                key = question.getId();

            Task task = mDatabase.child("MultipleChoiceQuestion").child(key).setValue(question);

            task.addOnSuccessListener(new OnSuccessListener() {
                @Override
                public void onSuccess(Object o) {
                    Log.d("DataMan/SaveQuesSuccess", "Save success");
                    //Lưu câu hỏi thành công thì tiếp tục lưu cả câu trả lời luôn.

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("DataMan/SaveQuesFail", "Save failed : " + e.toString());
                }
            });
            return true;
        }catch (Exception e){
            Log.d("DataMan/SaveQuestion", e.toString());
        }
        return false;
    }

    public void LoadQuestionWithoutAnswerHighlight(QuestionListCustomAdapter adapter, List<Question> questionList, String eventID){
        final DatabaseReference questions_Ref = FirebaseDatabase.getInstance().getReference("MultipleChoiceQuestion");
        Query query = questions_Ref.orderByChild("event").equalTo(eventID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Đầu tiên chúng ta cần xoá bỏ đi dữ liệu cũ để không bị trùng lặp.
                questionList.clear();

                if (!dataSnapshot.exists()){
                    return;
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Question question = snapshot.getValue(Question.class);
                    questionList.add(question);
                    adapter.notifyDataSetChanged();
                }
                adapter.resetAllAnswersCorrectness();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void LoadQuestions(QuestionListCustomAdapter adapter, List<Question> questionList, String eventID){
        final DatabaseReference questions_Ref = FirebaseDatabase.getInstance().getReference("MultipleChoiceQuestion");
        Query query = questions_Ref.orderByChild("event").equalTo(eventID);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Đầu tiên chúng ta cần xoá bỏ đi dữ liệu cũ để không bị trùng lặp.
                questionList.clear();

                if (!dataSnapshot.exists()){
                    return;
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Question question = snapshot.getValue(Question.class);
                    questionList.add(question);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    
    public String SaveImageToDatastore(String folder,Uri uriToImage){

        String result="";
        String id;
        String child;
        if(folder.equals("organ/"))
        {
            Long localDateTime=System.currentTimeMillis();
            id = localDateTime.toString();
        }
        else
        {
            id = DataCenter.UserID;
        }

        child = folder + id;
        StorageReference riversRef = mStorageRef.child(child);

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
                Log.e("DTM","save image suscess");
            }
        });
       // Log.e("DTM",localDateTime.toString());
       // return localDateTime.toString();
        return id;
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

    public Boolean LoadAttendanceByEvent(List<Attendance> lsAttendance, ParticipantAdapter adapter, String eventId){
        try {
            final DatabaseReference attendance_Ref = FirebaseDatabase.getInstance().getReference("Attendance");
            attendance_Ref.orderByKey().equalTo(eventId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    lsAttendance.clear();
                    for (DataSnapshot attendanceSnapshot: dataSnapshot.getChildren()){
                        Log.e("DTM","got person: "+attendanceSnapshot.getValue().toString());
                        for (DataSnapshot attendanceDetail: attendanceSnapshot.getChildren()) {
                            Log.e("DTM","got person: "+ attendanceDetail.getValue().toString());
                            Attendance attendance = new Attendance();
                            attendance.setUser_key(attendanceDetail.getKey());
                            attendance.setUser_name(attendanceDetail.getValue().toString());
                            lsAttendance.add(attendance);
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
            Log.e("DTM","err get list attendance: "+e.getMessage());
            return false;
        }

        return true;
    }

    public Boolean LoadAnswersOfEvent(List<ParticipantAnswerDetails> answers, com.example.nguyenhongphuc98.checkmein.adapter.ParticipantAnswerDetailsCustomAdapter adapter, String eventId){
        try {
            final DatabaseReference attendance_Ref = FirebaseDatabase.getInstance().getReference("AnswerMultipleChoiceQ").child(eventId).child("m_user_results");
             attendance_Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    answers.clear();
                    List<ParticipantAnswerDetailsDAL> answersDetail= new ArrayList<>();
                    for (DataSnapshot answerSnapshot: dataSnapshot.getChildren()){
                        ParticipantAnswerDetailsDAL a = answerSnapshot.getValue(ParticipantAnswerDetailsDAL.class);
                        answersDetail.add(a);
                    }

                    //sort and setup ranking
                    Collections.sort(answersDetail);
                    for (int i=0; i<answersDetail.size();i++) {
                        ParticipantAnswerDetailsDAL e = answersDetail.get(i);
                        answers.add(new ParticipantAnswerDetails(e.getUser_name(),
                                                                i+1,answersDetail.size(),e.getNum_correct(),
                                                                    e.getTotal_question(),e.getTime_elapsed()));
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Log.e("DTM","err get answers list: "+e.getMessage());
            return false;
        }

        return true;
    }
}
