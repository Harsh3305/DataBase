package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.Contact;
import params.Params;

public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context) {
        super(context, Params.DB_NAME, null, Params.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + Params.DB_NAME + "(" +
                Params.KEY_ID + " INTEGER PRIMARY KEY,"+ Params.KEY_NAME +
                 "Text, "+ Params.KEY_PHONE + " TEXT" + ")";
        Log.d("Database log","Following query is running: " + create);
        db.execSQL(create);
    }
    public void addContact (Contact contact) {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(Params.KEY_NAME, contact.getName());
        values.put(Params.KEY_PHONE, contact.getPhoneNumber());

        database.insert(Params.TABLE_NAME, null, values);
        Log.d("Database log","Data added to DB");
        database.close();
    }
    public List<Contact> getAllContact () {
        List<Contact> listOfContact = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String select = "SELECT * FROM " + Params.TABLE_NAME;
        Cursor cursor = database.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                listOfContact.add(contact);
            }
            while(cursor.moveToNext());

        }
        database.close();
        return listOfContact;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
