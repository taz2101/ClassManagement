package com.example.talkbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://easytalk-afc8e-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button btn = findViewById(R.id.transToGroup);
        Button listBtn = findViewById(R.id.litStd);
        Button listbtnAbsent = findViewById(R.id.absentList);
        Button sheduleBtn = findViewById(R.id.shedule);


        btn.setOnClickListener(View -> {ClassGroup();});
        listBtn.setOnClickListener(View -> {showListStd();});
        listbtnAbsent.setOnClickListener(View -> {showListStdAbsent();});
        sheduleBtn.setOnClickListener(View -> {customDialog();});



    }
    public void ClassGroup(){
        startActivity(new Intent(MainMenu.this, Group.class));
        finish();

    }
    public void showListStd()
    {
        startActivity(new Intent(MainMenu.this,ListStudent.class));
        ListStudent.childOfdataBase = "users";
        finish();
    }
    public void showListStdAbsent()
    {

            startActivity(new Intent(MainMenu.this,ListStudent.class));
            ListStudent.childOfdataBase = "Week";
            finish();

    }
    public void customDialog(){


        TextView okay_text, cancel_text;
        Dialog dialog = new Dialog(MainMenu.this);

        dialog.setContentView(R.layout.activity_dialog_celender);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        //dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

        okay_text = dialog.findViewById(R.id.okay_text);
        cancel_text = dialog.findViewById(R.id.cancel_text);

        TextInputEditText day = dialog.findViewById(R.id.getDay);
        TextInputEditText moth = dialog.findViewById(R.id.getMoth);


        okay_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = String.valueOf(day.getText());
                String m = String.valueOf(moth.getText());

                if(day.equals("")||m.equals("")){
                    Toast.makeText(MainMenu.this, "chua nhan", Toast.LENGTH_SHORT).show();
                }
                if(Integer.parseInt(d)<=31&&Integer.parseInt(d)>=1){
                    if(Integer.parseInt(m)<=12&&Integer.parseInt(m)>=1){
                        Toast.makeText(MainMenu.this, "nhan ngay thang", Toast.LENGTH_SHORT).show();
                        setCalender(d,m);
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(MainMenu.this, "Khong hop le", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(MainMenu.this, " Khong hop le", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(MainMenu.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();

    }
    public void setCalender(String d, String m)
    {

        String date = d+" "+m;
        databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(date)){
                    Toast.makeText(MainMenu.this, "Lich hoc da duoc them roi", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("Week").child(date).child("Empty").setValue("1");
                    Toast.makeText(MainMenu.this, "da them ngay thang nam", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}