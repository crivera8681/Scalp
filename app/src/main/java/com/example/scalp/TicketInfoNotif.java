package com.example.scalp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TicketInfoNotif extends AppCompatActivity {

    TextView eventName, price, date, time, quantity, seats, status;
    TextView sellerName, sellerPhone;
    DatabaseHelper myDB;

    int id = 0; //used to delete the ticket from list of tickets after adding to notifications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info_notif);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String shUsername = mPreferences.getString(getString(R.string.sessionUserName), "");

        myDB = new DatabaseHelper(this);
        //
        //====POPULATE DATA=======
        //
        eventName = (TextView) findViewById(R.id.ticketinfo_eventname);
        sellerName = (TextView) findViewById(R.id.ticketinfo_seller);
        sellerPhone = (TextView) findViewById(R.id.ticketinfo_phone_number);

        price = (TextView) findViewById(R.id.ticketinfo_price);
        date = (TextView) findViewById(R.id.ticketinfo_date);
        time = (TextView) findViewById(R.id.ticketinfo_time);
        quantity = (TextView) findViewById(R.id.ticketinfo_quantity);
        seats = (TextView) findViewById(R.id.ticketinfo_seats);
        status = (TextView) findViewById(R.id.ticketinfo_status);

        // get the data passed from the tickets fragment
        String ticket_eventName = getIntent().getStringExtra("passedEventName");
        String ticket_Seller = getIntent().getStringExtra("passedSellerName");
        eventName.setText(ticket_eventName);
        sellerName.setText(ticket_Seller);

        Cursor data2 = myDB.getUserPhoneNumber(ticket_Seller);
        String sellerPhoneNum = "";
        while (data2.moveToNext()) {
            sellerPhoneNum = data2.getString(0);
        }
        sellerPhone.setText(sellerPhoneNum);

        String thisEventName = eventName.getText().toString();
        if (!thisEventName.equals("")) {
            //Toast.makeText(this, "Event name is not null", Toast.LENGTH_LONG).show();
            Cursor data = myDB.getSpecificTicketContents(thisEventName);
            int cost = -1;
            String ticket_date = "";
            String ticket_time = "";
            int ticket_quantity = -1;
            String ticket_seats = "";
            String ticket_status = "";
            while (data.moveToNext()) {
                id = data.getInt(0);
                cost = data.getInt(3);
                ticket_date = data.getString(4);
                ticket_time = data.getString(5);
                ticket_quantity = data.getInt(6);
                ticket_seats = data.getString(7);
                ticket_status = data.getString(8);
            }
            if (cost > -1) {
                //We did it!
                //Toast.makeText(this, "We did it!", Toast.LENGTH_LONG).show();
                price.setText("$" + Integer.toString(cost) + ".00");
                date.setText(ticket_date);
                time.setText(ticket_time);
                quantity.setText(Integer.toString(ticket_quantity));
                seats.setText(ticket_seats);
                status.setText(ticket_status);
            } else {
                //No event date associated with that event name
                //Toast.makeText(this, "F my life", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Event name is null", Toast.LENGTH_LONG).show();
        }

    }
}
