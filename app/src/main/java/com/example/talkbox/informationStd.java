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
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    public static String mssv;
    public static int count = 0;

    private static final String TAG = "MyActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_std);
        showInformation(mssv);
        Button backBtn = findViewById(R.id.backButton);

        Button editInfor = findViewById(R.id.confirmEditBtn);
        editInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editStudentInformation(mssv);
            }
        });
        backBtn.setOnClickListener(view -> {
            startActivity(new Intent(informationStd.this, Group.class));
            finish();
        });
        showAbsent(mssv);

        Button sendText = findViewById(R.id.sendTextToStd);
        sendText.setOnClickListener(View -> {

            databaseReference.child("users").child(mssv).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                  String sdt = snapshot.child("sdt").getValue(String.class);
                  sendText(sdt,"tai sao hom nay nghi hoc");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    public void editStudentInformation(String mssv) {
        //lay ra cac element can dung
        EditText textFullName = findViewById(R.id.textFullName);
        EditText textID = findViewById(R.id.textID);

//        Boolean isUpdated;
//        Boolean isMssvUpdated;
//        Log.d(TAG, textFullName.getText().toString());
        // set gia tri moi cho fullName + 2 calllback function
        databaseReference.child("users").child(mssv).child("fullName").setValue(textFullName.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        Log.d(TAG,"Edit name successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Log.d(TAG,"Edit name fail");
                    }
                });
        // set new id for student
        databaseReference.child("users").child(mssv).child("mssv").setValue(Integer.parseInt(textID.getText().toString()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        // ...
                        Log.d(TAG,"Edit Mssv successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Log.d(TAG,"Edit Mssv fail");
                    }
                });
        makeText(informationStd.this, "Chỉnh sửa thông tin sinh viên thành công", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(informationStd.this, Group.class));
    }

    ;

    public void showInformation(String mssv) {


        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("d M");
        EditText textFullName = findViewById(R.id.textFullName);

        EditText textID = findViewById(R.id.textID);
        CheckBox checkAbsent = findViewById(R.id.isAbsent);
        TextView fullname = findViewById(R.id.fullname);
//        EditText nameInput = findViewById(R.id.nameInput);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(mssv).child("fullName").getValue(String.class);
                textFullName.setText(snapshot.child(mssv).child("fullName").getValue(String.class));

                fullname.setText(snapshot.child(mssv).child("fullName").getValue(String.class));
                textID.setText(mssv);

//                nameInput.setText(snapshot.child(mssv).child("fullName").getValue(String.class));
                databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild(ft.format(date))) {

                            if (snapshot.child(ft.format(date)).hasChild(name)) {
                                checkAbsent.setChecked(true);

                            }
                        } else {
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
                        if (checkAbsent.isChecked()) {
                            databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.hasChild(ft.format(date))) {
                                        if (snapshot.child(ft.format(date)).hasChild(name)) {
                                            makeText(informationStd.this, "Da them roi", Toast.LENGTH_SHORT).show();
                                        } else {
                                            databaseReference.child("Week").child(ft.format(date)).child(name).setValue(mssv);
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        } else {
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

    public void showAbsent(String mssv) {

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
                        count = 0;


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
    public void sendText(String number,String text){
        String smsNumber = String.format("smsto: %s",number);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
        smsIntent.setData(Uri.parse(smsNumber));
        smsIntent.putExtra("sms_body", text);
        startActivity(smsIntent);
    }






}
