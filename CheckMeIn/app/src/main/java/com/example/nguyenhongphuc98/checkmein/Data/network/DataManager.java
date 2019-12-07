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
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

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
    public FirebaseAuth mAuth;
    public DatabaseReference mDatabase;
    public final FirebaseDatabase database;

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
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
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
