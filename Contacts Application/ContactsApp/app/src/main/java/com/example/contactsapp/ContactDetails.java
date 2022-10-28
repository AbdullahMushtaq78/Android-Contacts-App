package com.example.contactsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ContactDetails extends AppCompatActivity {

    private TextView nameTv,phoneTv,emailTv,addedTimeTv,updatedTimeTv,noteTv;
    private String id;

    private DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_details);

        Intent intent  = getIntent();
        id = intent.getStringExtra("contactId");

        dbHelper = new DbHelper(this);
        nameTv = findViewById(R.id.nameTv);
        phoneTv = findViewById(R.id.phoneTv);
        emailTv = findViewById(R.id.emailTv);
        addedTimeTv = findViewById(R.id.addedTimeTv);
        updatedTimeTv = findViewById(R.id.updatedTimeTv);
        noteTv = findViewById(R.id.noteTv);
        loadDataById();

    }

    private void loadDataById() {
        String selectQuery = "SELECT * FROM "+ Constatnts.TABLE_NAME+" WHERE " + Constatnts.C_ID + " =\"" + id + "\"";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
               String name = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_NAME));
               String image = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_IMAGE));
               String phone = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_PHONE));
               String email = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_EMAIL));
               String note = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_NOTE));
               String addTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_ADDED_TIME));
               String updateTime = ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_UPDATED_TIME));


                Calendar calendar = Calendar.getInstance(Locale.getDefault());

                calendar.setTimeInMillis(Long.parseLong(addTime));
                String timeAdd = ""+ android.text.format.DateFormat.format("dd/MM/yy hh:mm:aa",calendar);

                calendar.setTimeInMillis(Long.parseLong(updateTime));
                String timeUpdate = ""+ android.text.format.DateFormat.format("dd/MM/yy hh:mm:aa",calendar);

                nameTv.setText(name);
                phoneTv.setText(phone);
                emailTv.setText(email);
                noteTv.setText(note);
                addedTimeTv.setText(timeAdd);
                updatedTimeTv.setText(timeUpdate);

            }while(cursor.moveToNext());
        }
        db.close();
    }
}