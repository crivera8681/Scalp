package com.example.scalp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TicketListAdapter extends ArrayAdapter<Ticket> {

    private static final String TAG = "PersonListAdapter";

    private Context mContext;
    int mResource;


    public TicketListAdapter(Context context, int resource, ArrayList<Ticket> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the ticket info
        String ticket_seller = getItem(position).getTICKET_SELLER();
        String event_name = getItem(position).getEVENT_NAME();
        String ticket_price = getItem(position).getTICKET_PRICE();
        String ticket_date = getItem(position).getTICKET_DATE();
        String ticket_quantity = getItem(position).getTICKET_QUANTITY();
        String ticket_avail = getItem(position).getTICKET_AVAIL();

        //Create the ticket object with the information
        Ticket ticket = new Ticket(ticket_seller, event_name, ticket_price, ticket_date, ticket_quantity, ticket_avail);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tSeller = (TextView) convertView.findViewById(R.id.listadapter_seller);
        TextView tEventName = (TextView) convertView.findViewById(R.id.listadapter_eventname);
        TextView tPrice = (TextView) convertView.findViewById(R.id.listadapter_price);
        TextView tDate = (TextView) convertView.findViewById(R.id.listadapter_date);
        TextView tQuantity = (TextView) convertView.findViewById(R.id.listadapter_quantity);
        TextView tAvail = (TextView) convertView.findViewById(R.id.listadapter_status);

        tSeller.setText(ticket_seller);
        tEventName.setText(event_name);
        tPrice.setText("$"+ticket_price+".00");
        tDate.setText(ticket_date);
        tQuantity.setText("Qty: "+ticket_quantity);
        tAvail.setText(ticket_avail);

        return convertView;
    }
}
