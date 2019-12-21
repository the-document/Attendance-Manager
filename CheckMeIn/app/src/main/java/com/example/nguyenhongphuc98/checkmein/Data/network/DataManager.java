package com.example.nguyenhongphuc98.checkmein.Data.network;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.nguyenhongphuc98.checkmein.Adapter.QuestionListCustomAdapter;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Account;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Answer;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Organization;
import com.example.nguyenhongphuc98.checkmein.Data.db.model.Question;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
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
        account.setMail(email);
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
    public Boolean SaveOrgan(Organization organization){

        try{
            //create new node organ
            String key=mDatabase.child("Organization").push().getKey();

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
                key = mDatabase.child("Question").push().getKey();
                question.setId(key);
            }

            else
                key = question.getId();

            Task task = mDatabase.child("Question").child(key).setValue(question);

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
                    LoadAnswersForQuestion(adapter, questionList, question);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                Log.e("DTM","download uri:"+uri.getPath());
                Glide.with(mContext).load(uri).into(imageView);
            }
        });
    }
}
