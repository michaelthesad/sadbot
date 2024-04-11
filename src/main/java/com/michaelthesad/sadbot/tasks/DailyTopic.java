package com.michaelthesad.sadbot.tasks;

import java.sql.Timestamp;

public class DailyTopic implements Runnable {
    @Override
    public void run() {
        System.out.println("The function is performing as expected... Good job! :)");
        java.util.Date date= new java.util.Date();
        System.out.println(new Timestamp(date.getTime()));

    }
}
