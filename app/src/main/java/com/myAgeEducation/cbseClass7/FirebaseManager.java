package com.myAgeEducation.cbseClass7;

import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {
    public ArrayList<Question> questionList = new ArrayList<>();
    public boolean anyNotebookTypeQuestion = false;

    public FirebaseManager()
    {
        // default constructor
    }

    public void write(Object object, String nodePath)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(nodePath);
        myRef.setValue(object);
    }

    public void write(Object object, String nodePath, final FirebaseCallback firebaseCallback)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(nodePath);
        myRef.setValue(object).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                firebaseCallback.onCallback("1");
                }
            });
    }

    public void writeSingleValue(String nodePath, long value)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference(nodePath);
        dbRef.setValue(value);
    }

    public void readObject(final String nodePath, String orderBy, final FirebaseCallback firebaseCallback)
    {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(nodePath);
        Query query;// = myRef.orderByChild(orderBy).limitToLast(100);
        if(TextUtils.isEmpty(orderBy))
        {
            query = myRef;
        }
        else
        {
            query = myRef.orderByChild(orderBy).limitToLast(100);
        }

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firebaseCallback.onCallback(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                firebaseCallback.onCallback(null);
            }
        });
    }

    public void getServerTime(final FirebaseCallback firebaseCallback)
    {
        Log.d("Server_Time", "Getting Server Time");
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset");
        offsetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                double offset = snapshot.getValue(Double.class);
                Double time = System.currentTimeMillis() + offset;
                long temp = time.longValue();
                firebaseCallback.onCallback(String.valueOf(temp));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                firebaseCallback.onCallback("0");
            }
        });
    }

    public void readSingleValue(String path, final FirebaseCallback firebaseCallback)
    {
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(path);
        offsetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    if(snapshot == null)
                    {
                        firebaseCallback.onCallback(null);
                    }
                    else {
                        firebaseCallback.onCallback(snapshot.getValue().toString());
                    }
                    Log.d("EXCEPTION", "successful read");
                }
                catch(Exception e)
                {
                    Log.d("EXCEPTION", "exception: " + e.getMessage());
                    firebaseCallback.onCallback("error");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("EXCEPTION", error.getMessage());
                firebaseCallback.onCallback("error");
            }
        });
    }

    public static void readYouTubeVideoList(String subject, int chapterNumber, FirebaseCallback firebaseCallback)
    {
        String path = "/videos/" + subject + "/" + chapterNumber;
        DatabaseReference offsetRef = FirebaseDatabase.getInstance().getReference(path);
        ArrayList<YouTubeVideoDetails> youTubeVideoDetailsArrayList = new ArrayList<>();
        offsetRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                try {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        try {
                            YouTubeVideoDetails question = postSnapshot.getValue(YouTubeVideoDetails.class);
                            youTubeVideoDetailsArrayList.add(question);
                        } catch (Exception e) {
                            Log.d("CBSE_ERROR", e.getMessage());
                        }
                    }
                    if(youTubeVideoDetailsArrayList.size() > 0) {
                        firebaseCallback.onCallback(youTubeVideoDetailsArrayList);
                    }
                    else
                    {
                        firebaseCallback.onCallback(null);
                    }
                }
                catch(Exception e)
                {
                    Log.d("EXCEPTION", "exception: " + e.getMessage());
                    firebaseCallback.onCallback("error");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d("EXCEPTION", error.getMessage());
                firebaseCallback.onCallback("error");
            }
        });
    }
}
