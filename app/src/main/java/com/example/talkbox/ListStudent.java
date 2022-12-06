package com.example.talkbox;

import static java.util.Objects.requireNonNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ListStudent extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://easytalk-afc8e-default-rtdb.firebaseio.com/");

    public static String childOfdataBase;

    boolean meo1IsShow = true;
    public void fade(View view){

        ImageView meo1 = findViewById(R.id.showmeo1);
        ImageView meo2 = findViewById(R.id.showmeo2);
        if(meo1IsShow){
            meo1IsShow=false;
            meo1.animate().alpha(0).setDuration(1000);
            meo2.animate().alpha(1).setDuration(1000);
        }
        else {
            meo1IsShow=true;
            meo1.animate().alpha(1).setDuration(1000);
            meo2.animate().alpha(0).setDuration(1000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student);


        listClass(childOfdataBase);

        Button backBtn = findViewById(R.id.backToMenu);
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(ListStudent.this,MainMenu.class));
            finish();
        });

    }

    public void listClass(String child){

        ListView listView = findViewById(R.id.listView);

        ArrayList<Student> student = new ArrayList<>();

        ArrayAdapter<Student> arrayAdapter = new ArrayAdapter<Student>(ListStudent.this, android.R.layout.simple_list_item_1,student);

        listView.setAdapter(arrayAdapter);
        TextView notif = findViewById(R.id.notifi);
        notif.setText("Danh sách sinh viên cả lớp");
        if(child == "users"){
            databaseReference.child(child).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    String name = snapshot.child("fullName").getValue(String.class);
                    String mssv = snapshot.child("mssv").getValue(Integer.class).toString();
                    Student student1 = new Student(name,mssv);
                    student.add(student1);
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        }
        else if(child == "Week"){
            Date date = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("d M");
            databaseReference.child(child).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(ft.format(date))){
                        TextView notif = findViewById(R.id.notifi);
                        notif.setText("Danh sách sinh viên nghỉ học");
                        databaseReference.child(child).child(ft.format(date)).addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                String name = snapshot.getKey();
                                if(!name.equals("Empty")){
                                    Student student1 = new Student(name,"mssv");
                                    student.add(student1);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                                //String mssv = snapshot.getValue(String.class).toString();

                            }

                            @Override
                            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                                arrayAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                            }

                            @Override
                            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    else {
                        TextView notif = findViewById(R.id.notifi);
                        notif.setText("Hôm nay không phải ngày học");
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


    }

}