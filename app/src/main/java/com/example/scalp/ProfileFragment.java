package com.example.scalp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileFragment extends Fragment {

    DatabaseHelper db;
    TextView profile_username;
    TextView profile_fullname;
    TextView profile_email;
    TextView profile_phonenumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_profile, container, false);
        //get the view of teh fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        //initialize shared preference
        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        //link the text view to full name one
        profile_username = (TextView) v.findViewById(R.id.profile_username);
        profile_fullname = (TextView) v.findViewById(R.id.profile_fullname);
        profile_email = (TextView) v.findViewById(R.id.profile_email);
        profile_phonenumber = (TextView) v.findViewById(R.id.profile_phone_number);

        //initialize DatabaseHelper
        db = new DatabaseHelper(getActivity());

        //get session username
        String shUsername = mPreferences.getString(getString(R.string.sessionUserName), "");
        profile_username.setText(shUsername);

        Cursor data = db.getAllUserInfo(shUsername);
        String getUsername = "";
        String getFullname = "";
        String getEmail = "";
        String getPhone = "";
        while (data.moveToNext()) {
            getUsername = data.getString(1);
            getFullname = data.getString(3);
            getEmail = data.getString(4);
            getPhone = data.getString(5);
        }
        if (getUsername != "") {
            //We did it!
            //Toast.makeText(this, "We did it!", Toast.LENGTH_LONG).show();
            //profile_username.setText(getUsername);
            profile_fullname.setText(getFullname);
            profile_email.setText(getEmail);
            profile_phonenumber.setText(getPhone);
        } else {
            //Toast.makeText(this, "Error pulling from database", Toast.LENGTH_LONG).show();
        }
        /*
        //run query to get info (password going into fullname for test purposes)
        Cursor cursor = db.getPassword(shUsername);
        if(cursor.getCount() == 0){
            Toast.makeText(getActivity(), "NO PASS DATA", Toast.LENGTH_SHORT).show();
        }else{
            profile_fullname.setText(cursor.getString(0));
        }
        */
        return v;
    }
}
