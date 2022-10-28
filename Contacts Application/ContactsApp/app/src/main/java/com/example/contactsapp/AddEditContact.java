package com.example.contactsapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEditContact extends AppCompatActivity {
    private ImageView profileIv;
    private EditText nameEt,phoneEt,emailEt,noteEt;
    private FloatingActionButton fab;
    String id,name,phone,email,note,addedTime,updatedTime;
    private ActionBar actionBar;
    private DbHelper dbHelper;

    Boolean isEditMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_contact);

        dbHelper = new DbHelper(this);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        profileIv = findViewById(R.id.profileIv);
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        emailEt = findViewById(R.id.emailEt);
        noteEt = findViewById(R.id.noteEt);
        fab = findViewById(R.id.fab);
        Intent intent= getIntent();
        isEditMode = intent.getBooleanExtra("isEditMode", false);
        if(isEditMode){
            actionBar.setTitle("Update Contact");

            id = intent.getStringExtra("ID");
            name = intent.getStringExtra("NAME");
            phone = intent.getStringExtra("PHONE");
            email = intent.getStringExtra("EMAIL");
            note = intent.getStringExtra("NOTE");
            addedTime= intent.getStringExtra("ADDEDTIME");
            updatedTime = intent.getStringExtra("UPDATEDTIME");
            // = intent.getStringExtra("IMAGE");
            nameEt.setText(name);
            phoneEt.setText(phone);
            emailEt.setText(email);
            noteEt.setText(note);
        }else{
            actionBar.setTitle("Add Contact");
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 saveData();
            }
        });
    }

    private void saveData() {
        name = nameEt.getText().toString();
        phone = phoneEt.getText().toString();
        email = emailEt.getText().toString();
        note = noteEt.getText().toString();
        String timeStamp = ""+System.currentTimeMillis();


        if(!name.isEmpty() || !phone.isEmpty() || !email.isEmpty() || !note.isEmpty()){


            if(isEditMode){
                dbHelper.updateContact(
                        ""+id,
                        "",
                        ""+name,
                        ""+phone,
                        ""+email,
                        ""+note,
                        ""+addedTime,
                        ""+timeStamp
                );
                Toast.makeText(getApplicationContext(), "Updated Successfully...", Toast.LENGTH_SHORT).show();
            }else{
                long id = dbHelper.insertContact("",
                        ""+name,
                        ""+phone,
                        ""+email,
                        ""+note,
                        ""+timeStamp,
                        ""+timeStamp);
                Toast.makeText(getApplicationContext(), "Inserted Successfully..."+id, Toast.LENGTH_SHORT).show();

            }

        }
        else{
            Toast.makeText(getApplicationContext(), "Nothing to save...", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}