package com.michaelthesad.sadbot;

import com.michaelthesad.sadbot.commands.CommandManager;
import com.michaelthesad.sadbot.tasks.DailyTopic;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class sadbot {

    private final Dotenv config;

    public static ShardManager shardManager;

    private static final ArrayList<String> topicList = new ArrayList<String>();

    public static String topicChannel;
    public static String server;


    //This handles connecting to Discord
    public sadbot() throws LoginException {
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");

        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setStatus(OnlineStatus.ONLINE);
        //the discord status is the quotes on this line (will have a random list eventually also)
        builder.setActivity(Activity.customStatus("Starting conversations!"));
        shardManager = builder.build();

        shardManager.addEventListener(new CommandManager());
        new DailyRunnerDaemon(new DailyTopic(), "DailyTopicThread").start();
        loadTopicList();

        topicChannel = config.get("topicchannel");
        server = config.get("SERVER");
    }

    //This makes sure the topic list works
    private void loadTopicList() {
        String fileName = "topiclist.txt";
        try (Scanner scan = new Scanner(new File(fileName))){
            while (scan.hasNext())
                topicList.add(scan.nextLine());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Dotenv getConfig() {
        return config;
    }

    public ShardManager getShardManager() {
        return shardManager;
    }

    public static void main(String[] args) {
        try {
            new sadbot();
        } catch (LoginException e) {
            System.out.println("ERROR: Provided bot token is invalid.");
        }
    }

    //Timer related stuff here
public void givenUsingTimer_whenSchedulingDailyTask_thenCorrect() {
    TimerTask repeatedTask = new TimerTask() {
        public void run() {
            System.out.println("Task performed on " + new Date());
        }
    };
    Timer timer = new Timer("Timer");
}

public class DailyRunnerDaemon
{
    private final Runnable dailyTask;

    private final String runThreadName;

    public DailyRunnerDaemon(Runnable dailyTask, String runThreadName)
    {
        this.dailyTask = dailyTask;
        this.runThreadName = runThreadName;
    }

    public void start()
    {
        startTimer();
    }

    private void startTimer()
    {
        new Timer(runThreadName, true).schedule(new TimerTask() {
            @Override
            public void run() {
                dailyTask.run();
                startTimer();
            }
        }, getNextRunTime());
    }

    private Date getNextRunTime()
    {
        // This is the time of day at which the bot will prompt
        Calendar startTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, 10);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        // remember to reset the timer to 10:00:00 after testing

        if(startTime.before(now) || startTime.equals(now))
        {
            startTime.add(Calendar.DATE, 1);
        }
        return startTime.getTime();
    }

}

// randomizer
public static String getTopicList() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(topicList.size());
        String randomElement = topicList.get(randomIndex);
        topicList.remove(randomIndex);

        return String.valueOf(randomElement);
}

}
