package com.example.scalp;

public class Ticket {
    private String TICKET_SELLER; //col 2
    private String EVENT_NAME; //col 3
    private String TICKET_PRICE; //col 4
    private String TICKET_DATE; //col 5
    //private String TICKET_TIME; //col 6
    private String TICKET_QUANTITY; //col 7
    //private String TICKET_SEATS; //col 8
    private String TICKET_AVAIL; //col 9 for ticket availability

    public Ticket(String ticket_seller, String event_name, String ticket_price, String ticket_date, String ticket_quantity, String ticket_avail) {
        this.TICKET_SELLER = ticket_seller;
        this.EVENT_NAME = event_name;
        this.TICKET_PRICE = ticket_price;
        this.TICKET_DATE = ticket_date;
        this.TICKET_QUANTITY = ticket_quantity;
        this.TICKET_AVAIL = ticket_avail;
    }

    public String getTICKET_SELLER() {
        return TICKET_SELLER;
    }

    public void setTICKET_SELLER(String TICKET_SELLER) {
        this.TICKET_SELLER = TICKET_SELLER;
    }

    public String getEVENT_NAME() {
        return EVENT_NAME;
    }

    public void setEVENT_NAME(String EVENT_NAME) {
        this.EVENT_NAME = EVENT_NAME;
    }

    public String getTICKET_PRICE() {
        return TICKET_PRICE;
    }

    public void setTICKET_PRICE(String TICKET_PRICE) {
        this.TICKET_PRICE = TICKET_PRICE;
    }

    public String getTICKET_DATE() {
        return TICKET_DATE;
    }

    public void setTICKET_DATE(String TICKET_DATE) {
        this.TICKET_DATE = TICKET_DATE;
    }

    public String getTICKET_QUANTITY() {
        return TICKET_QUANTITY;
    }

    public void setTICKET_QUANTITY(String TICKET_QUANTITY) {
        this.TICKET_QUANTITY = TICKET_QUANTITY;
    }

    public String getTICKET_AVAIL() {
        return TICKET_AVAIL;
    }

    public void setTICKET_AVAIL(String TICKET_AVAIL) {
        this.TICKET_AVAIL = TICKET_AVAIL;
    }
}


