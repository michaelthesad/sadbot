package com.michaelthesad.sadbot.commands;

import com.michaelthesad.sadbot.tasks.DailyTopic;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandManager extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if (command.equals("introduce")) {
            //command /introduce
            event.reply("Hello, I'm sadbot! I don't do anything yet, but I will soon!").queue();
        }
        if (command.equals("force")) {
            //command /force
            //the event.reply here will go away eventually ;)
            event.reply("The function is performing as expected... Good job! :)").queue();
            new DailyTopic().run();
        }
    }

    // Guild command

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("introduce", "sadbot will introduce itself"));
        commandData.add(Commands.slash("force", "forces the bot to run the test command"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("introduce", "sadbot will introduce itself"));
        commandData.add(Commands.slash("force", "forces the bot to run the test command"));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
