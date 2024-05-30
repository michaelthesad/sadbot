package com.michaelthesad.sadbot.commands;

import com.michaelthesad.sadbot.sadbot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.michaelthesad.sadbot.sadbot.shardManager;

public class CommandManager extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("introduce")) {
            //command /introduce
            event.reply("Hello, I'm sadbot! Every day I will share a new topic for everyone to talk about! If you have any suggestions for future topics feel free to message them to michaelthesad!").queue();
        }
        if (command.equals("newtopic")) {
            //command /newtopic
            String randomTopic = sadbot.getTopicList();
            event.reply(randomTopic).queue();
            System.out.println(randomTopic);
            Date date = new Date();
            System.out.println("^" + new Timestamp(date.getTime()));
        }
        if (command.equals("newstatus")) {
            //command /newstatus
            String randomStatus = sadbot.getStatusList();
            System.out.println("Status changed to:");
            System.out.println(randomStatus);
            shardManager.setActivity(Activity.customStatus(randomStatus));
            event.reply("The activity has been updated via random selection!").setEphemeral(true).queue();
        }
        else if (command.equals("say")) {
            //command /say <message>
            OptionMapping messageOption = event.getOption("message");
            assert messageOption != null;
            String message = messageOption.getAsString();
            event.getChannel().sendMessage(message).queue();
            event.reply("Message sent!").setEphemeral(true).queue();
        }
        if (command.equals("setstatus")) {
            //command /setstatus <message>
            OptionMapping messageOption = event.getOption("message");
            assert messageOption != null;
            String message = messageOption.getAsString();
            shardManager.setActivity(Activity.customStatus(message));
            event.reply("The activity has been updated via your request!").setEphemeral(true).queue();
        }
    }

    // Guild command
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("introduce", "sadbot will introduce himself"));
        commandData.add(Commands.slash("newtopic", "sadbot will pull a new topic"));
        commandData.add(Commands.slash("newstatus", "sadbot will select a new status via the random list"));
        OptionData option1 = new OptionData(OptionType.STRING, "message", "The message you want the bot to say", true);
        commandData.add(Commands.slash("say", "sadbot will repeat the entered text").addOptions(option1));
        commandData.add(Commands.slash("setstatus", "sadbot will have a new status").addOptions(option1));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("introduce", "sadbot will introduce himself"));
        commandData.add(Commands.slash("newtopic", "sadbot will pull a new topic"));
        commandData.add(Commands.slash("newstatus", "sadbot will select a new status via the random list"));
        OptionData option1 = new OptionData(OptionType.STRING, "message", "The message you want the bot to say", true);
        commandData.add(Commands.slash("say", "sadbot will repeat the entered text").addOptions(option1));
        commandData.add(Commands.slash("setstatus", "sadbot will have a new status").addOptions(option1));

        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
