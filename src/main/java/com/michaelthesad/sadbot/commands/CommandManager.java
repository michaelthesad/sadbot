package com.michaelthesad.sadbot.commands;

import com.michaelthesad.sadbot.sadbot;
import io.github.cdimascio.dotenv.Dotenv;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.michaelthesad.sadbot.sadbot.shardManager;
import static java.lang.System.out;

public class CommandManager extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        String author = String.valueOf(event.getUser());
        if (command.equals("introduce")) {
            //command /introduce
            event.reply("Hello, I'm sadbot! Every day I will share a new topic for everyone to talk about! If you have any suggestions for future topics feel free to message them to michaelthesad!").queue();
        }
        if (command.equals("newtopic")) {
            //command /newtopic
            String randomTopic = sadbot.getTopicList();
            event.reply(randomTopic).queue();
            out.println(randomTopic);
            Date date = new Date();
            out.println("^" + new Timestamp(date.getTime()));
        }
        if (command.equals("newstatus")) {
            //command /newstatus
            String randomStatus = sadbot.getStatusList();
            out.println("Status changed to:");
            out.println(randomStatus);
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
        if (command.equals("suggest")) {
            //command /suggest <message>
            OptionMapping suggestionOption = event.getOption("suggestion");
            assert suggestionOption != null;
            String message = suggestionOption.getAsString();
            Dotenv config = Dotenv.configure().load();
            String topicsuggestions = config.get("suggestionsfp");
            new File(topicsuggestions);
            Path File = Path.of("suggestions.txt");
            try {
                Files.write(File, List.of(message+" ..........was suggested by "+author), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            event.reply("Your suggestion has been recorded. Thank you!").setEphemeral(true).queue();
        }
    }

    // Guild command
    @Override
    public void onGuildReady(GuildReadyEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        OptionData option1 = new OptionData(OptionType.STRING, "message", "The message you want the bot to say", true);
        OptionData option2 = new OptionData(OptionType.STRING, "suggestion", "Type your suggestion for a new topic here!", true);
        commandData.add(Commands.slash("introduce", "sadbot will introduce himself"));
        commandData.add(Commands.slash("newtopic", "sadbot will pull a new topic"));
        commandData.add(Commands.slash("newstatus", "sadbot will select a new status via the random list"));
        commandData.add(Commands.slash("say", "sadbot will repeat the entered text").addOptions(option1));
        commandData.add(Commands.slash("setstatus", "sadbot will have a new status").addOptions(option1));
        commandData.add(Commands.slash("suggest", "suggest a new topic to be added!").addOptions(option2));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        List<CommandData> commandData = new ArrayList<>();
        OptionData option1 = new OptionData(OptionType.STRING, "message", "The message you want the bot to say", true);
        OptionData option2 = new OptionData(OptionType.STRING, "suggestion", "Type your suggestion for a new topic here!", true);
        commandData.add(Commands.slash("introduce", "sadbot will introduce himself"));
        commandData.add(Commands.slash("newtopic", "sadbot will pull a new topic"));
        commandData.add(Commands.slash("newstatus", "sadbot will select a new status via the random list"));
        commandData.add(Commands.slash("say", "sadbot will repeat the entered text").addOptions(option1));
        commandData.add(Commands.slash("setstatus", "sadbot will have a new status").addOptions(option1));
        commandData.add(Commands.slash("testwrite", "suggest a new topic to be added!").addOptions(option2));
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
