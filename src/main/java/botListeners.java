import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

import org.jetbrains.annotations.NotNull;

import java.util.Hashtable;

public class botListeners extends ListenerAdapter {

    // Initialises the dictionary of messenger and points
    Hashtable<String, Integer> messengerDict = new Hashtable<String, Integer>();

    String[] textChats = {" smells, ... *sniffs* ", " ya pineapple pizza eating fackenoaf ", " , facken drugga.", " whatyatalkingabeet?!?" };
    int[] priceList = {25, 25, 50, 100};

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        super.onMessageReceived(event);

        // Overriding the method from listenerAdapter

        if (!event.getAuthor().isBot()) {

            // Prints message in output terminal for debugging
            String message = event.getMessage().getContentRaw();
            System.out.println("A message has been sent: " + message);
            String messenger = event.getAuthor().getAsTag();

            // Initialises a dictionary of users and user's points
            if (messengerDict.containsKey(event.getAuthor().getAsTag())) {
                messengerDict.put(messenger, messengerDict.get(messenger) + 1);
            } else {
                messengerDict.put(event.getAuthor().getAsTag(), 1);
            }
            System.out.println(messengerDict.toString());
        }
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        super.onSlashCommandInteraction(event);

        if (event.getName().equals("raid")) {
            OptionMapping option = event.getOption("raid");
            if (option != null) {

                // Takes Options from the command the user has input into discord - from other java class
                OptionMapping raidChannel = event.getOption("raid");
                OptionMapping userName = event.getOption("user");
                OptionMapping numberChoice = event.getOption("menuchoice");

                // Gets the messengers @ (for testing)
                String messenger = event.getUser().getAsTag();

                if (raidChannel != null) {

                    // Checks that the users has enough points
                    System.out.println(messenger);
                    if (messengerDict.get(messenger) >= priceList[numberChoice.getAsInt() - 1]) {

                        // Chooses text message and sends it to specified channel
                        String channelName = raidChannel.getAsTextChannel().getName();
                        String optionText = textChats[numberChoice.getAsInt() - 1];
                        TextChannel textChannel = event.getGuild().getTextChannelsByName(channelName, true).get(0);
                        textChannel.sendMessage(userName.getAsMentionable().getAsMention() + optionText).queue();

                        // Take off price of message from their total spending
                        messengerDict.put(messenger, messengerDict.get(messenger) - priceList[numberChoice.getAsInt() - 1]);

                        // Sends message back that only requester can see
                        event.reply("Told em, aey?!").setEphemeral(true)
                                .queue();
                    }else{

                        // Sends message back that only requester can see, if they don't have enough funds!
                        event.reply("Not got enough points, ya dummy").setEphemeral(true).queue();
                    }
                }
            }
        }
        else if (event.getName().equals("menu")) {

            // Sends a menu with all the possible messages that can be sent
            event.reply(" 1. Smells \uD83D\uDE36 - 25 points \n 2. Eats Pineapple Pizza \uD83C\uDF4D - 25 points\n 3. \"Drugga\" \uD83D\uDC8A - 50 points \n4. whatyatalkingabeet - 100 points").setEphemeral(true).queue();

        }
        else if (event.getName().equals("check")) {

            // Checks how many points are in the dictionary for the user
            String messenger = event.getUser().getAsTag();
            event.reply(messenger + " has " + messengerDict.get(messenger) + " points!").setEphemeral(true).queue();
        }
    }


}