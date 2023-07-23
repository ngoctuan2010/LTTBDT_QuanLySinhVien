package com.example.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    DateFormat formatter;
    String dateFormat = "";

    public DateFormatter(){};
    public DateFormatter(String dateFormat){
        this.dateFormat = dateFormat;
    }

    public String DateToString(Date date){
        formatter = new SimpleDateFormat(dateFormat);
        return formatter.format(date);
    }

    public Date StringToDate(String date) throws ParseException {
        formatter = new SimpleDateFormat(dateFormat);
        return formatter.parse(date);
    }


    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
