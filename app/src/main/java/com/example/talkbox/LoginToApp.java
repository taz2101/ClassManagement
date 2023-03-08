package com.example.talkbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.talkbox.customerFoodPanel.InformationCustom;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginToApp extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://easytalk-afc8e-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_to_app);
        final EditText phone = findViewById(R.id.fullname);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.loginBtn);
        final TextView registerNowBtn = findViewById(R.id.registerNowButton);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String phoneTxt = phone.getText().toString();
                final String passwordTxt = password.getText().toString();

                //check xem edit text co trong khong
                if(phoneTxt.isEmpty()||passwordTxt.isEmpty()){
                    Toast.makeText(LoginToApp.this, "Please complete all information", Toast.LENGTH_SHORT).show();
                }
                else {

                    databaseReference.child("Admin").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //if phone is exist
                            if(snapshot.hasChild(phoneTxt)){
                                //check pass
                                final String getPassword = snapshot.child(phoneTxt).child("password").getValue(String.class);
                                if(getPassword.equals(passwordTxt)){
                                    Toast.makeText(LoginToApp.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                                    InformationCustom.phoneNumber = phoneTxt.toString();
                                    startActivity(new Intent(LoginToApp.this, MainMenu.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(LoginToApp.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(LoginToApp.this, "Phone is not Exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
                if (!isNetworkAvailable()){

                    Toast.makeText(LoginToApp.this, "No internet", Toast.LENGTH_SHORT).show();
                }
            }
            private boolean isNetworkAvailable() {
                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        });
    }




}
