package com.example.talkbox;
import static android.widget.Toast.makeText;

import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class informationStd extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://easytalk-afc8e-default-rtdb.firebaseio.com/");

    StorageReference storageReference;

    public static  String mssv;
    public static int count = 0 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_std);
        showInformation(mssv);
        Button backBtn = findViewById(R.id.backButton);



        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(informationStd.this,Group.class));
            finish();
        });
        showAbsent(mssv);

       // String imageID =


    }
    public void showInformation(String mssv){


        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("d M");
        TextView textFullName = findViewById(R.id.textFullName);

        TextView textID = findViewById(R.id.textID);
        CheckBox checkAbsent = findViewById(R.id.isAbsent);
        TextView fullname = findViewById(R.id.fullname);


        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(mssv).child("fullName").getValue(String.class);
                textFullName.setText(snapshot.child(mssv).child("fullName").getValue(String.class));

                fullname.setText(snapshot.child(mssv).child("fullName").getValue(String.class));
                textID.setText(mssv);

                databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(snapshot.hasChild(ft.format(date))){

                            if(snapshot.child(ft.format(date)).hasChild(name)){
                                checkAbsent.setChecked(true);

                            }
                        }
                        else {
                            makeText(informationStd.this, "Hom nay khong co buoi hoc", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                    checkAbsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            //makeText(informationStd.this, ft.format(date), Toast.LENGTH_SHORT).show();
                            if(checkAbsent.isChecked()){
                                databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.hasChild(ft.format(date))){
                                            if(snapshot.child(ft.format(date)).hasChild(name)){
                                                makeText(informationStd.this, "Da them roi", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                databaseReference.child("Week").child(ft.format(date)).child(name).setValue(mssv);
                                            }
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            else {
                                databaseReference.child("Week").child(ft.format(date)).child(name).removeValue();
                            }

                        }
                    });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void showAbsent(String mssv){

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(mssv).child("fullName").getValue(String.class);
                databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot s : snapshot.getChildren()) {
                            for (DataSnapshot a : s.getChildren()) {
                                for (int i = 0; i <= a.getChildrenCount(); i++) {
                                    //makeText(informationStd.this, a.getKey(), Toast.LENGTH_SHORT).show();
                                    if (a.getKey().equals(name)) {
                                        count++;
                                    }
                                }
                            }
                        }
                        TextView absent = findViewById(R.id.textShowAbsent);
                        absent.setText(Integer.toString(count));
                        count=0;



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
