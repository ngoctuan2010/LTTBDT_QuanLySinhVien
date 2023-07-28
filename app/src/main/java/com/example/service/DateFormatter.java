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

    public static String reformat(String date, String inputFormat, String outputFormat) throws ParseException {
        DateFormat iFormat = new SimpleDateFormat(inputFormat);
        Date iDate = (Date) iFormat.parse(date);

        DateFormat oFormat = new SimpleDateFormat(outputFormat);
        String oDate = oFormat.format(iDate);

        return oDate;
    }


    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
