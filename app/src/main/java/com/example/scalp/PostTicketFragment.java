package com.example.scalp;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PostTicketFragment extends Fragment {

    DatabaseHelper myDB;
    Button confirmBtn, goListBtn;
    EditText eventName, ticketPrice, eventDate, eventTime, ticketQuantity, eventSeats;
    ImageView ticketImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_post_ticket, container, false);
        View v = inflater.inflate(R.layout.fragment_post_ticket, container, false);

        myDB = new DatabaseHelper(getActivity());

        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = mPreferences.edit();
        String shImage = mPreferences.getString(getString(R.string.shImage), "");
        Bitmap imageBitmap = StringToBitMap(shImage);

        eventName = (EditText) v.findViewById(R.id.postticket_eventname);
        ticketPrice = (EditText) v.findViewById(R.id.postticket_price);
        eventDate = (EditText) v.findViewById(R.id.postticket_date);
        eventTime = (EditText) v.findViewById(R.id.postticket_time);
        ticketQuantity = (EditText) v.findViewById(R.id.postticket_quantity);
        eventSeats = (EditText) v.findViewById(R.id.postticket_seats);

        confirmBtn = (Button) v.findViewById(R.id.postticket_confirm);
        goListBtn = (Button) v.findViewById(R.id.postticket_golist);

        ticketImg = (ImageView) v.findViewById(R.id.ticketView2);
        ticketImg.setImageBitmap(imageBitmap);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize shared preference
                SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

                String newSeller = mPreferences.getString(getString(R.string.sessionUserName), "");
                String newEventName = eventName.getText().toString();
                String stringTicketPrice = ticketPrice.getText().toString();//get the price data in string form
                int intTicketPrice = Integer.parseInt(stringTicketPrice); //convert price string to float
                String newEventDate = eventDate.getText().toString();
                String newEventTime = eventTime.getText().toString();
                String stringTicketQ = ticketQuantity.getText().toString(); //get the quantity in string form
                int intTicketQ = Integer.parseInt(stringTicketQ); //convert quantity to int
                String newEventSeats = eventSeats.getText().toString();

                if (eventName.length() != 0) {
                    addTData(newSeller, newEventName, intTicketPrice, newEventDate, newEventTime, intTicketQ, newEventSeats);
                    eventName.setText("");
                    ticketPrice.setText("");
                    eventDate.setText("");
                    eventTime.setText("");
                    ticketQuantity.setText("");
                    eventSeats.setText("");
                } else {
                    Toast.makeText(getActivity(), "You must enter an event name.", Toast.LENGTH_LONG).show();
                }
            }
        });
        goListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to the ticket list fragments after all this
                TicketsFragment ticketFragment = new TicketsFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, ticketFragment).commit();
            }
        });

        return v;
    }

    public void addTData(String newSeller, String newEventName, int intTicketPrice, String newEventDate, String newEventTime, int intTicketQ, String newEventSeats) {
        boolean insertData = myDB.addTicketData(newSeller, newEventName, intTicketPrice, newEventDate, newEventTime, intTicketQ, newEventSeats);

        if (insertData == true) {
            Toast.makeText(getActivity(), "Successfully entered.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Something went wrong with ticket entry", Toast.LENGTH_LONG).show();
        }
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




