package com.example.talkbox.customerFoodPanel;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.talkbox.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class InformationCustom extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://easytalk-afc8e-default-rtdb.firebaseio.com/");

    public static String phoneNumber;

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //field
        View v = inflater.inflate(R.layout.fragment_custominfo,null);
        getActivity().setTitle("Infomation");


        //Set information

        TextView nameTxt = (TextView)v.findViewById(R.id.textFullName);
        TextView phoneTxt = (TextView)v.findViewById(R.id.textShowAbsent);
        TextView emailTxt = (TextView)v.findViewById(R.id.email);
        TextView phone = (TextView)v.findViewById(R.id.fullname);

        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nameTxt.setText(snapshot.child(phoneNumber).child("fullname").getValue(String.class));
                phoneTxt.setText(phoneNumber);
                phone.setText(phoneNumber);
                emailTxt.setText(snapshot.child(phoneNumber).child("email").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return v;

    }
}
