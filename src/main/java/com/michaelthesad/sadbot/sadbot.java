package com.michaelthesad.sadbot;

import com.michaelthesad.sadbot.commands.CommandManager;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class sadbot {

    private final Dotenv config;

    private final ShardManager shardManager;

    public sadbot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        builder.setActivity(Activity.customStatus("Please be patient... I was only born yesterday."));
        shardManager = builder.build();

        //Register Listeners
        shardManager.addEventListener(new CommandManager());
    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            sadbot bot = new sadbot();
        } catch (LoginException e) {
            System.out.println("ERROR: Provided bot token is invalid.");
        }
    }

    //I have no flipping clue what is going on here


}
