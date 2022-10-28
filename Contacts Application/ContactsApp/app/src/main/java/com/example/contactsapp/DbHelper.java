package com.example.contactsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(@Nullable Context context) {
        super(context, Constatnts.DATABASE_NAME, null, Constatnts.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constatnts.CREATE_TABLE );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+Constatnts.TABLE_NAME);
        onCreate(db);
    }
    public long insertContact(String image, String name, String phone, String email, String note, String addedTime, String updatedTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constatnts.C_IMAGE,image);
        contentValues.put(Constatnts.C_NAME,name);
        contentValues.put(Constatnts.C_PHONE,phone);
        contentValues.put(Constatnts.C_EMAIL,email);
        contentValues.put(Constatnts.C_NOTE,note);
        contentValues.put(Constatnts.C_ADDED_TIME,addedTime);
        contentValues.put(Constatnts.C_UPDATED_TIME,updatedTime);
        long id = db.insert(Constatnts.TABLE_NAME,null,contentValues);
        db.close();
        return id;
    }
    public void updateContact(String id, String image, String name, String phone, String email, String note, String addedTime, String updatedTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Constatnts.C_IMAGE,image);
        contentValues.put(Constatnts.C_NAME,name);
        contentValues.put(Constatnts.C_PHONE,phone);
        contentValues.put(Constatnts.C_EMAIL,email);
        contentValues.put(Constatnts.C_NOTE,note);
        contentValues.put(Constatnts.C_ADDED_TIME,addedTime);
        contentValues.put(Constatnts.C_UPDATED_TIME,updatedTime);
        db.update(Constatnts.TABLE_NAME,contentValues, Constatnts.C_ID+" =? ", new String[]{id});
        db.close();
    }

    public void deleteContact(String id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(Constatnts.TABLE_NAME, Constatnts.C_ID+ " =? ", new String[]{id});
        db.close();
    }
    public void deleteAllContact( ){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+ Constatnts.TABLE_NAME);
        db.close();
    }
    public ArrayList<ModelContact> getAllData(){
        //create arrayList
        ArrayList<ModelContact> arrayList = new ArrayList<>();
        //sql command query
        String selectQuery = "SELECT * FROM "+Constatnts.TABLE_NAME;

        //get readable db
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery,null);

        // looping through all record and add to list

        if (cursor.moveToFirst()){
            do {
                ModelContact modelContact = new ModelContact(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constatnts.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_NOTE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_ADDED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_UPDATED_TIME))
                );
                arrayList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return arrayList;
    }

    public ArrayList<ModelContact> getSearchContact(String query){
        ArrayList<ModelContact> contactList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String queryToSearch  = "SELECT * FROM " + Constatnts.TABLE_NAME+ " WHERE " + Constatnts.C_NAME + " LIKE '%"+ query+"%'";
        Cursor cursor = db.rawQuery(queryToSearch, null);

        if (cursor.moveToFirst()){
            do {
                ModelContact modelContact = new ModelContact(
                        // only id is integer type
                        ""+cursor.getInt(cursor.getColumnIndexOrThrow(Constatnts.C_ID)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_NAME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_IMAGE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_PHONE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_EMAIL)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_NOTE)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_ADDED_TIME)),
                        ""+cursor.getString(cursor.getColumnIndexOrThrow(Constatnts.C_UPDATED_TIME))
                );
                contactList.add(modelContact);
            }while (cursor.moveToNext());
        }
        db.close();
        return contactList;
    }
}
