package com.michaelthesad.sadbot.tasks;

import com.michaelthesad.sadbot.sadbot;

import java.sql.Timestamp;

public class DailyTopic implements Runnable {
    @Override
    public void run() {
        String randomTopic = sadbot.getTopicList();
        System.out.println(randomTopic);
        System.out.println("The function is performing as expected... Good job! :)");
        java.util.Date date= new java.util.Date();
        System.out.println("^" + new Timestamp(date.getTime()));

    }
}
