package com.example.talkbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Group extends AppCompatActivity {


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://easytalk-afc8e-default-rtdb.firebaseio.com/");

    ArrayList<String> arr = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
        genButton(10);
        Button btnBack = findViewById(R.id.backToMenu);
        btnBack.setOnClickListener(View -> {
            startActivity(new Intent(Group.this, MainMenu.class));
            finish();});


    }
    public void genButton(int n){
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.buttonGroup);
        for (int i = 1; i <= n; i++) {

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            Button btn = new Button(this);
            params.setMargins(20, 5, 5, 20);
            btn.setId(i);
            final int id_ = btn.getId();
            btn.setText("button " + id_);
            btn.setHeight(300);
            btn.setWidth(20);
            btn.setText("Nhóm "+id_);
            btn.setBackgroundResource(R.drawable.design_btn2);
            linearLayout.addView(btn, params);
            btn = ((Button) findViewById(id_));
            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if(arr.size()>3||arr.size()<2){
                        arr.clear();
                    }
                    setGroup(id_);
                }
            });

        }
    }
    public void setGroup(int id){


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //String name = snapshot.child(mssv).child("fullName").getValue(String.class);
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot s : snapshot.getChildren()) {

                            databaseReference.child("users").child(s.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    /*if(snapshot.hasChild("id")){
                                       */
                                        databaseReference.child("users").child(snapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                if(snapshot.hasChild("id")){
                                                    if(snapshot.child("id").getValue().toString().equals(Integer.toString(id))){
                                                        //Toast.makeText(Group.this,snapshot.getKey(), Toast.LENGTH_SHORT).show();

                                                            arr.add(snapshot.getKey());



                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Button btn = findViewById(R.id.button);
        Button btn1 = findViewById(R.id.button2);
        Button btn2 = findViewById(R.id.backBtnI);
        if(arr.size()==0){


            btn.setText("Khong co hoc sinh");
            btn1.setText("Khong co hoc sinh");
            btn2.setText("Khong co hoc sinh");
            btn.setOnClickListener(View->{
                Toast.makeText(Group.this, "khong co hoc sinh", Toast.LENGTH_SHORT).show();;});
            btn1.setOnClickListener(View->{
                Toast.makeText(Group.this, "khong co hoc sinh", Toast.LENGTH_SHORT).show();;});
            btn2.setOnClickListener(View->{
                Toast.makeText(Group.this, "khong co hoc sinh", Toast.LENGTH_SHORT).show();;});

        }
        else if(arr.size()==3){


            /*btn.setText(arr.get(0));
            btn1.setText(arr.get(1));
            btn2.setText(arr.get(2));*/
            btn.setOnClickListener(View->{openInfo(arr.get(0));});
            btn1.setOnClickListener(View->{openInfo(arr.get(1));});
            btn2.setOnClickListener(View->{openInfo(arr.get(2));});
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    btn.setText(snapshot.child(arr.get(0)).child("fullName").getValue(String.class));
                    btn1.setText(snapshot.child(arr.get(1)).child("fullName").getValue(String.class));
                    btn2.setText(snapshot.child(arr.get(2)).child("fullName").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        else {

            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    btn.setText(snapshot.child(arr.get(0)).child("fullName").getValue(String.class));
                    btn1.setText(snapshot.child(arr.get(1)).child("fullName").getValue(String.class));
                    btn2.setText("Không có học sinh");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            btn.setOnClickListener(View->{openInfo(arr.get(0));});
            btn1.setOnClickListener(View->{openInfo(arr.get(1));});
            btn2.setOnClickListener(View->{
                Toast.makeText(Group.this, "khong co hoc sinh", Toast.LENGTH_SHORT).show();;});
        }
    }
    public void openInfo(String mssv) {
        informationStd.mssv = mssv;
        startActivity(new Intent(Group.this, informationStd.class));
        finish();
    /*}
    public String getname(String mssv){
        String name;
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String namex = snapshot.child(mssv).child("fullName").getValue(String.class) ;
               name = namex;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }*/


}}