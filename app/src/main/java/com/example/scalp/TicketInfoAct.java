package com.example.scalp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TicketInfoAct extends AppCompatActivity {

    TextView eventName, price, date, time, quantity, seats, status;
    TextView sellerName, sellerPhone;
    DatabaseHelper myDB;
    Button purchaseButton;
    ImageView ticketImg;

    int id = 0; //used to delete the ticket from list of tickets after adding to notifications

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_info);

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String shUsername = mPreferences.getString(getString(R.string.sessionUserName), "");
        String shImage = mPreferences.getString(getString(R.string.shImage), "");
        Bitmap imageBitmap = StringToBitMap(shImage);

        myDB = new DatabaseHelper(this);
        purchaseButton = (Button) findViewById(R.id.ticketinfo_purchase_button);
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

        ticketImg = (ImageView) findViewById(R.id.ticketinfo_img);
        ticketImg.setImageBitmap(imageBitmap);


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
        //
        //====BUTTON CODE=======
        //
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to seller notifications here
                //add to buyer notifications here
                myDB.buyTicket(id, shUsername);
                Intent intent = new Intent(TicketInfoAct.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
    /**
     * @param encodedString
     * @return bitmap (from given string)
     */
    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }
}
