package com.michaelthesad.sadbot.commands;

import com.michaelthesad.sadbot.sadbot;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
            //the event.reply here will go away eventually ;)
            String randomTopic = sadbot.getTopicList();
            event.reply(randomTopic).queue();
            System.out.println(randomTopic);
            java.util.Date date= new java.util.Date();
            System.out.println("^" + new Timestamp(date.getTime()));
        }
    }

    // Guild command

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("introduce", "sadbot will introduce itself"));
        commandData.add(Commands.slash("newtopic", "forces the bot to pull a new topic"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("introduce", "sadbot will introduce itself"));
        commandData.add(Commands.slash("newtopic", "forces the bot to pull a new topic"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
