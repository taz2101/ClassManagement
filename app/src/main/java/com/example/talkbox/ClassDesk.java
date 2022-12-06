package com.example.talkbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
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

import java.util.Date;

public class ClassDesk extends AppCompatActivity {


    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://easytalk-afc8e-default-rtdb.firebaseio.com/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_desk);

        Button btn1 = findViewById(R.id.btn);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn13);
        Button btn4 = findViewById(R.id.btn14);
        Button btn5 = findViewById(R.id.btn15);
        Button btn6 = findViewById(R.id.btn16);
        Button btn7 = findViewById(R.id.btn17);
        Button btn8 = findViewById(R.id.btn18);
        Button btn9 = findViewById(R.id.btn19);
        Button btn10 = findViewById(R.id.btn20);
        Button btn11 = findViewById(R.id.btn22);
        Button btn12 = findViewById(R.id.btn24);
        Button btn13 = findViewById(R.id.btn25);
        Button btn14 = findViewById(R.id.btn26);
        Button btn15 = findViewById(R.id.btn27);
        Button btn16 = findViewById(R.id.btn28);
        Button btn17 = findViewById(R.id.btn29);
        Button btn18 = findViewById(R.id.btn30);
        Button btn19 = findViewById(R.id.btn31);
        Button btn20 = findViewById(R.id.btn32);
        Button btn21 = findViewById(R.id.btn33);
        Button btn22 = findViewById(R.id.btn34);
        Button btn23 = findViewById(R.id.btn35);
        Button btn24 = findViewById(R.id.btn36);
        Button btn25 = findViewById(R.id.btn37);
        Button btn26 = findViewById(R.id.btn38);
        Button btn27 = findViewById(R.id.btn39);
        Button btn28 = findViewById(R.id.btn40);
        Button btn29 = findViewById(R.id.btn41);
        Button btn30 = findViewById(R.id.btn42);

        Button listClass = findViewById(R.id.listBtn);
        Button listClassAbsent = findViewById(R.id.listClassBtn);
        Button dialogBtn = findViewById(R.id.calender);


        btn1.setOnClickListener(view -> {openInfo("20182328");});
        btn2.setOnClickListener(view -> {openInfo("20182609");});
        btn3.setOnClickListener(view -> {openInfo("20182686");});
        btn4.setOnClickListener(view -> {openInfo("20182865");});
        btn5.setOnClickListener(view -> {openInfo("20182461");});
        btn6.setOnClickListener(view -> {openInfo("20182593");});
        btn7.setOnClickListener(view -> {openInfo("20182722");});
        btn8.setOnClickListener(view -> {openInfo("20182864");});
        btn9.setOnClickListener(view -> {openInfo("20182401");});
        btn10.setOnClickListener(view -> {openInfo("20182466");});
        btn11.setOnClickListener(view -> {openInfo("20182410");});
        btn12.setOnClickListener(view -> {openInfo("20182786");});
        btn13.setOnClickListener(view -> {openInfo("20172672");});
        btn14.setOnClickListener(view -> {openInfo("20172888");});
        btn15.setOnClickListener(view -> {});
        btn16.setOnClickListener(view -> {openInfo("20182514");});
        btn17.setOnClickListener(view -> {});
        btn18.setOnClickListener(view -> {openInfo("20182833");});
        btn19.setOnClickListener(view -> {openInfo("20182882");});
        btn20.setOnClickListener(view -> {openInfo("20182353");});
        btn21.setOnClickListener(view -> {openInfo("20182893");});
        btn22.setOnClickListener(view -> {openInfo("20182749");});
        btn23.setOnClickListener(view -> {openInfo("20182508");});
        btn24.setOnClickListener(view -> {});
        btn25.setOnClickListener(view -> {openInfo("20182801");});
        btn26.setOnClickListener(view -> {openInfo("20182567");});
        btn27.setOnClickListener(view -> {openInfo("20182353");});
        btn28.setOnClickListener(view -> {openInfo("20192874");});
        btn29.setOnClickListener(view -> {openInfo("20193135");});
        btn30.setOnClickListener(view -> {openInfo("20182445");});


        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("d M");

            databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(ft.format(date))){

                        int count =(int) snapshot.child(ft.format(date)).getChildrenCount() - 1;
                        String countAb= Integer.toString(count);
                        databaseReference.child("Week").child(ft.format(date)).child("Empty").setValue(countAb);

                    }
                    else {
                        Toast.makeText(ClassDesk.this,"Hom nay khong co lich hoc", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }


            });
            databaseReference.child("Week").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    TextView countAbsent = findViewById(R.id.textAbsent);
                    countAbsent.setText(snapshot.child(ft.format(date)).child("Empty").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            listClass.setOnClickListener(view -> {
                startActivity(new Intent(ClassDesk.this,ListStudent.class));
                ListStudent.childOfdataBase = "users";
                finish();
            });

            listClassAbsent.setOnClickListener(view -> {
                startActivity(new Intent(ClassDesk.this,ListStudent.class));
                ListStudent.childOfdataBase = "Week";
                finish();
            });


            dialogBtn.setOnClickListener(view -> {customDialog();});

    }

    public void openInfo(String mssv) {
        informationStd.mssv = mssv;
        startActivity(new Intent(ClassDesk.this, informationStd.class));
        finish();
    }

    public void customDialog(){


        TextView okay_text, cancel_text;
        Dialog dialog = new Dialog(ClassDesk.this);

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
                    Toast.makeText(ClassDesk.this, "chua nhan", Toast.LENGTH_SHORT).show();
                }
                if(Integer.parseInt(d)<=31&&Integer.parseInt(d)>=1){
                    if(Integer.parseInt(m)<=12&&Integer.parseInt(m)>=1){
                        Toast.makeText(ClassDesk.this, "nhan ngay thang", Toast.LENGTH_SHORT).show();
                        setCalender(d,m);
                        dialog.dismiss();
                    }
                    else{
                        Toast.makeText(ClassDesk.this, "Khong hop le", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(ClassDesk.this, " Khong hop le", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(ClassDesk.this, "Cancel clicked", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(ClassDesk.this, "Lich hoc da duoc them roi", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("Week").child(date).child("Empty").setValue("1");
                    Toast.makeText(ClassDesk.this, "da them ngay thang nam", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}