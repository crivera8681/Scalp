package com.example.scalp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TicketsFragment extends Fragment {

    private static final String TAG = "TicketsFragment";
    DatabaseHelper myDB;
    private ListView ticketListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tickets, container, false);
        Log.d(TAG, "onCreate: Started.");
        ticketListView = (ListView) v.findViewById(R.id.tickets_listview);

        myDB = new DatabaseHelper(getActivity());

        //ArrayList<String> theList = new ArrayList<>();
        final ArrayList<Ticket> theList = new ArrayList<>(); //MADE THIS FINAL TO COMP ERROR WITH TEMP PASS TO INTENT
        Cursor data = myDB.getListContents();

        if (data.getCount() == 0) {
            Toast.makeText(getActivity(), "The Database is empty.", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                //theList.add(data.getString(1)); //1 refers to the column, 1 ticket id
                //theList.add(data.getString(2));
                Ticket tmp = new Ticket(data.getString(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4),
                        data.getString(6),
                        data.getString(8));
                theList.add(tmp);
                TicketListAdapter adapter = new TicketListAdapter(getActivity(), R.layout.adapter_view_layout, theList);
                ticketListView.setAdapter(adapter);
                //ListAdapter listAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, theList);
                //ticketListView.setAdapter(listAdapter);

                ticketListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity(), "Clicked item", Toast.LENGTH_LONG).show();

                        Ticket passTicket = theList.get(position);
                        String passSeller = passTicket.getTICKET_SELLER();
                        String passEventName = passTicket.getEVENT_NAME();

                        Intent intent = new Intent(getActivity(), TicketInfoAct.class);
                        intent.putExtra("passedSellerName", passSeller);
                        intent.putExtra("passedEventName", passEventName);
                        startActivity(intent);
                    }
                });
            }
        }
        return v;
    }



}
