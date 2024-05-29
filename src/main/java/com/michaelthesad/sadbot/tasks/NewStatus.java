package com.michaelthesad.sadbot.tasks;

import com.michaelthesad.sadbot.sadbot;
import net.dv8tion.jda.api.entities.Activity;

import static com.michaelthesad.sadbot.sadbot.shardManager;

public class NewStatus implements Runnable{

    public void run() {
        String randomStatus = sadbot.getStatusList();
        System.out.println(randomStatus);
        System.out.println("Selecting new status...");
        shardManager.setActivity(Activity.customStatus(randomStatus));
    }
}
