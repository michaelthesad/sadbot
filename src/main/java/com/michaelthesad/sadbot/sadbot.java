package com.michaelthesad.sadbot;

import com.michaelthesad.sadbot.commands.CommandManager;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

    //I have no flipping clue what is going on here but anything below this point is gonna be timer related testing
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

        if(startTime.before(now) || startTime.equals(now))
        {
            startTime.add(Calendar.DATE, 1);
        }
        return startTime.getTime();
    }

}

}
