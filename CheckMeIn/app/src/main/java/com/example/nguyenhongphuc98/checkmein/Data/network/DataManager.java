package com.example.nguyenhongphuc98.checkmein.Data.network;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.nguyenhongphuc98.checkmein.Adapter.EventAdapter;
import com.example.nguyenhongphuc98.checkmein.Adapter.OrganAdaptor;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Collaborator;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Event;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Organization;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager {

    Context mContext;

    Account account;
    FirebaseAuth mAuth;

    private DatabaseReference mDatabase;
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
        }

        return _instance;
    }
    public static DataManager Instance(Context c){
        if(_instance==null){
            _instance=new DataManager(c);
        }

        return _instance;
    }


    //fire base implement here
    public DataManager() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

    }
    public DataManager(Context c) {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mContext=c;
    }

    public void getLoginInfo(String email, String password) {
        account.setUserName(email);
        account.setPassword(password);
    }

    public boolean checkLoginStatus()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            return true;
        } else {
            // No user is signed in
            return false;
        }
    }

    public void ProcessLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                        }
                    }
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

    public void LoadOrgan(OrganAdaptor adaptor,List<String> lsID,List<String> lsImage,String host){

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

                                lsImage.add(uri.toString());
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

                        adapter.notifyDataSetChanged();
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
            //create new node organ
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
            final DatabaseReference events_Reference = FirebaseDatabase.getInstance().getReference("Account");
            Query query=events_Reference.orderByChild("person").equalTo(personKey);

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


    //tempt-------------------------------------------------------------
    public boolean checkEmailVerify() {
        return mAuth.getCurrentUser().isEmailVerified();
    }

}
