package com.michaelthesad.sadbot.tasks;

import com.michaelthesad.sadbot.sadbot;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.sql.Timestamp;

import static com.michaelthesad.sadbot.sadbot.server;

public class DailyTopic implements Runnable {
    @Override
    public void run() {
        String randomTopic = sadbot.getTopicList();
        System.out.println(randomTopic);
        System.out.println("The function is performing as expected... Good job! :)");
        java.util.Date date= new java.util.Date();
        System.out.println("^" + new Timestamp(date.getTime()));
        String topicChannel = sadbot.topicChannel;
        ShardManager shardManager = sadbot.shardManager;
        shardManager
                .getGuildById(server)
                .getTextChannelById(topicChannel)
                .sendMessage(randomTopic)
                .queue();
    }
}
