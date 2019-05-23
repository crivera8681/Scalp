package com.example.scalp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "register.db";
    //registeruser
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static final String NAME = "fullname";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    private static final String CREATE_USER_DB = "CREATE TABLE "
            + TABLE_NAME + "(" + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_2 + " TEXT, "
            + COL_3 + " TEXT, "
            + NAME + " TEXT, "
            + EMAIL + " TEXT, "
            + PHONE + " TEXT);";
    //tickets
    public static final String TABLE_TICKETS = "tickets";
    public static final String KEY_TICKETS = "ticket_ID"; //col 0
    public static final String TICKET_SELLER = "ticket_seller"; //col 1
    public static final String EVENT_NAME = "event_name"; //col 2
    public static final String TICKET_PRICE = "ticket_price"; //col 3
    public static final String TICKET_DATE = "ticket_date"; //col 4
    public static final String TICKET_TIME = "ticket_time"; //col 5
    public static final String TICKET_QUANTITY = "ticket_quantity"; //col 6
    public static final String TICKET_SEATS = "ticket_seats"; //col 7
    public static final String TICKET_AVAIL = "ticket_avail"; //col 8 for ticket availability
    public static final String TICKET_IMAGE = "ticket_image"; //col 9 to store bitmap
    public static final String TICKET_BUYER = "ticket_buyer"; //col 10 to store ticket buyer
    private static final String CREATE_TICKET_DB = "CREATE TABLE "
            + TABLE_TICKETS + "(" + KEY_TICKETS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TICKET_SELLER + " TEXT, "
            + EVENT_NAME + " TEXT, "
            + TICKET_PRICE + " INTEGER, "
            + TICKET_DATE + " TEXT, "
            + TICKET_TIME + " TEXT, "
            + TICKET_QUANTITY + " INTEGER,"
            + TICKET_SEATS + " TEXT, "
            + TICKET_AVAIL + " TEXT, "
            + TICKET_IMAGE + " BLOB, "
            + TICKET_BUYER + " TEXT);";


    public DatabaseHelper(Context context) {
        /*
        Version Control: Version 1->2: May 9th, 4:43PM I added the Ticket DB and moved to change Version num
                         Version 2->3: May 11th, 2:00PM Dropping old user table and adding with 3 new elements.
                         Version 3->4: May 15th, 5:04PM Add a buyer to the ticker table for notification
         */
        super(context, DATABASE_NAME, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL("CREATE TABLE registeruser (ID INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        //db.execSQL("CREATE TABLE tickets (ticket_ID INTEGER PRIMARY KEY AUTOINCREMENT, event_name TEXT, ticket_price INTEGER, ticket_date TEXT, ticket_time TEXT, ticket_quantity INTEGER, ticket_seats TEXT, ticket_avail TEXT, ticket_image BLOB)");
        db.execSQL(CREATE_TICKET_DB);
        //db.execSQL(CREATE_USER_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKETS);
        onCreate(db);
    }

    //==============================
    // USER FUNCTIONS
    //==============================
    /*
    public long addUser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }
*/
    public long addUser(String user, String password, String fullname, String email, String phonenumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("phone", phonenumber);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }
    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0)
            return true;
        else
            return false;
    }
    public Cursor getUserEmail(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + EMAIL + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = '" + username + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }
    public Cursor getUserPhoneNumber(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + PHONE + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = '" + username + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }
    public Cursor getAllUserInfo(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_2 + " = '" + username + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }

    //Quick test func to try get some data and display on profile
    public Cursor getPassword(String currentUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT password from registeruser WHERE name=c" + currentUser, null);
        return cursor;
    }

    //==============================
    // TICKET FUNCTIONS
    //==============================
    public boolean addTicketData(String seller, String event, int price, String date, String time, int quantity, String seats){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TICKET_SELLER,seller);
        contentValues.put(EVENT_NAME,event);
        contentValues.put(TICKET_PRICE,price);
        contentValues.put(TICKET_DATE,date);
        contentValues.put(TICKET_TIME,time);
        contentValues.put(TICKET_QUANTITY,quantity);
        contentValues.put(TICKET_SEATS,seats);
        contentValues.put(TICKET_AVAIL,"Available");
        contentValues.put(TICKET_IMAGE,"PLACEHOLDER BLOB");
        contentValues.put(TICKET_BUYER,"none");

        long result = db.insert(TABLE_TICKETS, null, contentValues);
        if(result ==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_TICKETS + " WHERE " + TICKET_AVAIL + "='Available'", null);
        return data;
    }
    public Cursor getListNotificationContents(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_TICKETS + " WHERE " + TICKET_SELLER + " = '" + user + "'" + " OR " + TICKET_BUYER + " = '" + user + "'", null);
        return data;
    }
    public Cursor getSpecificTicketContents(String specEventName){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_TICKETS + " WHERE " + EVENT_NAME + " = '" + specEventName + "'";
        Cursor data = db.rawQuery(query, null);

        return data;
    }
    public void buyTicket(int id, String buyer) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateStatus = "UPDATE " + TABLE_TICKETS + " SET " + TICKET_AVAIL + "='SOLD'" + " WHERE " + KEY_TICKETS + " = '" + id + "'";
        db.execSQL(updateStatus);
        //
        String updateBuyer = "UPDATE " + TABLE_TICKETS + " SET " + TICKET_BUYER + "='" + buyer + "'" + " WHERE " + KEY_TICKETS + " = '" + id + "'";
        db.execSQL(updateBuyer);
        //String query = "DELETE FROM " + TABLE_TICKETS + " WHERE " + KEY_TICKETS + " = '" + id + "'";
        //db.execSQL(query);
    }

}